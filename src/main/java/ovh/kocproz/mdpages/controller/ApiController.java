package ovh.kocproz.mdpages.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ovh.kocproz.mdpages.service.SearchService;
import ovh.kocproz.mdpages.service.TagService;
import ovh.kocproz.mdpages.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hubertus
 * Created 12.11.2017
 */
@RestController
public class ApiController {

    private TagService tagService;
    private UserService userService;
    private SearchService searchService;

    public ApiController(TagService tagService,
                         UserService userService,
                         SearchService searchService) {
        this.tagService = tagService;
        this.userService = userService;
        this.searchService = searchService;
    }

    @GetMapping("/api/tagsAutocomplete")
    public List<String> getTagsFromFragment(@RequestParam(name = "fragment") String fragment) {
        return fragment == null || fragment.length() < 3
                ? new ArrayList<String>() : tagService.getTagsContaining(fragment);
    }

    @GetMapping("/api/usersAutocomplete")
    public List<String> getUsersFromFragment(@RequestParam(name = "fragment") String fragment) {
        return fragment == null || fragment.length() < 3
                ? new ArrayList<String>() : userService.getUsersContaining(fragment);
    }



}
