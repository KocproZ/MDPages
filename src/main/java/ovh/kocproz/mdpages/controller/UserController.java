package ovh.kocproz.mdpages.controller;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.page.repository.PageRepository;
import ovh.kocproz.mdpages.data.repository.UserRepository;

import java.security.Principal;

//@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger;
    private UserRepository userRepository;
    private PageRepository pageRepository;
    private Pbkdf2PasswordEncoder passwordEncoder;

    public UserController(Logger logger,
                          UserRepository userRepository,
                          PageRepository pageRepository,
                          Pbkdf2PasswordEncoder passwordEncoder) {
        this.logger = logger;
        this.userRepository = userRepository;
        this.pageRepository = pageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("")
    public String user() {
        return "redirect:/user/profile";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {
        UserModel user = userRepository.getByUsername(principal.getName());

        model.addAttribute("page_title", String.format("%s profile | md pages", user.getUsername()));
        model.addAttribute("user", user);
//        model.addAttribute("pages", pages);
        return "profile";
    }

    @GetMapping("/{username}")
    public String profileByUsername(@PathVariable(name = "username") String username,
                                    Model model, Authentication auth) {
        UserModel user = userRepository.getByUsername(username);

        model.addAttribute("page_title", String.format("%s profile | md pages", user.getUsername()));
        model.addAttribute("user", user);
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
