package ovh.kocproz.markpages.service;

import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.Util;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.markpages.data.repository.PageRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Hubertus
 * Created 25.10.2017
 */
@Service
public class EditService {

    private PageMaintainerRepository maintainerRepository;
    private PageRepository pageRepository;
    private TagService tagService;

    public EditService(PageMaintainerRepository maintainerRepository,
                       PageRepository pageRepository,
                       TagService tagService) {
        this.maintainerRepository = maintainerRepository;
        this.pageRepository = pageRepository;
        this.tagService = tagService;
    }

    //TODO Prevent empty names

    public PageModel addOrUpdatePage(String pageName,
                                     String pageContent,
                                     PageModel.Visibility visibility,
                                     List<String> tags,
                                     String stringId) {
        PageModel page;
        if (stringId == null) {
            page = new PageModel();
            page.setStringId(Util.randomString(8));
            page.setCreationDate(new Date());
        } else {
            page = pageRepository.findAllByStringId(stringId);
        }
        page.setName(pageName);
        page.setLastEdited(new Date());
        page.setContent(pageContent);
        page.setVisibility(visibility);
        tagService.setTags(tags, page);
        pageRepository.save(page);
        return page;
    }

}
