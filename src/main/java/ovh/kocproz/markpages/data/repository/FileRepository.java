package ovh.kocproz.markpages.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.kocproz.markpages.Visibility;
import ovh.kocproz.markpages.data.model.FileModel;

import java.util.List;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
public interface FileRepository extends JpaRepository<FileModel, Long> {
    List<FileModel> findAllByVisibility(Visibility visibility);
    FileModel findFirstByCode(String code);
}
