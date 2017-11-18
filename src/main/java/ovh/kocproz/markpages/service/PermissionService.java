package ovh.kocproz.markpages.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.data.model.PageMaintainerModel;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.PermissionModel;
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

    public boolean hasPermission(UserModel user, PermissionModel.Permission permission) {
        for (PermissionModel p : user.getPermissions()) {
            if (p.getPermission() == permission)
                return true;
        }
        return false;
    }

    public boolean hasPermission(Authentication auth, PermissionModel.Permission permission) {
        for (GrantedAuthority a : auth.getAuthorities()) {
            if (a.getAuthority().equals(permission.toString()))
                return true;
        }
        return false;
    }

    public boolean canEdit(UserModel user, PageModel page) {
        if (hasPermission(user, PermissionModel.Permission.MODERATE)) return true;

        PageMaintainerModel maintainerModel = maintainerRepository.findFirstByPageAndUser(page, user);
        if (maintainerModel == null) return false;
        return maintainerModel.getRole() == PageMaintainerModel.Role.OWNER
                || maintainerModel.getRole() == PageMaintainerModel.Role.MAINTAINER;
    }

    public PageMaintainerModel getRole(String pageStringId, String user) {
        return maintainerRepository.findFirstByPage_StringIdAndUser(pageStringId, userRepository.getByUsername(user));
    }
}
