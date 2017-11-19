package ovh.kocproz.markpages.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ovh.kocproz.markpages.Permission;
import ovh.kocproz.markpages.data.repository.PageRepository;
import ovh.kocproz.markpages.data.repository.UserRepository;
import ovh.kocproz.markpages.service.PermissionService;
import ovh.kocproz.markpages.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private PageRepository pageRepository;
    private UserRepository userRepository;
    private UserService userService;
    private PermissionService permissionService;
    private Pbkdf2PasswordEncoder passwordEncoder;

    public AdminController(PageRepository pageRepository,
                           UserRepository userRepository,
                           UserService userService,
                           PermissionService permissionService,
                           Pbkdf2PasswordEncoder passwordEncoder) {
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String admin(Model model,
                        @RequestParam(defaultValue = "1") int pagesPage,
                        @RequestParam(defaultValue = "1") int usersPage) {
        model.addAttribute("pagesPage", pagesPage);
        model.addAttribute("pages", pageRepository.findAll(
                new PageRequest(pagesPage - 1, 10, Sort.Direction.ASC, "name")
        ));

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("users", userRepository.findAll(
                new PageRequest(usersPage - 1, 10, Sort.Direction.ASC, "username")
        ));
        return "admin";
    }

    @PostMapping("/useradd")
    public String addUser(@RequestParam String username, @RequestParam String password, Authentication auth) {
        if (!username.isEmpty() &&
                !password.isEmpty() &&
                permissionService.hasPermission(auth, Permission.REGISTER)) {
            userService.createUser(username, password,
                    new Permission[]{Permission.CREATE,
                            Permission.UPLOAD});
        }
        return "redirect:/admin";
    }

}
