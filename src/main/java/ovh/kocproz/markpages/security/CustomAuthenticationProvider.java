package ovh.kocproz.markpages.security;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;
import ovh.kocproz.markpages.Permission;
import ovh.kocproz.markpages.data.model.PermissionModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.UserRepository;
import ovh.kocproz.markpages.service.UserService;

import java.util.ArrayList;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private UserRepository userRepository;
    private UserService userService;
    private Pbkdf2PasswordEncoder passwordEncoder;
    private Logger logger;

    public CustomAuthenticationProvider(UserRepository userRepository,
                                        UserService userService,
                                        Pbkdf2PasswordEncoder passwordEncoder,
                                        Logger logger) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserModel user = userRepository.getByUsername(username);
        if (user == null) throw new UsernameNotFoundException("No user with username " + username);
        if (user.getOpenidSubject() == null && passwordEncoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(user.getUsername(), password, getUserAuthorities(user));

        return null;
    }

    private ArrayList<GrantedAuthority> getUserAuthorities(UserModel user) {
        ArrayList<GrantedAuthority> ar = new ArrayList<>();
        Set<PermissionModel> permissions = user.getPermissions();
        for (PermissionModel p : permissions)
            ar.add(new SimpleGrantedAuthority(p.getPermission().toString()));
        return ar;
    }

    public Authentication authenticateOpenId(String subject, String username) {
        UserModel user = userRepository.getByOpenidSubject(subject);
        if (user == null) {
            if (userRepository.getByUsername(username) != null) return null;
            user = userService.createUser(username,
                    null,
                    new Permission[]{Permission.CREATE, Permission.UPLOAD},
                    subject);
        }

        return new OpenIdAuthenticationToken(username, getUserAuthorities(user));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
