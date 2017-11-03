package ovh.kocproz.markpages.service;

import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.data.repository.PageMaintainerRepository;

/**
 * @author Hubertus
 * Created 25.10.2017
 */
@Service
public class EditService {

    private PageMaintainerRepository maintainerRepository;

    public EditService(PageMaintainerRepository maintainerRepository) {
        this.maintainerRepository = maintainerRepository;
    }

    //TODO move logic from EditorController

}
