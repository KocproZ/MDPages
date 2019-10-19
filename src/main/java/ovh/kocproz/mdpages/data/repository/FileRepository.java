package ovh.kocproz.mdpages.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.kocproz.mdpages.Visibility;
import ovh.kocproz.mdpages.data.model.FileModel;

import java.util.List;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
public interface FileRepository extends JpaRepository<FileModel, Long> {
    List<FileModel> findAllByVisibility(Visibility visibility);
    FileModel findFirstByCode(String code);
}
