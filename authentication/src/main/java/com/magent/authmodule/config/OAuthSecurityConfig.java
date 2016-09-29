package com.magent.authmodule.config;

import com.magent.authmodule.config.filter.SimpleCORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.Arrays;

/**
 * Created by Sergey on 05.08.2015.
 *
 * @version 1.1.1
 * @apiNote BE CAREFUL each application which will be use this jar as security module must override (void configure) for their matchers and roles
 * @apiNote each value in configuration must be present in any properties file which point general application
 */
@Configuration
@EnableResourceServer
@EnableWebMvcSecurity
@ComponentScan("com.magent.authmodule.config")
public class OAuthSecurityConfig extends ResourceServerConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthSecurityConfig.class);

    private static final String RESOURCE_ID = "magent";

    @Value("${oauth.token.server}")
    private String tokenServer;

    @Value("${oauth.client.id}")
    private String clientId;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    @Value("${oauth.otp.clientid}")
    private String otpClientId;
    // current value must be the same as on oauthServer
    @Value("${oauth.otp.client.secret}")
    private String otpClientSecret;


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setResourceId(RESOURCE_ID);
        authenticationManager.setTokenServices(tokenServices());
        return authenticationManager;
    }


    @Bean(name = "tokenServices")
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        tokenServices.setCheckTokenEndpointUrl(tokenServer + "/oauth/check_token");
        return tokenServices;
    }

    @Bean
    public OauthClientCredentials clientCredentialsOtp() {
        LOGGER.debug("Using OAuth server for gets token with otp: {}", tokenServer);
        return new OauthClientCredentials(tokenServer, otpClientId, otpClientSecret);
    }

    @Bean
    public OauthClientCredentials clientCredentials() {
        LOGGER.debug("Using OAuth server for gets token without otp: {}", tokenServer);
        return new OauthClientCredentials(tokenServer, clientId, clientSecret);
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID).authenticationManager(authenticationManagerBean());
    }


    @Bean
    public Filter simpleCORSFilter() {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(Arrays.asList(new SimpleCORSFilter()));
        return filter;
    }

    /**
     *
     * @see org.springframework.security.config.annotation.web.builders.HttpSecurity
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
