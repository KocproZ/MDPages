package ovh.kocproz.mdpages.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import ovh.kocproz.mdpages.Permission;
import ovh.kocproz.mdpages.data.model.PageMaintainerModel;
import ovh.kocproz.mdpages.data.model.PageModel;
import ovh.kocproz.mdpages.data.model.PermissionModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.mdpages.data.repository.PermissionRepository;
import ovh.kocproz.mdpages.data.repository.UserRepository;
import ovh.kocproz.mdpages.user.dto.SignInDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author KocproZ
 * Created 11/16/17
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private PermissionRepository permissionRepository;
    private Pbkdf2PasswordEncoder passwordEncoder;
    private PageMaintainerRepository maintainerRepository;

    public UserService(UserRepository userRepository,
                       PermissionRepository permissionRepository,
                       Pbkdf2PasswordEncoder passwordEncoder,
                       PageMaintainerRepository maintainerRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
        this.maintainerRepository = maintainerRepository;
    }

    public UserModel getUser(String username) {
        UserModel user = userRepository.getByUsername(username);
//        if(user == null) //TODO throw exception
        return user;
    }

    public UserModel findUser(String username) {
        return userRepository.getByUsername(username);
    }

    public UserModel createUser(String username,
                                String password,
                                Permission[] permissions) {
        return createUser(username, password, permissions, null);
    }

    public UserModel createUser(String username,
                                String password,
                                Permission[] permissions,
                                String openidSubject) {
        UserModel user = new UserModel();
        user.setUsername(username);
        user.setPassword(password != null ? passwordEncoder.encode(password) : "");
        user.setOpenidSubject(openidSubject);
        userRepository.save(user);
        for (Permission permission : permissions) {
            PermissionModel pm = new PermissionModel();
            pm.setPermission(permission);
            pm.setUser(user);
            permissionRepository.save(pm);
            user.addPermission(pm);
        }
        return user;
    }

    public boolean verifyUserCredentials(SignInDTO signInDTO){
        UserModel user = findUser(signInDTO.getUsername());
        if(user == null) return false;
        return passwordEncoder.matches(signInDTO.getPassword(), user.getPassword());
    }

    public Set<PermissionModel> getUserPermissions(UserModel user) {
        return permissionRepository.findAllByUser(user);
    }

    public List<String> getPageMaintainers(PageModel page) {
        List<PageMaintainerModel> pms = maintainerRepository
                .findAllByPageAndRole(page, PageMaintainerModel.Role.MAINTAINER);
        if (pms == null) return new ArrayList<>();
        return convertMaintainers(pms);
    }

    public String getPageOwner(PageModel page) {
        PageMaintainerModel owner = maintainerRepository
                .findFirstByPageAndRole(page, PageMaintainerModel.Role.OWNER);
        if (owner == null) return null;
        return owner.getUser().getUsername();
    }

    public List<String> getUsersContaining(String fragment) {
        Iterable<UserModel> users = userRepository.findAllByUsernameContaining(
                fragment,
                new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "username")));
        return convertUsers(users);
    }

    private List<String> convertUsers(Iterable<UserModel> users) {
        List<String> userNames = new ArrayList<>();
        for (UserModel user : users) {
            userNames.add(user.getUsername());
        }
        return userNames;
    }

    private List<String> convertMaintainers(Iterable<PageMaintainerModel> pms) {
        List<String> userNames = new ArrayList<>();
        for (PageMaintainerModel pm : pms) {
            userNames.add(pm.getUser().getUsername());
        }
        return userNames;
    }

}
