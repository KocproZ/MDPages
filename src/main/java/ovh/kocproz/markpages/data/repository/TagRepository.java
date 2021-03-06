package ovh.kocproz.markpages.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ovh.kocproz.markpages.data.model.TagModel;

import java.util.List;

public interface TagRepository extends CrudRepository<TagModel, Long> {
    TagModel findFirstByName(String name);

    List<TagModel> findAllByNameContaining(String name, Pageable pageable);

    List<TagModel> findAllByNameContaining(String name);

}
