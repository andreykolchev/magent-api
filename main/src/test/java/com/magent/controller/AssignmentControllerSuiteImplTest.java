package com.magent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magent.config.MockWebSecurityConfig;
import com.magent.utils.EntityGenerator;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created on 12.05.16.
 */
public class AssignmentControllerSuiteImplTest extends MockWebSecurityConfig {


    @Test
    public void testGet() throws Exception {
        mvc.perform(get("/assignments/")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void testCreate() throws Exception {
        mvc.perform(post("/assignments/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewAssignment())))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testUpdate() throws Exception {
        mvc.perform(put("/assignments/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getForUpdateAssignment())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetById() throws Exception {
        mvc.perform(get("/assignments/1")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void testDeleteById() throws Exception {
        mvc.perform(delete("/assignments/1")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAttributesByAssignmentId() throws Exception {
        mvc.perform(get("/assignments/1/attributes/")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    public void testCreateAttribute() throws Exception {
        mvc.perform(post("/assignments/1/attributes/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewAssignmentAttribute())))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testUpdateAttribute() throws Exception {
        mvc.perform(put("/assignments/1/attributes/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewAssignmentAttribute())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetAttributeById() throws Exception {
        mvc.perform(get("/assignments/1/attributes/1")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    public void testDeleteAttributeById() throws Exception {
        mvc.perform(delete("/assignments/attributes/1")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTasksByAssignmentId() throws Exception {
        mvc.perform(get("/assignments/1/tasks/")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    public void testCreateTask() throws Exception {
        mvc.perform(post("/assignments/1/tasks/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewAssignmentTask())))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testUpdateTask() throws Exception {
        mvc.perform(put("/assignments/1/tasks/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewAssignmentTask())))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @Sql("classpath:data.sql")
    public void testGetTaskById() throws Exception {
        mvc.perform(get("/assignments/1/tasks/1/")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    public void testDeleteTaskById() throws Exception {
        mvc.perform(delete("/assignments/tasks/1/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllTasksControls() throws Exception {
        mvc.perform(get("/assignments/tasks/1/controls/")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    public void testCreateControl() throws Exception {
        mvc.perform(post("/assignments/tasks/1/controls/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewAssignmentTaskControl())))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testUpdateControl() throws Exception {
        mvc.perform(put("/assignments/tasks/1/controls/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewAssignmentTaskControl())))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testGetControlById() throws Exception {
        mvc.perform(get("/assignments/tasks/1/controls/1")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    public void testDeleteControlById() throws Exception {
        mvc.perform(delete("/assignments/tasks/controls/1")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:data.sql")
    public void testCreateByTemplateId() throws Exception {
        mvc.perform(post("/assignments/createByTemplateId/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getTemplateAssignment())))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testAssignToUser() throws Exception {
        mvc.perform(put("/assignments/1/assignToUser/1")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getForUpdateAssignment())))
                .andExpect(status().isOk())
                .andReturn();
    }
}