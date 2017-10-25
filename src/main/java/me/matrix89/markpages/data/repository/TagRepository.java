package me.matrix89.markpages.data.repository;

import me.matrix89.markpages.data.model.TagModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<TagModel, Long> {
    TagModel findFirstByName(String name);

    List<TagModel> findAllByNameContaining(String name);
}
