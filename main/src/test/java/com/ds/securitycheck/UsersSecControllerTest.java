package com.ds.securitycheck;

import com.ds.config.MockWebSecurityConfig;
import com.ds.utils.EntityGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artomov.ihor on 10.06.2016.
 */
public class UsersSecControllerTest extends MockWebSecurityConfig {
    @Test
    public void testCreateNegative() throws Exception {
        mvc.perform(post("/users/")
                .header(authorizationHeader, getBackOfficeAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewTestUser())))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testChangePasswordNegative() throws Exception {
        mvc.perform(put("/users/1/changepassword")
                .header(authorizationHeader, getBackOfficeAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getChangePasswordDto())))
                .andExpect(status().isForbidden());
    }
}
