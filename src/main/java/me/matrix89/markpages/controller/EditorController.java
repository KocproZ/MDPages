package me.matrix89.markpages.controller;

import me.matrix89.markpages.model.PageModel;
import me.matrix89.markpages.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class EditorController {

    @Autowired
    private PageRepository pageRepository;

    @GetMapping("/editor")
    public String editor() {
        return "editor";
    }

    @GetMapping("/edit/{a}")
    public String edit(@PathVariable String a, Model model) {
        PageModel p = pageRepository.getByName(a);
        if (a == null) {
            return "redirect:/editor";
        }
        model.addAttribute("name", p.getName());
        model.addAttribute("content", p.getContent());
        return "editor";

    }

    @RequestMapping("/add")
    public String add(@RequestParam String name, @RequestParam String mdPage, @RequestParam String visibility) {
        PageModel p = new PageModel();
        p.setName(name);
        p.setContent(mdPage);
        p.setLastEdited(new Date());
        if (!visibility.equals("authorized") && !visibility.equals("everyone"))
            return "redirect:/editor";
        p.setVisibility(visibility);
        pageRepository.save(p);
        return String.format("redirect:%s", name);
    }

    @RequestMapping("/update")
    public String update(@RequestParam String name, @RequestParam String mdPage) {
        PageModel m = pageRepository.getByName(name);
        m.setContent(mdPage);
        pageRepository.save(m);
        return String.format("redirect:%s", name);
    }

}
