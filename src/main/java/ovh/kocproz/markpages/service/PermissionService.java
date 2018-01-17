package ovh.kocproz.markpages.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.Permission;
import ovh.kocproz.markpages.data.model.*;
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

    public PermissionService(PageMaintainerRepository maintainerRepository,
                             UserRepository userRepository) {
        this.maintainerRepository = maintainerRepository;
        this.userRepository = userRepository;
    }

    /**
     * Returns true if user has given permission
     *
     * @param user       user object to check permission
     * @param permission permission to check
     * @return whether  permission is present or not
     * @see Permission
     */
    public boolean hasPermission(UserModel user, Permission permission) {
        for (PermissionModel p : user.getPermissions()) {
            if (p.getPermission() == permission)
                return true;
        }
        return false;
    }

    /**
     * Returns true if auth contains given permission
     *
     * @param auth       authentication object containing permissions
     * @param permission permission to check
     * @return whether permission is present or not
     * @see Permission
     */
    public boolean hasPermission(Authentication auth, Permission permission) {
        for (GrantedAuthority a : auth.getAuthorities()) {
            if (a.getAuthority().equals(permission.toString()))
                return true;
        }
        return false;
    }

    /**
     * Returns true if user has given permission
     *
     * @param username   username
     * @param permission permission to check
     * @return whether permission is present or not
     * @see Permission
     */
    public boolean hasPermission(String username, Permission permission) {
        UserModel user = userRepository.getByUsername(username);
        return hasPermission(user, permission);
    }

    /**
     * Returns true if user is maintainer of the page or has permission MODERATE
     *
     * @param user user object
     * @param page page object
     * @return true if user can edit the page
     * @see Permission
     * @see PageModel
     */
    public boolean canEdit(UserModel user, PageModel page) {
        if (hasPermission(user, Permission.MODERATE)) return true;

        PageMaintainerModel maintainerModel = maintainerRepository.findFirstByPageAndUser(page, user);
        if (maintainerModel == null) return false;
        return maintainerModel.getRole() == PageMaintainerModel.Role.OWNER
                || maintainerModel.getRole() == PageMaintainerModel.Role.MAINTAINER;
    }

    public boolean canUpload(UserModel user) {
        return hasPermission(user, Permission.UPLOAD);
    }

    public boolean canUpdateFile(String username, FileModel f) {
        UserModel userModel = userRepository.getByUsername(username);
        return canUpdateFile(userModel, f);
    }

    /**
     * Returns true i user can update file
     *
     * @param u UserModel to get permissions
     * @param f FileModel to get owner
     * @return false if UserModel or FileModel is null
     */
    public boolean canUpdateFile(UserModel u, FileModel f) {
        if (u == null || f == null) return false;
        return hasPermission(u, Permission.MODERATE) || f.getCreator().equals(u);
    }

    /**
     * Returns user role for the page
     *
     * @param pageCode 8-characters long page code
     * @param username username to check role
     * @return role of the user
     * @see ovh.kocproz.markpages.data.model.PageMaintainerModel.Role
     */
    public PageMaintainerModel.Role getRole(String pageCode, String username) {
        PageMaintainerModel model = maintainerRepository
                .findFirstByPage_CodeAndUser(pageCode, userRepository.getByUsername(username));
        return model == null ? null : model.getRole();
    }

    public boolean canEditMaintainers(String pageCode, String username) {
        if (getRole(pageCode, username) == PageMaintainerModel.Role.OWNER) return true;
        else {
            UserModel user = userRepository.getByUsername(username);
            for (PermissionModel model : user.getPermissions()) {
                if (model.getPermission() == Permission.MODERATE) return true;
            }
        }
        return false;
    }
}
