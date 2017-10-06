package me.matrix89.markpages.data.repository;

import me.matrix89.markpages.data.model.PageModel;
import me.matrix89.markpages.data.model.TagModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<PageModel, Integer> {
    PageModel findAllByStringId(String stringId);

    List<PageModel> findAllByVisibility(PageModel.Visibility visibility, Sort sort);

    List<PageModel> findAllByVisibilityNot(PageModel.Visibility visibility, Sort sort);

    List<PageModel> findAllByMaintainer_Id(Integer maintainer_id);

    List<PageModel> findAllByVisibilityAndTags(PageModel.Visibility visibility, TagModel tag, Sort sort);

    //List<PageModel> findAllByVisibilityNotLikeOrderByNameAsc(PageModel.Visibility visibility);
    //List<PageModel> findAllByVisibilityAndNameContainingOrderByNameAsc(PageModel.Visibility visibility, String name);

    List<PageModel> findAllByVisibilityAndNameContaining(PageModel.Visibility visibility, String name, Sort sort);

    List<PageModel> findAllByVisibilityNotAndNameContaining(PageModel.Visibility visibility, String name, Sort sort);
}
