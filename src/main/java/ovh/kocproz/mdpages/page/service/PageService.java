package ovh.kocproz.mdpages.page.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.kocproz.mdpages.Visibility;
import ovh.kocproz.mdpages.data.model.PageModel;
import ovh.kocproz.mdpages.data.model.TagModel;
import ovh.kocproz.mdpages.page.dto.response.PageInfoResponseDTO;
import ovh.kocproz.mdpages.page.dto.response.PageListResponseDTO;
import ovh.kocproz.mdpages.page.dto.response.PaginationInfoResponseDTO;
import ovh.kocproz.mdpages.page.repository.PageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageService {
    private static final int PAGES_PER_PAGE = 20; //pagination. Great naming, I know

    private PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public PaginationInfoResponseDTO getPaginationInfo(boolean loggedIn) {
        int count = Math.toIntExact(loggedIn ? pageRepository.countAll() : pageRepository.countAllByVisibility(Visibility.PUBLIC));
        return new PaginationInfoResponseDTO(
                count / PAGES_PER_PAGE + (count % PAGES_PER_PAGE > 0 ? 1 : 0),
                count,
                PAGES_PER_PAGE);
    }

    public PageListResponseDTO listPages(int pagination, boolean loggedIn) {
        List<PageModel> pages = (loggedIn ? pageRepository.findAll(new PageRequest(pagination, PAGES_PER_PAGE)).getContent() :
                pageRepository.findAllByVisibility(Visibility.PUBLIC, new PageRequest(pagination, PAGES_PER_PAGE)).getContent());
        return new PageListResponseDTO(
                pages.stream().map(pageModel -> new PageInfoResponseDTO(
                        pageModel.getCode(),
                        pageModel.getTitle(),
                        pageModel.getTags().stream().map(TagModel::getName).collect(Collectors.toList())
                )).collect(Collectors.toList()),
                pagination
        );
    }
}
