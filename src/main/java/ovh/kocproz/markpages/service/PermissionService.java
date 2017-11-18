package ovh.kocproz.markpages.service;

import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.data.model.PageMaintainerModel;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.markpages.data.repository.UserRepository;

/**
 * @author Hubertus
 * Created 27.10.2017
 */
@Service
public class PermissionService {

    private PageMaintainerRepository maintainerRepository;
    private UserRepository userRepository;

    public PermissionService(PageMaintainerRepository maintainerRepository, UserRepository userRepository) {
        this.maintainerRepository = maintainerRepository;
        this.userRepository = userRepository;
    }

    public boolean canEdit(UserModel user, PageModel page) {
        if (user.getRole().equals("ROLE_ADMIN")) return true;

        PageMaintainerModel maintainerModel = maintainerRepository.findFirstByPageAndUser(page, user);
        if (maintainerModel == null) return false;
        return maintainerModel.getRole() == PageMaintainerModel.Role.OWNER
                || maintainerModel.getRole() == PageMaintainerModel.Role.MAINTAINER;
    }

    public boolean canUpload(UserModel user){
        //TODO implement permissions

        return true;
    }

    public PageMaintainerModel getRole(String pageStringId, String user) {
        return maintainerRepository.findFirstByPage_StringIdAndUser(pageStringId, userRepository.getByUsername(user));
    }
}
