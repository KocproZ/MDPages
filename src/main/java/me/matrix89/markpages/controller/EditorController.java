package me.matrix89.markpages.controller;

import me.matrix89.markpages.Util;
import me.matrix89.markpages.data.model.PageModel;
import me.matrix89.markpages.data.model.UserModel;
import me.matrix89.markpages.data.repository.PageRepository;
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

@Controller
public class EditorController {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/editor")
    public String editor(@RequestParam(defaultValue = "ace") String e) {
        if (e != null) {
            if (e.equals("cke"))
                return "editor-cke";
            else if (e.equals("ace"))
                return "editor";
        }
        return "editor";
    }

    @GetMapping("/edit/{StringId}")
    public String edit(@PathVariable String StringId, Model model, Principal principal) {
        PageModel page = pageRepository.getAllByStringId(StringId);
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
        return String.format("redirect:%s", p.getStringId());
    }

    @RequestMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam String mdPage,
                         @RequestParam PageModel.Visibility visibility, @RequestParam String name) {
        PageModel m = pageRepository.findOne(id);
        m.setName(name);
        m.setContent(mdPage);
        m.setVisibility(visibility);
        m.setLastEdited(new Date());
        pageRepository.save(m);
        return String.format("redirect:%d", id);
    }

}
