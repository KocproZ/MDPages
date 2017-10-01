package me.matrix89.markpages.controller;

import me.matrix89.markpages.model.PageModel;
import me.matrix89.markpages.model.UserModel;
import me.matrix89.markpages.repository.PageRepository;
import me.matrix89.markpages.repository.UserRepository;
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
    public String editor() {
        return "editor";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, Principal principal) {
        PageModel page = pageRepository.findOne(id);
        UserModel user = userRepository.getByUsername(principal.getName());
        if (user == null || page == null)
            return "redirect:/editor";
        if (user.canEdit(page)) {
            model.addAttribute("page", page);
            return "editor";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/add")
    public String add(@RequestParam String name, @RequestParam String mdPage, @RequestParam String visibility, Principal principal) {
        PageModel p = new PageModel();
        p.setName(name);
        p.setContent(mdPage);
        p.setCreationDate(new Date());
        p.setLastEdited(new Date());
        p.setMaintainer(userRepository.getByUsername(principal.getName()));
        if (!visibility.equals("authorized") && !visibility.equals("everyone"))
            return "redirect:/editor";
        p.setVisibility(visibility);
        pageRepository.save(p);
        return String.format("redirect:%d", p.getId());
    }

    @RequestMapping("/update")
    public String update(@RequestParam String name, @RequestParam String mdPage, @RequestParam String visibility) {
        PageModel m = pageRepository.getByName(name);
        m.setContent(mdPage);
        m.setVisibility(visibility);
        m.setLastEdited(new Date());
        pageRepository.save(m);
        return String.format("redirect:%s", name);
    }

}
