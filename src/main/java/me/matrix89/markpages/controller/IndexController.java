package me.matrix89.markpages.controller;

import me.matrix89.markpages.data.dto.SearchDTO;
import me.matrix89.markpages.data.model.PageModel;
import me.matrix89.markpages.data.model.UserModel;
import me.matrix89.markpages.data.repository.PageRepository;
import me.matrix89.markpages.data.repository.TagRepository;
import me.matrix89.markpages.data.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    Logger logger;

    @GetMapping("/")
    public String index(Model m, Principal principal) {
        if (principal != null) {
            m.addAttribute("pages", pageRepository.findAllByVisibilityNot(
                    PageModel.Visibility.HIDDEN, new Sort(Sort.Direction.ASC, "name")
            ));
            m.addAttribute("user", userRepository.getByUsername(principal.getName()));
        } else {
            m.addAttribute("pages", pageRepository.findAllByVisibility(
                    PageModel.Visibility.PUBLIC, new Sort(Sort.Direction.ASC, "name")
            ));
        }

        return "index";
    }

    @GetMapping("/{id}")
    public String mdPage(@PathVariable(name = "id") String pageId, Model model, Principal principal) {
        PageModel page = pageRepository.findAllByStringId(pageId);
        if (page == null) {
            return "redirect:/";
        }

        UserModel user = null;
        if (principal != null) {
            user = userRepository.getByUsername(principal.getName());
        }


        if (user == null && page.getVisibility() == PageModel.Visibility.AUTHORIZED) {
            return "redirect:/login";
        }

        model.addAttribute("page", page);
        model.addAttribute("user", user);
        return "mdPage";
    }

    @RequestMapping("/search")
    public String search(Model model, Principal principal, @Valid @ModelAttribute(name = "query") SearchDTO search) {
        if (principal != null)
            model.addAttribute("user", userRepository.getByUsername(principal.getName()));

        if (principal != null && !search.getName().isEmpty()) {
            model.addAttribute("pages", pageRepository.findAllByVisibilityNotAndNameContaining(
                    PageModel.Visibility.HIDDEN, search.getName(), new Sort(Sort.Direction.ASC, "name")));
        } else if (principal == null && !search.getName().isEmpty()) {
            model.addAttribute("pages", pageRepository.findAllByVisibilityAndNameContaining(
                    PageModel.Visibility.PUBLIC, search.getName(), new Sort(Sort.Direction.ASC, "name")));
        } else if (principal != null && !search.getTags().isEmpty()) { //TODO szukanie tagami dla zalogowanych
//            model.addAttribute("page", pageRepository.findAllByVisibilityNotAndTagNames(
//                    PageModel.Visibility.HIDDEN, search.getTags(), search.getTags().size()
//            ));
        } else if (principal == null && !search.getTags().isEmpty()) { //TODO szukanie tagami dla niezalogowanych
//            model.addAttribute("page", pageRepository.findAllByVisibilityAndTagNames(
//                    PageModel.Visibility.PUBLIC, search.getTags(), search.getTags().size()
//            ));
        }


        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
