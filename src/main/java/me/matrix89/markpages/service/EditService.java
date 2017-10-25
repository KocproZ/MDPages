package me.matrix89.markpages.service;

import me.matrix89.markpages.data.model.PageMaintainerModel;
import me.matrix89.markpages.data.model.PageModel;
import me.matrix89.markpages.data.model.UserModel;
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

    public boolean canEdit(UserModel user, PageModel page) {
        if (user.getRole().equals("ROLE_ADMIN")) return true;

        PageMaintainerModel maintainerModel = maintainerRepository.findFirstByPageAndUser(page, user);
        if (maintainerModel == null) return false;
        return maintainerModel.getRole() == PageMaintainerModel.Role.OWNER
                || maintainerModel.getRole() == PageMaintainerModel.Role.MAINTAINER;
    }

}
