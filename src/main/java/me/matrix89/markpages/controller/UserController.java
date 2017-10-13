package me.matrix89.markpages.controller;

import me.matrix89.markpages.data.model.UserModel;
import me.matrix89.markpages.data.repository.PageRepository;
import me.matrix89.markpages.data.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List pages = pageRepository.findAllByMaintainer_Id(user.getId());

        model.addAttribute("page_title", String.format("%s profile | md pages", user.getUsername()));
        model.addAttribute("user", user);
        model.addAttribute("pages", pages);
        return "profile";
    }

    @PostMapping("/chpass")
    public String changePassword(@RequestParam String oldpass, @RequestParam String newpass,
                                 Principal principal, RedirectAttributes red) {
        if (newpass.isEmpty()) {
            red.addFlashAttribute("emptyNewPassword", true);
            return "redirect:/user/profile";
        }

        UserModel user = userRepository.getByUsername(principal.getName());
        if (passwordEncoder.matches(oldpass, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newpass));
            userRepository.save(user);
            red.addFlashAttribute("success", true);
            return "redirect:/user/profile";
        }

        red.addFlashAttribute("wrongOldPassword", true);
        return "redirect:/user/profile";
    }

}
