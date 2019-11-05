package ovh.kocproz.mdpages.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ovh.kocproz.mdpages.Visibility;
import ovh.kocproz.mdpages.data.dto.PageDTO;
import ovh.kocproz.mdpages.data.model.PageModel;
import ovh.kocproz.mdpages.data.model.TagModel;
import ovh.kocproz.mdpages.page.repository.PageRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hubertus
 * Created 17.11.2017
 */
@Service
public class SearchService {

    private PageRepository pageRepository;

    public SearchService(PageRepository pageRepository) {

        this.pageRepository = pageRepository;
    }

    public List<PageDTO> searchByTag(String tag, int page, boolean loggedIn) {
        List<PageModel> pageModels = loggedIn ?
                pageRepository.findAllByTagName(
                        tag,
                        new PageRequest(page - 1, 20, Sort.Direction.ASC, "title")).getContent() :
                pageRepository.findAllByTagNameAndVisibility(
                        tag,
                        Visibility.PUBLIC,
                        new PageRequest(page - 1, 20, Sort.Direction.ASC, "title")).getContent();
        return repack(pageModels);
    }

    public Long countPagesByTag(String tag, boolean loggedIn) {
        long count = loggedIn ?
                pageRepository.countAllByTagName(tag) :
                pageRepository.countAllByTagNameAndVisibility(tag, Visibility.PUBLIC);
        return count / 20 + (count % 20 > 0 ? 1 : 0);
    }

    /**
     * @deprecated
     */
    public Long countPages(boolean loggedIn) {
        long count = loggedIn ? pageRepository.countAll() : pageRepository.countAllByVisibility(Visibility.PUBLIC);
        return count / 20 + (count % 20 > 0 ? 1 : 0);//TODO Ask why 20
                                                        // seemed more or less ok, though maybe it should be possible to choose a different value
    }

    public List<PageDTO> getPages(int page, boolean loggedIn) {
        List<PageModel> pageModels = loggedIn ?
                pageRepository.findAll(
                        new PageRequest(page - 1, 20, Sort.Direction.ASC, "title")).getContent()
                :
                pageRepository.findAllByVisibility(
                        Visibility.PUBLIC,
                        new PageRequest(page - 1, 20, Sort.Direction.ASC, "title")).getContent();
        return repack(pageModels);
    }

    public List<PageDTO> searchPages(String query, boolean loggedIn, int page) {
        List<PageModel> pageModels = loggedIn ?
                pageRepository.findAllByTitleContaining(
                        query,
                        new PageRequest(page - 1, 20, Sort.Direction.ASC, "title")).getContent()
                :
                pageRepository.findAllByVisibilityAndTitleContaining(
                        Visibility.PUBLIC,
                        query,
                        new PageRequest(page - 1, 20, Sort.Direction.ASC, "title")).getContent();

        return repack(pageModels);
    }

    public long countPagesContaining(String query, boolean loggedIn) {
        long count = loggedIn ?
                pageRepository.countAllByTitleContaining(query)
                :
                pageRepository.countAllByVisibilityAndTitleContaining(Visibility.PUBLIC, query);
        return count / 20 + (count % 20 > 0 ? 1 : 0);
    }

    private List<PageDTO> repack(List<PageModel> models) {
        List<PageDTO> dtos = new ArrayList<>();

        for (PageModel m : models) {
            List<String> tags = new ArrayList<>();
            for (TagModel t : m.getTags()) {
                tags.add(t.getName());
            }
            dtos.add(new PageDTO(m.getTitle(), tags, m.getCode()));
        }
        return dtos;
    }
}
