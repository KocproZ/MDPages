package ovh.kocproz.markpages.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.kocproz.markpages.data.model.PageMaintainerModel;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.UserModel;

import java.util.List;

/**
 * @author Hubertus
 * Created 25.10.2017
 */
public interface PageMaintainerRepository extends JpaRepository<PageMaintainerModel, Long> {

    List<PageMaintainerModel> findAllByUser_Id(Long id);

    List<PageMaintainerModel> findAllByPage_Id(Long id);

    List<PageMaintainerModel> findAllByUser(UserModel user);

    List<PageMaintainerModel> findAllByPageAndRole(PageModel page, PageMaintainerModel.Role role);

    PageMaintainerModel findFirstByRoleAndPage(PageMaintainerModel.Role role, PageModel page);
    PageMaintainerModel findFirstByPageAndUser(PageModel page, UserModel user);
    PageMaintainerModel findFirstByPage_StringIdAndUser(String pageStringId, UserModel user);

    void deleteAllByRoleAndPage(PageMaintainerModel.Role role, PageModel page);
}
