package ovh.kocproz.mdpages.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.kocproz.mdpages.data.model.PageMaintainerModel;
import ovh.kocproz.mdpages.data.model.PageModel;
import ovh.kocproz.mdpages.data.model.UserModel;

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

    PageMaintainerModel findFirstByPageAndRole(PageModel page, PageMaintainerModel.Role role);

    PageMaintainerModel findFirstByPageAndUser(PageModel page, UserModel user);

    PageMaintainerModel findFirstByPage_CodeAndUser(String pageCode, UserModel user);

    void deleteAllByRoleAndPage(PageMaintainerModel.Role role, PageModel page);

    void deleteAllByPage(PageModel pageModel);
}
