package me.matrix89.markpages.repository;

import me.matrix89.markpages.model.TagModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<TagModel, Integer> {
    List<TagModel> findAllByName(String name);

    List<TagModel> findAllByNameContaining(String name);
}
