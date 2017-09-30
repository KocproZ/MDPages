package me.matrix89.markpages.repository;

import me.matrix89.markpages.model.PageModel;
import org.springframework.data.repository.CrudRepository;

public interface PageRepository extends CrudRepository<PageModel, Integer> {
    PageModel getByName(String name);

}
