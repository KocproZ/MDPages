package ovh.kocproz.mdpages.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ovh.kocproz.mdpages.Permission;
import ovh.kocproz.mdpages.data.dto.admin.UserPermissionsDTO;
import ovh.kocproz.mdpages.data.model.PermissionModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.data.repository.PermissionRepository;
import ovh.kocproz.mdpages.data.repository.UserRepository;
import ovh.kocproz.mdpages.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hubertus
 * Created 26.01.2018
 */
@Service
public class PermissionEditorService {

    private UserRepository userRepository;
    private PermissionRepository permissionRepository;

    public PermissionEditorService(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    public Long countUsers(String search) {
        long count;
        count = search != null && search.length() > 0 ?
                userRepository.countAllByUsernameContaining(search) : userRepository.count();
        return count / 20 + (count % 20 > 0 ? 1 : 0);//TODO Ask why 20
    }

    public List<UserPermissionsDTO> getUsers(String search, int page) {

        List<UserModel> users = search != null && search.length() > 0 ?
                userRepository.findAllByUsernameContaining(
                        search,
                        new PageRequest(page - 1, 10, Sort.Direction.ASC, "username"))
                        .getContent() :
                userRepository.findAll(
                        new PageRequest(page - 1, 10, Sort.Direction.ASC, "username")
                ).getContent();
        return pack(users);
    }

    private List<UserPermissionsDTO> pack(List<UserModel> userModels) {
        List<UserPermissionsDTO> dtos = new ArrayList<>();
        for (UserModel userModel : userModels) {
            UserPermissionsDTO dto = new UserPermissionsDTO();

            List<Permission> permissions = new ArrayList<>();
            for (PermissionModel permissionModel : userModel.getPermissions()) {
                permissions.add(permissionModel.getPermission());
            }
            dto.setPermissions(permissions);
            dto.setUsername(userModel.getUsername());
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public void updatePermissions(UserPermissionsDTO userPermissionsDTO) {
        UserModel user = userRepository.getByUsername(userPermissionsDTO.getUsername());
        if (user == null) throw new UserNotFoundException();

        permissionRepository.deleteAllByUser(user);
        for (Permission p : userPermissionsDTO.getPermissions()) {
            permissionRepository.save(new PermissionModel(user, p));
        }
    }

}
