package me.matrix89.markpages.controller;

import me.matrix89.markpages.model.PageModel;
import me.matrix89.markpages.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class Index {
    @Autowired
    private PageRepository pageRepository;

    @GetMapping("/")
    public String index(Model m, Principal principal) {
        if (principal != null) {
            m.addAttribute("pages", pageRepository.findAll());
        } else {
            m.addAttribute("pages", pageRepository.getAllByVisibility("everyone"));
        }

        return "index";
    }

    @GetMapping("/{a}")
    public String mdPage(@PathVariable String a, Model model) {
        PageModel p = pageRepository.getByName(a);
        if (p == null) {
            return "redirect:/";
        }
        model.addAttribute("page_title", p.getName());
        model.addAttribute("content", p.getContent());
        return "mdPage";
    }

}
