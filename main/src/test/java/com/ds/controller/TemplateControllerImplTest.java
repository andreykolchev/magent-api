package com.ds.controller;

import com.ds.config.MockWebSecurityConfig;
import com.ds.domain.*;
import com.ds.service.interfaces.GeneralService;
import com.ds.utils.EntityGenerator;
import com.ds.utils.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TemplateControllerImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 19, 2016</pre>
 */
public class TemplateControllerImplTest extends MockWebSecurityConfig {
    private static final int LIST_SIZE_BEFORE_ADD = 2;
    @Autowired
    @Qualifier("templateGeneralService")
    private GeneralService templateGenService;

    @Autowired
    private JsonConverter converter;

    @Autowired
    @Qualifier("templateAttributeGeneralService")
    private GeneralService templateAttributeGeneralService;

    @Autowired
    @Qualifier("templateTaskGeneralService")
    private GeneralService templateTaskGenService;

    @Autowired
    @Qualifier("templateTaskControlGeneralService")
    private GeneralService templateTaskControlGenService;

    /**
     * Method: get()
     */
    @Test
    public void testGet() throws Exception {
        mvc.perform(get("/templates/")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(LIST_SIZE_BEFORE_ADD)))
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andDo(print())
                .andReturn();
    }

    /**
     * Method: create(@RequestBody Template template)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testCreate() throws Exception {
        Template testTemplate = EntityGenerator.getNewTestTemplate();

        mvc.perform(post("/templates/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testTemplate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.name", Matchers.is(testTemplate.getName())))
                .andExpect(jsonPath("$.desc", Matchers.is(testTemplate.getDesc())))
                .andReturn();

        int sizeAfterAdd = LIST_SIZE_BEFORE_ADD + 1;
        //checking it added
        mvc.perform(get("/templates/")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(sizeAfterAdd)));
    }

    /**
     * Method: update(@RequestBody Template template)
     */
    @Test
    @Rollback
    public void testUpdate() throws Exception {
        Template template = (Template) templateGenService.getById(1L);
        template = (Template) converter.convert(template, Template.class);
        String templateDesc = "new Desc";
        template.setDesc(templateDesc);

        //test
        mvc.perform(put("/templates/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(template)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.desc", Matchers.is(template.getDesc())))
                .andReturn();
    }

    /**
     * Method: getTemplateById(@PathVariable("id") Long id)
     */
    @Test
    public void testGetTemplateById() throws Exception {
        Template testTemplate = (Template) templateGenService.getById(1L);

        mvc.perform(get("/templates/1")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.id", Matchers.is(testTemplate.getId().intValue())))
                .andExpect(jsonPath("$.name", Matchers.is(testTemplate.getName())))
                .andExpect(jsonPath("$.attributes", Matchers.notNullValue()))
                .andExpect(jsonPath("$.templateTasks", Matchers.notNullValue()))
                .andReturn();
    }

    /**
     * Method: deleteById(@PathVariable("id") Long id)
     * Additional info template deleted if it don't have any assignments
     */
    @Test
    @Rollback
    public void testDeleteById() throws Exception {
        int sizeBeforeDelete = templateGenService.getAll().size();

        mvc.perform(delete("/templates/2")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk());

        int sizeAfter = templateGenService.getAll().size();

        Assert.assertTrue(sizeBeforeDelete > sizeAfter);
    }

    /**
     * Method: getAttributesByTemplateId(@PathVariable("templId") Long templId)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetAttributesByTemplateId() throws Exception {
        mvc.perform(get("/templates/1/attributes/")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].templateId", Matchers.is(1)))
                .andExpect(jsonPath("$", Matchers.hasSize(4)));
    }

    /**
     * Method: createAttribute(@PathVariable("templId") Long templId, @RequestBody TemplateAttribute templateAttribute)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testCreateAttribute() throws Exception {
        TemplateAttribute testAtribute = EntityGenerator.getNewTestTemplateAttribute();
        mvc.perform(post("/templates/1/attributes/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testAtribute)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.templateId", Matchers.is(1)))
                .andReturn();
    }

    /**
     * Method: updateAttribute(@PathVariable("templId") Long templId, @RequestBody TemplateAttribute templateAttribute)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testUpdateAttribute() throws Exception {
        //pre condition
        TemplateAttribute attribute = (TemplateAttribute) templateAttributeGeneralService.getById(1L);
        attribute = (TemplateAttribute) converter.convert(attribute, TemplateAttribute.class);
        String newAttributeDesc = "Update description";
        attribute.setDesc(newAttributeDesc);
        //test
        mvc.perform(put("/templates/1/attributes/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(attribute)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc", Matchers.is(newAttributeDesc)))
                .andReturn();
    }

    /**
     * Method: getAttributeById(@PathVariable("templId") Long templId, @PathVariable("attrId") Long attrId)
     */
    @Test
    public void testGetAttributeById() throws Exception {
        mvc.perform(get("/templates/1/attributes/2")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.valueType", Matchers.is(ValueType.ADDRESS.toString())))
                .andReturn();
    }

    /**
     * Method: deleteAttributeById(@PathVariable("id") Long tmpAttrId)
     */
    @Test
    @Rollback
    public void testDeleteAttributeById() throws Exception {
        int sizeBefore = templateAttributeGeneralService.getAll().size();

        mvc.perform(delete("/templates/attributes/1")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk());

        int sizeAfter = templateAttributeGeneralService.getAll().size();

        Assert.assertTrue(sizeBefore > sizeAfter);
    }

    /**
     * Method: getTasksByTemplateId(@PathVariable("templId") Long templId)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetTasksByTemplateId() throws Exception {

        mvc.perform(get("/templates/1/tasks/")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andReturn();
    }

    /**
     * Method: createTask(@PathVariable("templId") Long templId, @RequestBody TemplateTask templateTask)
     */
    @Test
    @Rollback
    public void testCreateTask() throws Exception {
        TemplateTask task = EntityGenerator.getNewTemplateTask();
        mvc.perform(post("/templates/1/tasks/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.desc", Matchers.is("new TemplateTask")))
                .andExpect(jsonPath("$.templateId", Matchers.is(1)))
                .andReturn();
    }

    /**
     * Method: updateTask(@PathVariable("templId") Long templId, @RequestBody TemplateTask templateTask)
     */
    @Test
    public void testUpdateTask() throws Exception {
        //pre condition
        String newDesc = "UpdateDesc";
        TemplateTask task = (TemplateTask) templateTaskGenService.getById(1L);
        task = (TemplateTask) converter.convert(task, TemplateTask.class);
        task.setDesc(newDesc);

        //test
        mvc.perform(put("/templates/1/tasks/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc", Matchers.is(newDesc)))
                .andReturn();
    }

    /**
     * Method: getTemplateTaskById(@PathVariable("templId") Long templId, @PathVariable("taskId") Long taskId)
     */
    @Test
    public void testGetTemplateTaskById() throws Exception {
        mvc.perform(get("/templates/1/tasks/2")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.templateId", Matchers.is(1)))
                .andReturn();
    }

    /**
     * Method: deleteTaskById(@PathVariable("id") Long taskId)
     */
    @Test
    @Rollback
    public void testDeleteTaskById() throws Exception {
        int sizeBefore = templateTaskGenService.getAll().size();

        mvc.perform(delete("/templates/tasks/1")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk());

        int sizeAfter = templateTaskGenService.getAll().size();

        Assert.assertTrue(sizeBefore > sizeAfter);

    }

    /**
     * Method: getControlsByTaskId(@PathVariable("taskId") Long taskId)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetControlsByTaskId() throws Exception {
        mvc.perform(get("/templates/tasks/1/controls/")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andReturn();
    }

    /**
     * Method: createControl(@PathVariable("taskId") int taskId, @RequestBody TemplateTaskControl templateTaskControl)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testCreateControl() throws Exception {
        TemplateTaskControl task = EntityGenerator.getNewTemplateTaskControl();

        mvc.perform(post("/templates/tasks/1/controls/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.validationRule", Matchers.is("by test")))
                .andExpect(jsonPath("$.templateTaskId", Matchers.is(1)))
                .andReturn();
    }

    /**
     * Method: updateControl(@PathVariable("taskId") int taskId, @RequestBody TemplateTaskControl templateTaskControl)
     */
    @Test
    public void testUpdateControl() throws Exception {
        //pre conditions
        String newDesc = "Update Check";
        Long taskControlId = 1L;
        TemplateTaskControl templateTaskControl = (TemplateTaskControl) templateTaskControlGenService.getById(taskControlId);
        templateTaskControl = (TemplateTaskControl) converter.convert(templateTaskControl, TemplateTaskControl.class);
        templateTaskControl.setDesc(newDesc);
        //test
        mvc.perform(put("/templates/tasks/1/controls/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(templateTaskControl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc", Matchers.is(newDesc)))
                .andReturn();
    }

    /**
     * Method: getControlById(@PathVariable("taskId") Long taskId, @PathVariable("controlId") Long controlId)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetControlById() throws Exception {
        mvc.perform(get("/templates/tasks/1/controls/1")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(jsonPath("$.templateTaskId", Matchers.is(1)))
                .andExpect(jsonPath("$.valueType", Matchers.is("PHOTO")))
                .andExpect(jsonPath("$.required", Matchers.is(true)))
                .andReturn();
    }

    /**
     * Method: deleteControlById(@PathVariable("controlId") Long controlId)
     */
    @Test
    public void testDeleteControlById() throws Exception {
        int sizeBefore = templateTaskControlGenService.getAll().size();
        mvc.perform(delete("/templates/tasks/controls/1")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk());
        int sizeAfter = templateTaskControlGenService.getAll().size();

        Assert.assertTrue(sizeBefore > sizeAfter);

    }


} 
