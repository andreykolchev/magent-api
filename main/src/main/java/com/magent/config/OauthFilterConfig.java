package com.magent.config;

import com.magent.authmodule.config.OAuthSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import static com.magent.domain.enums.UserRoles.*;
import static com.magent.domain.enums.UserRoles.ADMIN;
import static com.magent.domain.enums.UserRoles.BACK_OFFICE_EMPLOYEE;

/**
 * Created on 01.09.2016.
 * @see com.magent.authmodule.config.OAuthSecurityConfig
 */
@Configuration
public class OauthFilterConfig extends OAuthSecurityConfig{

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
                .antMatchers("/refresh*").permitAll()
                .antMatchers("/login/otp*").permitAll()
                .antMatchers("/signup").permitAll()

                .antMatchers(HttpMethod.GET, "/onboards/").permitAll()

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

                .antMatchers(HttpMethod.GET,"/mobile/**").hasAnyAuthority(REMOTE_SELLER_STAFFER.toString(),SALES_AGENT_FREELANCER_LEAD_GEN.toString(),SALES_AGENT_FREELANCER.toString(),ADMIN.toString())
                .antMatchers(HttpMethod.PUT,"/mobile/**").hasAnyAuthority(REMOTE_SELLER_STAFFER.toString(),SALES_AGENT_FREELANCER_LEAD_GEN.toString(),SALES_AGENT_FREELANCER.toString(),ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/mobile/**").hasAnyAuthority(REMOTE_SELLER_STAFFER.toString(),SALES_AGENT_FREELANCER_LEAD_GEN.toString(),SALES_AGENT_FREELANCER.toString(),ADMIN.toString())
                .antMatchers(HttpMethod.DELETE,"/mobile/**").hasAnyAuthority(ADMIN.toString())
                //common controller
                .antMatchers(HttpMethod.GET,"/data/**").authenticated()

                //on board get by id , and modifying this info allowed only for admin According to SAP_45
                .antMatchers(HttpMethod.GET,"/onboards/**").authenticated()
                .antMatchers(HttpMethod.POST, "/onboards/").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/onboards/").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/onboards/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.GET,"/data/user-balance*").hasAnyAuthority(REMOTE_SELLER_STAFFER.toString(),SALES_AGENT_FREELANCER_LEAD_GEN.toString(),SALES_AGENT_FREELANCER.toString())

                .antMatchers(HttpMethod.GET,"/template-types/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.PUT,"/template-types/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/template-types/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.DELETE,"/template-types/**").hasAnyAuthority(ADMIN.toString())

                .antMatchers("/tracking/**").authenticated()

                .antMatchers(HttpMethod.GET,"/reports/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())
                .antMatchers(HttpMethod.PUT,"/reports/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())
                .antMatchers(HttpMethod.POST,"/reports/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())
                .antMatchers(HttpMethod.DELETE,"/reports/**").hasAnyAuthority(ADMIN.toString(), BACK_OFFICE_EMPLOYEE.toString())

                .antMatchers("/devices/**").authenticated()

                .antMatchers(HttpMethod.GET,"/reasons/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.PUT,"/reasons/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/reasons/**").hasAnyAuthority(ADMIN.toString())
                .antMatchers(HttpMethod.DELETE,"/reasons/**").hasAnyAuthority(ADMIN.toString())

                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
