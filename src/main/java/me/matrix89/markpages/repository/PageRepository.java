package me.matrix89.markpages.repository;

import me.matrix89.markpages.model.PageModel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PageRepository extends PagingAndSortingRepository<PageModel, Integer> {
    PageModel getByName(String name);

    List<PageModel> getAllByVisibility(String visibility);

    List<PageModel> getAllByMaintainer_Id(Integer maintainer_id);
}
