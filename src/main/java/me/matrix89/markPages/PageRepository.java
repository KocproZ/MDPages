package me.matrix89.markPages;

import org.springframework.data.repository.CrudRepository;

public interface PageRepository extends CrudRepository<PageModel, Integer> {
    PageModel getByName(String name);

}
