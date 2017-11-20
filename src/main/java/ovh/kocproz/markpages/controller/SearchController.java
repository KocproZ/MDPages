package ovh.kocproz.markpages.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ovh.kocproz.markpages.service.SearchService;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Hubertus
 * Created 19.11.2017
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/tag")
    public String searchTag(@Valid @Length(min = 3) @ModelAttribute("query") @RequestParam("tag") String tag,
                            Model model, Principal principal) {
        model.addAttribute("pageCount", searchService.countPagesByTag(tag, principal != null));
        return "tagSearch";
    }
}
