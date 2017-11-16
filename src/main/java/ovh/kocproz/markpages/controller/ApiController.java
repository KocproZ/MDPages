package ovh.kocproz.markpages.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ovh.kocproz.markpages.service.TagService;
import ovh.kocproz.markpages.service.UserService;

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

    public ApiController(TagService tagService,
                         UserService userService) {
        this.tagService = tagService;
        this.userService = userService;
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
