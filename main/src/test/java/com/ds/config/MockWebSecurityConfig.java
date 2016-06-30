package com.ds.config;


import com.ds.Application;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by artomov.ihor on 13.05.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
public class MockWebSecurityConfig extends MockWebAppConfiguration {
    protected final static JsonParser parser = new JsonParser();
    protected final static String testLogin = "user1";//ADMIN
    protected final static String testPassword = "edd8279b8ebe50c5652ff42e32c3561dd6f85e93";
    protected final static String header = "refreshToken";
    protected final static String authorizationHeader = "Authorization";

    private final static String backOfficeEmployer = "+380506847580";
    private final static String backPass = "e310c6b1fb3c8f43b88ea8f3c0fcd4eb19a60c1c";

    protected String getToken() throws Exception {
        return mvc.perform(post("/login")
                .param("username", testLogin)
                .param("password", testPassword))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    protected String getRefreshToken() throws Exception {
        JsonObject object = (JsonObject) parser.parse(getToken());
        return object.get("refresh_token").getAsString();
    }

    protected String getAccessAdminToken() throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(getToken());
        return "bearer" + object.get("access_token").getAsString();
    }


    protected String getBackOfficeAccessToken() throws Exception {
        String token = mvc.perform(post("/login")
                .param("username", backOfficeEmployer)
                .param("password", backPass))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(token);
        return "bearer" + object.get("access_token").getAsString();
    }

    @Before
    @Sql("classpath:data.sql")
    public void before() {
        this.mvc = webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }
}
