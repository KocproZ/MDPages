package ovh.kocproz.markpages.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.kocproz.markpages.data.model.FileModel;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
public interface FileRepository extends JpaRepository<FileModel, Long> {

    FileModel findFirstByCode(String code);
}
