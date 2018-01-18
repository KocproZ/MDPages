package ovh.kocproz.markpages.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ovh.kocproz.markpages.data.dto.PageDTO;
import ovh.kocproz.markpages.data.dto.SearchDTO;
import ovh.kocproz.markpages.data.dto.TagSearchDTO;
import ovh.kocproz.markpages.service.SearchService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author Hubertus
 * Created 20.11.2017
 */

@RestController
public class RestSearchController {

    private SearchService searchService;

    public RestSearchController(SearchService searchService) {

        this.searchService = searchService;
    }

    @GetMapping("/search/tag/data")
    public List<PageDTO> getTagSearchData(@Valid TagSearchDTO searchDTO, Principal principal) {
        return searchService.searchByTag(searchDTO.getTag(), searchDTO.getP(), principal != null);
    }

    @GetMapping("/search/page/data")
    public List<PageDTO> getPageSearchData(@Valid SearchDTO searchDTO, Principal principal) {
        return searchService.searchPages(searchDTO.getQuery(), principal != null, searchDTO.getP());
    }

    @GetMapping("/list/data")
    public List<PageDTO> getListData(@RequestParam(name = "p") int p, Principal principal) {
        return searchService.getPages(p, principal != null);
    }
}
