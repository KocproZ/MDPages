package me.matrix89.markpages.service;

import me.matrix89.markpages.data.repository.PageMaintainerRepository;
import org.springframework.stereotype.Service;

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
