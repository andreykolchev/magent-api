package com.ds.controller;

import com.ds.config.MockWebSecurityConfig;
import com.ds.utils.EntityGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by user on 18.05.16.
 */
public class DeviceControllerImplTest extends MockWebSecurityConfig {

    @Test
    public void testAddOrUpdateDevice() throws Exception {
        mvc.perform(post("/devices/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getUpdateDevice())))
                .andExpect(status().isOk())
                .andDo(print());
    }
}