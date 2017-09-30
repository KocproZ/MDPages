package me.matrix89.markpages.controller;

import me.matrix89.markpages.model.UserModel;
import me.matrix89.markpages.repository.PageRepository;
import me.matrix89.markpages.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Pbkdf2PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String admin(Model model) {
        model.addAttribute("pages", pageRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

    @PostMapping("/useradd")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        UserModel user = new UserModel();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/admin";
    }


}
