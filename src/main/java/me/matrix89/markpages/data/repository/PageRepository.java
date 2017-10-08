package me.matrix89.markpages.data.repository;

import me.matrix89.markpages.data.model.PageModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<PageModel, Integer> {
    PageModel findAllByStringId(String stringId);

    List<PageModel> findAllByMaintainer_Id(Integer maintainer_id);
    List<PageModel> findAllByVisibility(PageModel.Visibility visibility, Sort sort);
    List<PageModel> findAllByVisibilityNot(PageModel.Visibility visibility, Sort sort);

//    @Query(value = "SELECT pages.id as id, content, created, last_edited, pages.name as name, string_id, visibility, maintainer_id " +
//            "FROM tags_pages " +
//            "join pages on pages.id = tags_pages.page_id " +
//            "join tags on tags.id = tags_pages.tag_id " +
//            "where tags.name in (?2) AND visibility = ?1 " +
//            "group by tags_pages.page_id " +
//            "having count(distinct tags.name)= ?3 " +
//            "order by name ASC", nativeQuery = true)
//    List<Object[]> findAllByVisibilityAndTagNames(PageModel.Visibility visibility, List<String> tags_names, int tags_names_size);
//    @Query(value = "SELECT pages.id as id, content, created, last_edited, pages.name as name, string_id, visibility, maintainer_id " +
//            "FROM tags_pages " +
//            "join pages on pages.id = tags_pages.page_id " +
//            "join tags on tags.id = tags_pages.tag_id " +
//            "where tags.name in (?2) AND visibility <> ?1 " +
//            "group by tags_pages.page_id " +
//            "having count(distinct tags.name)= ?3 " +
//            "order by name ASC", nativeQuery = true)
//    List<Object[]> findAllByVisibilityNotAndTagNames(PageModel.Visibility visibility, List<String> tags_names, int tags_names_size);

    List<PageModel> findAllByVisibilityAndNameContaining(PageModel.Visibility visibility, String name, Sort sort);
    List<PageModel> findAllByVisibilityNotAndNameContaining(PageModel.Visibility visibility, String name, Sort sort);
}
