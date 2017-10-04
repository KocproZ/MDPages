package me.matrix89.markpages.repository;

import me.matrix89.markpages.model.PageModel;
import me.matrix89.markpages.model.TagModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<PageModel, Integer> {
    List<PageModel> getAllByVisibility(PageModel.Visibility visibility, Sort sort);
    List<PageModel> getAllByMaintainer_Id(Integer maintainer_id);

    List<PageModel> findAllByVisibilityAndTagsOrderByNameAsc(PageModel.Visibility visibility, TagModel tag);

    List<PageModel> findAllByNameContainingOrTagsOrderByNameAsc(String name, TagModel tag);
}
