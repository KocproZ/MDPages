package ovh.kocproz.mdpages.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ovh.kocproz.mdpages.Util;
import ovh.kocproz.mdpages.Visibility;
import ovh.kocproz.mdpages.data.model.PageMaintainerModel;
import ovh.kocproz.mdpages.data.model.PageModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.mdpages.data.repository.PageRepository;
import ovh.kocproz.mdpages.data.repository.UserRepository;

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
    private UserRepository userRepository;
    private TagService tagService;

    public EditService(PageMaintainerRepository maintainerRepository,
                       PageRepository pageRepository,
                       UserRepository userRepository,
                       TagService tagService) {
        this.maintainerRepository = maintainerRepository;
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
        this.tagService = tagService;
    }

    @Transactional
    public void setMaintainers(List<String> usernames, PageModel page) {
        PageMaintainerModel owner = maintainerRepository.findFirstByPageAndRole(page, PageMaintainerModel.Role.OWNER);
        PageMaintainerModel pm;
        maintainerRepository.deleteAllByRoleAndPage(PageMaintainerModel.Role.MAINTAINER, page);
        for (String username : usernames) {
            UserModel user = userRepository.getByUsername(username);
            if (user != null) {
                pm = new PageMaintainerModel();
                pm.setUser(user);
                pm.setRole(PageMaintainerModel.Role.MAINTAINER);
                pm.setPage(page);
                if (owner == null || !user.getUsername().equals(owner.getUser().getUsername()))
                    maintainerRepository.save(pm);
            }
        }
    }

    public void setOwner(PageModel page, UserModel user) {
        PageMaintainerModel pm = new PageMaintainerModel();
        pm.setPage(page);
        pm.setUser(user);
        pm.setRole(PageMaintainerModel.Role.OWNER);
        maintainerRepository.save(pm);
    }

    public void setOwner(PageModel page, String username) {
        setOwner(page, userRepository.getByUsername(username));
    }

    public PageModel addPage(String pageTitle,
                             String pageContent,
                             Visibility visibility,
                             List<String> tags) {
        String code;
        do {
            code = Util.randomString(8);
        } while (pageRepository.findOneByCode(code) != null);
        PageModel page = updatePage(pageTitle, pageContent, visibility, tags, code);
        page.setCreationDate(new Date());

        tagService.setTags(tags, page);
        pageRepository.save(page);
        return page;
    }

    public PageModel updatePage(String pageTitle,
                                String pageContent,
                                Visibility visibility,
                                List<String> tags,
                                String code) {
        PageModel page = pageRepository.findOneByCode(code);
        boolean exists = page != null;
        if (!exists)
            page = new PageModel();

        if (pageTitle.trim().isEmpty())
            page.setTitle(Util.randomTitle(4));
        else
            page.setTitle(pageTitle);


        page.setCode(code);
        page.setContent(pageContent);
        page.setVisibility(visibility);
        page.setLastEdited(new Date());

        if (exists) {
            tagService.setTags(tags, page);
            pageRepository.save(page);
        }
        return page;
    }

    @Transactional
    public void deletePage(String code) {
        PageModel page = pageRepository.findOneByCode(code);
        maintainerRepository.deleteAllByPage(page);
        pageRepository.delete(page);
    }

}
