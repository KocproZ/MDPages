package ovh.kocproz.markpages.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kacper
 * Created 11/16/17
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getUsersContaining(String fragment) {
        Iterable<UserModel> users = userRepository.findAllByUsernameContaining(
                fragment,
                new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "username")));
        return convert(users);
    }

    private List<String> convert(Iterable<UserModel> users) {
        List<String> userNames = new ArrayList<>();
        for (UserModel user : users) {
            userNames.add(user.getUsername());
        }
        return userNames;
    }

}
