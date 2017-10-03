package me.matrix89.markpages.repository;

import me.matrix89.markpages.model.PageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<PageModel, Integer> {
    List<PageModel> getAllByVisibility(String visibility);
    List<PageModel> getAllByMaintainer_Id(Integer maintainer_id);
}
