package ovh.kocproz.mdpages.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ovh.kocproz.mdpages.data.model.PermissionModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.service.UserService;
import ovh.kocproz.mdpages.user.service.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAutheticationFilter extends GenericFilterBean {

    private String ROLE_PREFIX = "ROLE_";
    private JwtTokenService tokenService;
    private UserService userService;

    public JwtAutheticationFilter(JwtTokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest) request).getHeader("Authorization");

        try {

            Claims claims = tokenService.parse(token.substring(0, 7)/*remove "Bearer "prefix*/);

            UserModel user = userService.getUser(claims.getSubject());

            List<GrantedAuthority> authorities = new ArrayList<>(user.getPermissions().stream()
                    .map(PermissionModel::getPermission)
                    .map(permission -> new SimpleGrantedAuthority(ROLE_PREFIX + permission))
                    .collect(Collectors.toList()));

            //TODO check if user active
            /*if (user.isActive)*/ authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "USER"));

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
