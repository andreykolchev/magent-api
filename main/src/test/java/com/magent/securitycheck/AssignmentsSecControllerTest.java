package com.magent.securitycheck;

import com.magent.config.MockWebSecurityConfig;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artomov.ihor on 10.06.2016.
 */
public class AssignmentsSecControllerTest extends MockWebSecurityConfig {

    @Test
    public void testGetBackOfficePossitive() throws Exception {
        mvc.perform(get("/assignments/")
                .header(authorizationHeader, getBackOfficeAccessToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    public void testDeleteNegative() throws Exception {
        mvc.perform(delete("/assignments/1")
                .header(authorizationHeader, getBackOfficeAccessToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteAttributeByIdNegative() throws Exception {
        mvc.perform(delete("/assignments/attributes/1")
                .header(authorizationHeader, getBackOfficeAccessToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
