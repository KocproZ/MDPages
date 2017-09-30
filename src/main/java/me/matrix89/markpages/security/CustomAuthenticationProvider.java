package me.matrix89.markpages.security;

import me.matrix89.markpages.model.UserModel;
import me.matrix89.markpages.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Pbkdf2PasswordEncoder passwordEncoder;

    @Autowired
    Logger logger;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserModel user = userRepository.getByUsername(username);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
