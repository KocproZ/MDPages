package ovh.kocproz.mdpages.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import ovh.kocproz.mdpages.service.UserService;
import ovh.kocproz.mdpages.user.service.JwtTokenService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomAuthenticationProvider authProvider;
    private CustomUserDetailsService userDetailsService;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    private OAuth2RestTemplate restTemplate;
    private UserService userService;
    private JwtTokenService tokenService;

    public WebSecurityConfig(CustomAuthenticationProvider authProvider,
                             CustomUserDetailsService userDetailsService,
                             OAuth2RestTemplate restTemplate,
                             UserService userService,
                             JwtTokenService tokenService) {
        this.authProvider = authProvider;
        this.userDetailsService = userDetailsService;
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Bean
    public OpenIdConnectFilter openIdConnectFilter() {
        OpenIdConnectFilter filter = new OpenIdConnectFilter("/login/openid");
        filter.setRestTemplate(restTemplate);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAfter(openIdConnectFilter(), OAuth2ClientContextFilter.class)
                .addFilterBefore(new JwtAutheticationFilter(tokenService, userService), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//                .ignoringAntMatchers("/api/*")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/admin", "/admin/*")
//                .hasAuthority(Permission.ADMIN.toString())
//                .antMatchers("/files/upload", "/files/update")
//                .hasAuthority(Permission.UPLOAD.toString())
//                .antMatchers("/edit", "/edit/*", "/tags", "/tags/*")
//                .hasAnyAuthority(Permission.CREATE.toString(),
//                        Permission.MODERATE.toString())
//                .antMatchers("/", "/files", "/files/*")
//                .permitAll()
//                .antMatchers("/user/profile")
//                .authenticated()
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll()
//                .and()
//                .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login/openid"))
//                .and()
//                .rememberMe().rememberMeParameter("remember-me")
//                .tokenRepository(persistentTokenRepository()).tokenValiditySeconds(86400)
//                .userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

}
