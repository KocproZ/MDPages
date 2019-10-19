package ovh.kocproz.mdpages.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ovh.kocproz.mdpages.data.model.PermissionModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.data.repository.UserRepository;
import ovh.kocproz.mdpages.service.UserService;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private UserService userService;

    public CustomUserDetailsService(UserRepository userRepository,
                                    UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (PermissionModel p : user.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(p.getPermission().toString()));
        }
        return new User(username, user.getPassword(), authorities);
    }

}
