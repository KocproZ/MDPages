package ovh.kocproz.mdpages.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

/**
 * @author KocproZ
 * Created 2018-02-19 at 08:29
 */
@Configuration
@EnableOAuth2Client
public class OpenIdClientConfig {

    @Value("${domain}")
    private String domain;
    @Value("${openid.accessTokenUri}")
    private String accessTokenUri;
    @Value("${openid.userAuthorizationUri}")
    private String userAuthorizationUri;
    @Value("${openid.clientId}")
    private String clientId;
    @Value("${openid.clientSecret}")
    private String clientSecret;

    @Bean
    public OAuth2ProtectedResourceDetails openId() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId(clientId);
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setClientSecret(clientSecret);
        details.setScope(Arrays.asList("openid", "profile"));
        details.setPreEstablishedRedirectUri(domain + "/login/openid");
        details.setUseCurrentUri(false);
        return details;
    }

    @Bean
    public OAuth2RestTemplate openIdTemplate(@Qualifier("oauth2ClientContext") OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(openId(), clientContext);
    }

}
