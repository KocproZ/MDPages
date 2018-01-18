package ovh.kocproz.markpages.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ovh.kocproz.markpages.Visibility;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.TagModel;

import java.util.List;
import java.util.Set;

public interface PageRepository extends JpaRepository<PageModel, Long> {
    PageModel findOneByCode(String code);

    List<PageModel> findAllByVisibility(Visibility visibility, Sort sort);

    @Query(value = "select p from Page p " +
            "join p.tags as t " +
            "where t.name = :name ")
    Page<PageModel> findAllByTagName(@Param("name") String tag, Pageable pageable);

    @Query(value = "select p from Page p " +
            "join p.tags as t " +
            "where t.name = :name and p.visibility = :visibility ")
    Page<PageModel> findAllByTagNameAndVisibility(@Param("name") String tag,
                                                  @Param("visibility") Visibility visibility,
                                                  Pageable pageable);

    @Query(value = "select count(p) from Page p " +
            "join p.tags as t " +
            "where t.name = :name")
    Long countAllByTagName(@Param("name") String tag);

    @Query(value = "select count(p) from Page p " +
            "join p.tags as t " +
            "where t.name = :name and p.visibility = :visibility")
    Long countAllByTagNameAndVisibility(@Param("name") String tag, @Param("visibility") Visibility visibility);

    PageModel findFirstByTags(TagModel tag);

    @Query(value = "select case when count(p)>0 then true else false end from Page p where p.code = :code")
    boolean exists(@Param("code") String code);

    @Query(value = "select p from Page p " +
            "join p.tags as t " +
            "where t.name in :names and p.visibility = :visibility " +
            "group by p having count(t) = :names_size ")
    Set<PageModel> findAllByVisibilityAndTagNames(@Param("visibility") Visibility visibility,
                                                  @Param("names") List<String> tags_names,
                                                  @Param("names_size") long tags_names_size);

    default Set<PageModel> findAllByVisibilityAndTagNames(Visibility visibility, List<String> tags_names) {
        return findAllByVisibilityAndTagNames(visibility, tags_names, tags_names.size());
    }

    @Query(value = "select p from Page p " +
            "join p.tags as t " +
            "where t.name in :names and p.visibility <> :visibility " +
            "group by p having count(t) = :names_size ")
    Set<PageModel> findAllByVisibilityNotAndTagNames(@Param("visibility") Visibility visibility,
                                                     @Param("names") List<String> tags_names,
                                                     @Param("names_size") long tags_names_size);

    default Set<PageModel> findAllByVisibilityNotAndTagNames(Visibility visibility, List<String> tags_names) {
        return findAllByVisibilityNotAndTagNames(visibility, tags_names, tags_names.size());
    }

    List<PageModel> findAllByVisibilityAndTitleContaining(Visibility visibility, String titleFragment, Sort sort);

    List<PageModel> findAllByVisibilityNotAndTitleContaining(Visibility visibility, String titleFragment, Sort sort);

    Page<PageModel> findAllByVisibilityAndTitleContaining(Visibility visibility, String titleFragment, Pageable pageable);

    Page<PageModel> findAllByTitleContaining(String title, Pageable pageable);

    Long countAllByTitleContaining(String titleFragment);

    Long countAllByVisibilityAndTitleContaining(Visibility visibility, String titleFragment);

    @Query(value = "select count(p) from Page p")
    Long countAll();

    Long countAllByVisibility(Visibility visibility);

    Page<PageModel> findAllByVisibility(Visibility visibility, Pageable pageable);

    Page<PageModel> findAll(Pageable pageable);
}
