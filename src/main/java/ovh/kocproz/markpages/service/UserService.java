package ovh.kocproz.markpages.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.data.model.PageMaintainerModel;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.markpages.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KocproZ
 * Created 11/16/17
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private PageMaintainerRepository maintainerRepository;

    public UserService(UserRepository userRepository,
                       PageMaintainerRepository maintainerRepository) {
        this.userRepository = userRepository;
        this.maintainerRepository = maintainerRepository;
    }

    public List<String> getPageMaintainers(PageModel page) {
        List<PageMaintainerModel> pms = maintainerRepository
                .findAllByPageAndRole(page, PageMaintainerModel.Role.MAINTAINER);
        if (pms == null) return new ArrayList<>();
        return convertMaintainers(pms);

    }

    public List<String> getUsersContaining(String fragment) {
        Iterable<UserModel> users = userRepository.findAllByUsernameContaining(
                fragment,
                new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "username")));
        return convertUsers(users);
    }

    private List<String> convertUsers(Iterable<UserModel> users) {
        List<String> userNames = new ArrayList<>();
        for (UserModel user : users) {
            userNames.add(user.getUsername());
        }
        return userNames;
    }

    private List<String> convertMaintainers(Iterable<PageMaintainerModel> pms) {
        List<String> userNames = new ArrayList<>();
        for (PageMaintainerModel pm : pms) {
            userNames.add(pm.getUser().getUsername());
        }
        return userNames;
    }

}
