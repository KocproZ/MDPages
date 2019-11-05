package ovh.kocproz.mdpages.controller;

import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ovh.kocproz.mdpages.Visibility;
import ovh.kocproz.mdpages.data.model.PageModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.page.repository.PageRepository;
import ovh.kocproz.mdpages.data.repository.UserRepository;
import ovh.kocproz.mdpages.service.SearchService;
import ovh.kocproz.mdpages.service.TagService;

import java.security.Principal;

//@Controller
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
        m.addAttribute("page", pageRepository.findOneByCode("homePage"));
        return "index";
    }

    @GetMapping("/list")
    public String list(Model m, Principal principal) {
        m.addAttribute("pageCount", searchService.countPages(principal != null));
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

        model.addAttribute("page_title", page.getTitle() + " | mdPages");
        model.addAttribute("page", page);
        model.addAttribute("user", user);
        return "mdPage";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
