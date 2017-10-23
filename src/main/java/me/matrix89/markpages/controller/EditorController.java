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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
public class EditorController {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @RequestMapping("/editor/tags") //TODO post request, move to other class
    public @ResponseBody
    Map<String, String> test() {
        Iterable<TagModel> a = tagRepository.findAll();
        Map<String, String> list = new HashMap<>();
        a.forEach(tagModel -> list.put(tagModel.getName(), null));
        return list;
    }//TODO https://github.com/showdownjs/showdown/wiki/Showdown's-Markdown-syntax#ordered-lists

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

    @GetMapping("/edit/{StringId}")
    public String edit(@PathVariable String StringId, Model model, Principal principal) {
        PageModel page = pageRepository.findAllByStringId(StringId);
        UserModel user = userRepository.getByUsername(principal.getName());
        if (user == null || page == null)
            return "redirect:/editor";
        if (user.canEdit(page)) {
            model.addAttribute("visibility", page.getVisibility());
            model.addAttribute("page", page);
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
        p.setMaintainer(userRepository.getByUsername(principal.getName()));
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
