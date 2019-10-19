package ovh.kocproz.mdpages.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ovh.kocproz.mdpages.service.SearchService;
import ovh.kocproz.mdpages.service.TagService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 * @author Hubertus
 * Created 19.11.2017
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;
    private TagService tagService;

    public SearchController(SearchService searchService, TagService tagService) {
        this.searchService = searchService;
        this.tagService = tagService;
    }

    @GetMapping("/tag")
    public String searchTag(@Valid @Length(min = 3) @ModelAttribute("query") @RequestParam("tag") String tag,
                            Model model, Principal principal) {
        model.addAttribute("pageCount", searchService.countPagesByTag(tag, principal != null));
        return "tagSearch";
    }

    @RequestMapping("")
    public String search(Model model, Principal principal, @Valid @NotNull @RequestParam(name = "query") String query) {
        long pageCount = searchService.countPagesContaining(query, principal != null);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("tags", tagService.getAllTagsContaining(query));
        return "search";
    }
}
