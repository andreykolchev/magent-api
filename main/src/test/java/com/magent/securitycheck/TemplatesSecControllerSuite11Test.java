package com.magent.securitycheck;

import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.Template;
import com.magent.domain.TemplateTaskControl;
import com.magent.utils.EntityGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artomov.ihor on 10.06.2016.
 */
public class TemplatesSecControllerSuite11Test extends MockWebSecurityConfig {
    // this tests checks access according to security filter
    // access to templates should have only admin
    @Test
    public void testAdminOnlyAccessNegative() throws Exception {
        mvc.perform(get("/templates/")
                .header(authorizationHeader, getBackOfficeAccessToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAdminOnlyAccessPossitive() throws Exception {
        mvc.perform(get("/templates/")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostTemplateNegative() throws Exception {
        Template testTemplate = EntityGenerator.getNewTestTemplate();

        mvc.perform(post("/templates/")
                .header(authorizationHeader, getBackOfficeAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testTemplate)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql("classpath:data.sql")
    public void testTemplatesControlsPostNegative() throws Exception {
        TemplateTaskControl task = EntityGenerator.getNewTemplateTaskControl();

        mvc.perform(post("/templates/tasks/1/controls/")
                .header(authorizationHeader, getBackOfficeAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(task)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteNegative() throws Exception {
        mvc.perform(delete("/templates/2")
                .header(authorizationHeader, getBackOfficeAccessToken()))
                .andExpect(status().isForbidden());
    }

}
