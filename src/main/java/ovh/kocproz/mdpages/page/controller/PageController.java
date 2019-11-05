package ovh.kocproz.mdpages.page.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ovh.kocproz.mdpages.page.dto.request.PaginatedListRequestDTO;
import ovh.kocproz.mdpages.page.dto.response.PageDetailsResponseDTO;
import ovh.kocproz.mdpages.page.dto.response.PageListResponseDTO;
import ovh.kocproz.mdpages.page.dto.response.PaginationInfoResponseDTO;
import ovh.kocproz.mdpages.page.service.PageService;

import java.security.Principal;

@RestController
@RequestMapping("/rest/pages")
public class PageController {

    private PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/listInfo")
    public PaginationInfoResponseDTO getPaginationInfo(Principal principal) {
        return pageService.getPaginationInfo(principal != null);
    }

    @GetMapping("/list")
    public PageListResponseDTO getPageList(Principal principal, @RequestParam PaginatedListRequestDTO paginatedListRequestDTO) {
        return pageService.listPages(paginatedListRequestDTO.getPage(), principal != null);
    }

    public PageDetailsResponseDTO getPageDetails(Principal principal){
    }
}
