package me.matrix89.markpages.controller;

import me.matrix89.markpages.Util;
import me.matrix89.markpages.data.model.PageModel;
import me.matrix89.markpages.data.model.TagModel;
import me.matrix89.markpages.data.model.UserModel;
import me.matrix89.markpages.data.repository.PageRepository;
import me.matrix89.markpages.data.repository.TagRepository;
import me.matrix89.markpages.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class EditorController {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/edit")
    public String editor(@RequestParam(defaultValue = "ace") String e, Model model) {
        if (e.equals("cke")) {
            return "editor-cke";
        } else if (e.equals("ace")) {
            model.addAttribute("tags", tagRepository.findAll());
            return "editor";
        }

        return "editor";
    }

    @GetMapping("/edit/{stringId}")
    public String edit(@PathVariable String stringId, Model model, Principal principal) {
        PageModel page = pageRepository.findAllByStringId(stringId);
        UserModel user = userRepository.getByUsername(principal.getName());
        Set<TagModel> tags = page.getTags();
        tags.forEach(t -> {
            t.setPages(null);
        });
        if (user == null || page == null)
            return "redirect:/editor";
        if (user.canEdit(page)) {
            model.addAttribute("visibility", page.getVisibility());
            model.addAttribute("page", page);
            model.addAttribute("pageTags", tags);
            return "editor";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/add")
    public String add(@RequestParam String name, @RequestParam String mdPage,
                      @RequestParam PageModel.Visibility visibility, Principal principal) {
        PageModel p = new PageModel();
        p.setName(name);
        p.setContent(mdPage);
        p.setCreationDate(new Date());
        p.setLastEdited(new Date());
//        p.setMaintainer(userRepository.getByUsername(principal.getName()));
        p.setVisibility(visibility);
        p.setStringId(Util.randomString(8));
        pageRepository.save(p);
        return String.format("redirect:/p/%s", p.getStringId());
    }

    @RequestMapping("/update")
    public String update(@RequestParam String stringId, @RequestParam String mdPage,
                         @RequestParam PageModel.Visibility visibility, @RequestParam String name,
                         @RequestParam List<String> tags,
                         Principal principal) {
        if (principal != null &&
                userRepository.getByUsername(principal.getName()).canEdit(pageRepository.findAllByStringId(stringId))) {
            PageModel page = pageRepository.findAllByStringId(stringId);
            page.setName(name);
            page.setContent(mdPage);
            page.setVisibility(visibility);
            page.setLastEdited(new Date());
            tags.forEach(tag -> {
                List<TagModel> foundTag = tagRepository.findAllByName(tag);
                if (!foundTag.isEmpty()) {
                    page.addTag(foundTag.get(0));
                }
            });//TODO wczytywanie tagów w editor.html
            pageRepository.save(page);
        }
        return String.format("redirect:/p/%s", stringId);
    }

}
