package ovh.kocproz.markpages.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Collections;

/**
 * @author KocproZ
 * Created 2018-02-19 at 08:29
 */
@Configuration
@EnableOAuth2Client
public class OpenIdClientConfig {

    @Bean
    public OAuth2ProtectedResourceDetails openId() {//TODO: getting values from config
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId("mdPages");
        details.setAccessTokenUri("https://keycloak.starchasers.ovh/auth/realms/syf/protocol/openid-connect/token");
        details.setUserAuthorizationUri("https://keycloak.starchasers.ovh/auth/realms/syf/protocol/openid-connect/auth");
        details.setScope(Collections.singletonList("openid"));
        details.setPreEstablishedRedirectUri("http://localhost:8080/login/openid");
        details.setUseCurrentUri(false);
        return details;
    }

    @Bean
    public OAuth2RestTemplate openIdTemplate(@Qualifier("oauth2ClientContext") OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(openId(), clientContext);
    }

}
