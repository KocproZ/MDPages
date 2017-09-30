package me.matrix89.markpages.controller;

import me.matrix89.markpages.repository.PageRepository;
import me.matrix89.markpages.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("pages", pageRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

}
