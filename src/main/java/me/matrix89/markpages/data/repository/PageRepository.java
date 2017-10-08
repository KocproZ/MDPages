package me.matrix89.markpages.data.repository;

import me.matrix89.markpages.data.model.PageModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PageRepository extends JpaRepository<PageModel, Integer> {
    PageModel findAllByStringId(String stringId);

    List<PageModel> findAllByMaintainer_Id(Integer maintainer_id);
    List<PageModel> findAllByVisibility(PageModel.Visibility visibility, Sort sort);
    List<PageModel> findAllByVisibilityNot(PageModel.Visibility visibility, Sort sort);

    @Query(value = "select p from Page p " +
            "join p.tags as t " +
            "where t.name in :names and p.visibility = :visibility " +
            "group by p having count(t) = :names_size ")
    Set<PageModel> findAllByVisibilityAndTagNames(@Param("visibility") PageModel.Visibility visibility,
                                                  @Param("names") List<String> tags_names,
                                                  @Param("names_size") long tags_names_size);

    default Set<PageModel> findAllByVisibilityAndTagNames(PageModel.Visibility visibility, List<String> tags_names) {
        return findAllByVisibilityAndTagNames(visibility, tags_names, tags_names.size());
    }

    @Query(value = "select p from Page p " +
            "join p.tags as t " +
            "where t.name in :names and p.visibility <> :visibility " +
            "group by p having count(t) = :names_size ")
    Set<PageModel> findAllByVisibilityNotAndTagNames(@Param("visibility") PageModel.Visibility visibility,
                                                     @Param("names") List<String> tags_names,
                                                     @Param("names_size") long tags_names_size);

    default Set<PageModel> findAllByVisibilityNotAndTagNames(PageModel.Visibility visibility, List<String> tags_names) {
        return findAllByVisibilityNotAndTagNames(visibility, tags_names, tags_names.size());
    }

    List<PageModel> findAllByVisibilityAndNameContaining(PageModel.Visibility visibility, String name, Sort sort);
    List<PageModel> findAllByVisibilityNotAndNameContaining(PageModel.Visibility visibility, String name, Sort sort);
}
