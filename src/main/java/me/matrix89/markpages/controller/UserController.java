package me.matrix89.markpages.controller;

import me.matrix89.markpages.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    Logger logger;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Pbkdf2PasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public String login() {
        /*
        UserModel m = new UserModel();
        m.setUsername("admin");
        m.setRole("ADMIN");
        m.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(m);
        */
        return "login";
    }

}
