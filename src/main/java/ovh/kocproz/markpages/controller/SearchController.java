package ovh.kocproz.markpages.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ovh.kocproz.markpages.data.dto.TagSearchDTO;
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
    public String searchTag(@Valid @ModelAttribute("query") TagSearchDTO dto, Model model, Principal principal) {
        model.addAttribute("pageCount", searchService.countPagesByTag(dto.getTag(), principal != null));
        return "tagSearch";
    }
}
