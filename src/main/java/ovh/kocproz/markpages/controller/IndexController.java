package ovh.kocproz.markpages.controller;

import org.slf4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ovh.kocproz.markpages.Visibility;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.PageRepository;
import ovh.kocproz.markpages.data.repository.UserRepository;
import ovh.kocproz.markpages.service.SearchService;
import ovh.kocproz.markpages.service.TagService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@Controller
public class IndexController {

    private PageRepository pageRepository;
    private UserRepository userRepository;
    private Logger logger;
    private SearchService searchService;
    private TagService tagService;

    public IndexController(PageRepository pageRepository,
                           UserRepository userRepository,
                           Logger logger,
                           SearchService searchService,
                           TagService tagService) {
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
        this.logger = logger;
        this.searchService = searchService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String index(Model m, Principal principal) {
        return "index";
    }

    @GetMapping("/list")
    public String list(Model m, Principal principal) {
        if (principal != null) {
            m.addAttribute("pages", pageRepository.findAllByVisibility(
                    Visibility.PUBLIC, new Sort(Sort.Direction.ASC, "title")
            ));
            m.addAttribute("user", userRepository.getByUsername(principal.getName()));
        } else {
            m.addAttribute("pages", pageRepository.findAll(
                    new Sort(Sort.Direction.ASC, "title")
            ));
        }

        return "list";
    }

    @GetMapping("/p/{id}")
    public String mdPage(@PathVariable(name = "id") String pageId, Model model, Principal principal) {
        PageModel page = pageRepository.findOneByCode(pageId);
        if (page == null) {
            return "redirect:/";
        }

        UserModel user = null;
        if (principal != null) {
            user = userRepository.getByUsername(principal.getName());
        }


        if (user == null && page.getVisibility() == Visibility.AUTHORIZED) {
            return "redirect:/login";
        }

        model.addAttribute("page", page);
        model.addAttribute("user", user);
        return "mdPage";
    }

    //TODO: DTO validation
//    @RequestMapping("/search")
//    public String search(Model model, Principal principal, @Valid @ModelAttribute(name = "query") SearchDTO search) {
//        if (principal != null)
//            model.addAttribute("user", userRepository.getByUsername(principal.getName()));
//
//        if (principal != null && !search.getName().isEmpty()) {
//            model.addAttribute("pages", pageRepository.findAllByVisibilityNotAndTitleContaining(
//                    Visibility.AUTHORIZED, search.getName(), new Sort(Sort.Direction.ASC, "title")));
//        } else if (principal == null && !search.getName().isEmpty()) {
//            model.addAttribute("pages", pageRepository.findAllByVisibilityAndTitleContaining(
//                    Visibility.PUBLIC, search.getName(), new Sort(Sort.Direction.ASC, "title")));
//        } else if (principal != null && !search.getTags().isEmpty()) {
//            model.addAttribute("pages", pageRepository.findAllByVisibilityNotAndTagNames(
//                    Visibility.AUTHORIZED, search.getTags()
//            ));
//        } else if (principal == null && !search.getTags().isEmpty()) {
//            model.addAttribute("pages", pageRepository.findAllByVisibilityAndTagNames(
//                    Visibility.PUBLIC, search.getTags()
//            ));
//        }
//
//        return "list";
//    }

    @RequestMapping("/search")
    public String search(Model model, Principal principal, @Valid @NotNull @RequestParam(name = "query") String query) {
        long pageCount = searchService.countPagesContaining(query, principal != null);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("tags", tagService.getAllTagsContaining(query));
        return "search";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
