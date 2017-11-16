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

    //TODO Prevent empty names in javascript

    public PageModel addPage(String pageName,
                             String pageContent,
                             PageModel.Visibility visibility,
                             List<String> tags) {
        String stringId = Util.randomString(8);
        PageModel page = updatePage(pageName, pageContent, visibility, tags, stringId);
        page.setCreationDate(new Date());

        tagService.setTags(tags, page);
        pageRepository.save(page);
        return page;
    }

    public PageModel updatePage(String pageName,
                                String pageContent,
                                PageModel.Visibility visibility,
                                List<String> tags,
                                String stringId) {
        PageModel page = pageRepository.findOneByStringId(stringId);
        boolean exists = page != null;
        if (!exists)
            page = new PageModel();

        if (pageName.trim().isEmpty())
            page.setName(Util.randomTitle(4));
        else
            page.setName(pageName);


        page.setStringId(stringId);
        page.setContent(pageContent);
        page.setVisibility(visibility);
        page.setLastEdited(new Date());

        if (exists) {
            tagService.setTags(tags, page);
            pageRepository.save(page);
        }
        return page;
    }

}
