package me.matrix89.markpages.controller;

import me.matrix89.markpages.model.PageModel;
import me.matrix89.markpages.model.UserModel;
import me.matrix89.markpages.repository.PageRepository;
import me.matrix89.markpages.repository.TagRepository;
import me.matrix89.markpages.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

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
            m.addAttribute("pages", pageRepository.findAll(new Sort(Sort.Direction.ASC, "name")));
            m.addAttribute("user", userRepository.getByUsername(principal.getName()));
        } else {
            m.addAttribute("pages", pageRepository.getAllByVisibility(PageModel.Visibility.PUBLIC, new Sort(Sort.Direction.ASC, "name")));
        }

        return "index";
    }

    @GetMapping("/test")
    public @ResponseBody
    List<PageModel> test() {
        return pageRepository.getAllByMaintainer_Id(1);
    }

    @GetMapping("/{id}")
    public String mdPage(@PathVariable(name = "id") String pageId, Model model, Principal principal) {
        PageModel page = pageRepository.getAllByStringId(pageId);
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

//    @RequestMapping("/search") //TODO
//    public String search(Model model, @RequestParam String name, @RequestParam String tag, Principal principal) {
//        if (principal != null) {
//            model.addAttribute("pages",
//                    pageRepository.findAllByVisibilityNotLikeAndNameContainingOrderByNameAsc(PageModel.Visibility.HIDDEN, name));
//            model.addAttribute("user", userRepository.getByUsername(principal.getName()));
//        } else {
//            model.addAttribute("pages",
//                    pageRepository.findAllByVisibilityAndNameContainingOrderByNameAsc(PageModel.Visibility.PUBLIC, name));
//        }
//        return "index";
//    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
