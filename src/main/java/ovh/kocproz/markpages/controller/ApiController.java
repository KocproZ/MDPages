package ovh.kocproz.markpages.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ovh.kocproz.markpages.service.TagService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hubertus
 * Created 12.11.2017
 */
@RestController
public class ApiController {


    private TagService tagService;

    public ApiController(TagService tagService) {

        this.tagService = tagService;
    }

    @GetMapping("/api/tagsAutocomplete")
    public List<String> getEntries(@RequestParam(name = "fragment") String fragment) {
        return fragment == null || fragment.length() < 3
                ? new ArrayList<String>() : tagService.getTagsContaining(fragment);
    }

}
