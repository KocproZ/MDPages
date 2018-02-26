package ovh.kocproz.markpages.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${domain}")
    String domain;
    @Value("${openid.accessTokenUri}")
    String accessTokenUri;
    @Value("${openid.userAuthorizationUri}")
    String userAuthorizationUri;


    @Bean
    public OAuth2ProtectedResourceDetails openId() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId("mdPages");
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Collections.singletonList("openid"));
        details.setPreEstablishedRedirectUri(domain + "/login/openid");
        details.setUseCurrentUri(false);
        return details;
    }

    @Bean
    public OAuth2RestTemplate openIdTemplate(@Qualifier("oauth2ClientContext") OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(openId(), clientContext);
    }

}
