package com.magent.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

import static com.magent.domain.enums.UserRoles.*;

/**
 * Created by Sergey on 05.08.2015.
 */
@Configuration
@EnableResourceServer
@EnableWebMvcSecurity
@ComponentScan("com.magent.config.auth")
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
    public UsbOauthClientCredentials clientCredentialsOtp() {
        LOGGER.debug("Using OAuth server: {}", tokenServer);
        return new UsbOauthClientCredentials(tokenServer, otpClientId, otpClientSecret);
    }

    @Bean
    public UsbOauthClientCredentials clientCredentials() {
        LOGGER.debug("Using OAuth server: {}", tokenServer);
        return new UsbOauthClientCredentials(tokenServer, clientId, clientSecret);
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

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
                .antMatchers("/refresh*").permitAll()
                .antMatchers("/login/otp*").permitAll()
                .antMatchers("/signup").permitAll()

                .antMatchers(HttpMethod.GET, "/data/onboards").permitAll()

                //*SPRING 20 TASK SAP-13*//*
                .antMatchers(HttpMethod.GET,"/templates/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/templates/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.PUT,"/templates/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.DELETE,"/templates/**").hasAnyAuthority(ADMIN.toString())

                .antMatchers(HttpMethod.GET, "/assignments/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())
                .antMatchers(HttpMethod.POST, "/assignments/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())
                .antMatchers(HttpMethod.PUT, "/assignments/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())
                .antMatchers(HttpMethod.DELETE, "/assignments/**").hasAnyAuthority(ADMIN.toString())

                .antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())
                .antMatchers(HttpMethod.POST, "/users/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/users/**").hasAnyAuthority(ADMIN.toString())

                .antMatchers(HttpMethod.GET,"/data/**").hasAnyAuthority(ADMIN.toString(), REMOTE_SELLER_STAFFER.toString())
                .antMatchers(HttpMethod.PUT,"/data/**").hasAnyAuthority(ADMIN.toString(), REMOTE_SELLER_STAFFER.toString())
                .antMatchers(HttpMethod.POST,"/data/**").hasAnyAuthority(ADMIN.toString(), REMOTE_SELLER_STAFFER.toString())
                .antMatchers(HttpMethod.DELETE,"/data/**").hasAnyAuthority(ADMIN.toString(), REMOTE_SELLER_STAFFER.toString())
                //on board get by id , and modifying this info allowed only for admin According to SAP_45
                .antMatchers(HttpMethod.GET,"/data/onboards/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/data/onboards").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/data/onboards").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/data/onboards/**").hasAnyAuthority(ADMIN.toString())

                .antMatchers("/template-types/**").hasAnyAuthority(ADMIN.toString())

                .antMatchers("/tracking/**").authenticated()

                .antMatchers("/reports/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())
                .antMatchers("/testOauth/**").authenticated()

                .antMatchers("/devices/**").authenticated()

                .antMatchers(HttpMethod.GET,"/reasons/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.PUT,"/reasons/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/reasons/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.DELETE,"/reasons/**").hasAnyAuthority(ADMIN.toString())

                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
