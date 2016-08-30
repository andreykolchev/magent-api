package com.magent.controller;

import com.magent.config.MockWebSecurityConfig;
import com.magent.utils.EntityGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by user on 19.05.16.
 */
public class UserControllerSuiteTest extends MockWebSecurityConfig {

    @Test
    @Sql("classpath:data.sql")
    public void testGetList() throws Exception {
        mvc.perform(get("/users/")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @Sql("classpath:data.sql")
    public void testGetUserById() throws Exception {
        mvc.perform(get("/users/1")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @Sql("classpath:data.sql")
    public void testGetUsersByFilter() throws Exception {
        mvc.perform(get("/users/byFilter")
                .header(authorizationHeader, getAccessAdminToken())
                .param("filter", "role:ADMIN")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @Sql("classpath:data.sql")
    public void testEdit() throws Exception {

        mvc.perform(put("/users/1/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getForUpdateUser())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testChangePassword() throws Exception {
        mvc.perform(put("/users/1/changepassword")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getChangePasswordDto())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testCreate() throws Exception {
        mvc.perform(post("/users/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewTestUser())))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }
}