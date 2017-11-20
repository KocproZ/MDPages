package ovh.kocproz.markpages.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.Visibility;
import ovh.kocproz.markpages.data.dto.PageDTO;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.TagModel;
import ovh.kocproz.markpages.data.repository.PageRepository;

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
                        new PageRequest(page - 1, 20, Sort.Direction.ASC, "name")).getContent() :
                pageRepository.findAllByTagNameAndVisibility(
                        tag,
                        Visibility.PUBLIC,
                        new PageRequest(page - 1, 20, Sort.Direction.ASC, "name")).getContent();
        List<PageDTO> dtos = new ArrayList<>();

        for (PageModel m : pageModels) {
            List<String> tags = new ArrayList<>();
            for(TagModel t: m.getTags()){
                tags.add(t.getName());
            }
            dtos.add(new PageDTO(m.getName(), tags, m.getStringId()));
        }
        return dtos;
    }

    public Long countPagesByTag(String tag, boolean loggedIn) {
        long count = loggedIn ?
                pageRepository.countAllByTagName(tag) :
                pageRepository.countAllByTagNameAndVisibility(tag, Visibility.PUBLIC);
        return count / 20 + (count % 20 > 0 ? 1 : 0);
    }
}
