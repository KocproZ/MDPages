package ovh.kocproz.markpages.security;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author KocproZ
 * Created 2018-02-19 at 09:01
 */
public class OpenIdConnectFilter extends AbstractAuthenticationProcessingFilter {

    private OAuth2RestTemplate restTemplate;
    @Autowired
    CustomAuthenticationProvider auth;

    public OpenIdConnectFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(new NopeAuthManager());
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        OAuth2AccessToken accessToken;
        try {
            accessToken = restTemplate.getAccessToken();
        } catch (OAuth2Exception e) {
            throw new BadCredentialsException("Could not obtain access token", e);
        }
        try {
            String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
            Jwt tokenDecoded = JwtHelper.decode(idToken);
            Map<String, String> authInfo = new ObjectMapper().readValue(tokenDecoded.getClaims(), Map.class);

            return auth.authenticateOpenId(authInfo.get("sub"), authInfo.get("preferred_username"));
//            return new OpenIdAuthenticationToken("Sparta",//preferred_username
//                    Collections.singletonList(new SimpleGrantedAuthority("admin")));
        } catch (InvalidTokenException e) {
            throw new BadCredentialsException("Could not obtain user details from token", e);
        }
    }

    public void setRestTemplate(OAuth2RestTemplate template) {
        this.restTemplate = template;
    }

    private static class NopeAuthManager implements AuthenticationManager {

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            AuthenticationException e = new DisabledException("Nope.");
            e.printStackTrace();
            throw e;
        }
    }

}
