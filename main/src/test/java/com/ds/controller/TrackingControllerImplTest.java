package com.ds.controller;

import com.ds.config.MockWebSecurityConfig;
import com.ds.utils.EntityGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by user on 18.05.16.
 */
public class TrackingControllerImplTest extends MockWebSecurityConfig {

    @Test
    public void testGetSettings() throws Exception {
        mvc.perform(get("/tracking/settings/")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    @Test
    public void testCreateCalls() throws Exception {
        mvc.perform(post("/tracking/calls/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewCallList())))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testCreateLocations() throws Exception {
        mvc.perform(post("/tracking/locations/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewTestLocation())))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testCreateActivities() throws Exception {
        mvc.perform(post("/tracking/apps/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewActivityList())))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}