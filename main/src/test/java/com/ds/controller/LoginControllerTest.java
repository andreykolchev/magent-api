package com.ds.controller;

import com.ds.config.MockWebSecurityConfig;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artomov.ihor on 13.05.2016.
 */
public class LoginControllerTest extends MockWebSecurityConfig {

    @Test
    public void loginNegativeTest() throws Exception {
        mvc.perform(post("/login")
                .param("username", "user1")
                .param("password", "bad password"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql("classpath:data.sql")
    public void loginNegativeWithOtpTest() throws Exception {
        String request = mvc.perform(post("/login")
                .param("username", "user1")
                .param("password", "bad password")
                .param("withSms", String.valueOf(true)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("message password incorrect", request.trim());
    }

    @Test
    public void loginPositiveTest() throws Exception {
        mvc.perform(post("/login")
                .param("username", testLogin)
                .param("password", testPassword))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void refreshTokenPositiveTest() throws Exception {
        mvc.perform(post("/refresh").param(header, getRefreshToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", Matchers.notNullValue()))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void refreshTokenNegativeTest() throws Exception {
        mvc.perform(post("/refresh").param(header, "bad refresh header"))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();

    }


}

