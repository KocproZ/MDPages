package ovh.kocproz.markpages.data.repository;

import org.springframework.data.repository.CrudRepository;
import ovh.kocproz.markpages.data.model.PermissionModel;
import ovh.kocproz.markpages.data.model.UserModel;

import java.util.Set;

/**
 * @author KocproZ
 * Created 11/18/17
 */
public interface PermissionRepository extends CrudRepository<PermissionModel, Long> {
    Set<PermissionModel> findAllByUser(UserModel user);
    void deleteAllByUser(UserModel userModel);
}
