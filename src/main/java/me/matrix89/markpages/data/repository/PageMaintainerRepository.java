package me.matrix89.markpages.data.repository;

import me.matrix89.markpages.data.model.PageMaintainerModel;
import me.matrix89.markpages.data.model.PageModel;
import me.matrix89.markpages.data.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hubertus
 * Created 25.10.2017
 */
public interface PageMaintainerRepository extends JpaRepository<PageMaintainerModel, Long> {

    List<PageMaintainerModel> findAllByUser_Id(Long id);

    List<PageMaintainerModel> findAllByPage_Id(Long id);

    List<PageMaintainerModel> findAllByUser(UserModel user);

    PageMaintainerModel findFirstByPageAndUser(PageModel page, UserModel user);
    PageMaintainerModel findFirstByPage_StringIdAndUser(String pageStringId, UserModel user);
}
