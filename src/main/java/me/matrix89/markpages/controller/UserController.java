package me.matrix89.markpages.controller;

import me.matrix89.markpages.model.UserModel;
import me.matrix89.markpages.repository.PageRepository;
import me.matrix89.markpages.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    Logger logger;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private Pbkdf2PasswordEncoder passwordEncoder;

    @RequestMapping("")
    public String user() {
        return "redirect:/user/profile";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {
        UserModel user = userRepository.getByUsername(principal.getName());
        List pages = pageRepository.getAllByMaintainer_Id(user.getId());

        model.addAttribute("page_title", String.format("%s's profile | md pages", user.getUsername()));
        model.addAttribute("user", user);
        model.addAttribute("pages", pages);
        return "profile";
    }

    @PostMapping("/chpass")
    public String changePassword(@RequestParam String oldpass, @RequestParam String newpass, Principal principal) {
        UserModel user = userRepository.getByUsername(principal.getName());
        if (passwordEncoder.matches(oldpass, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newpass));
            userRepository.save(user);
        }
        return "redirect:/user/profile";
    }

}
