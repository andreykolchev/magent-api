package com.magent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.TemplateType;
import com.magent.domain.enums.UserRoles;
import com.magent.service.interfaces.GeneralService;
import com.magent.utils.EntityGenerator;
import javassist.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TemplateTypeControllerImpl Tester.
 */
public class TemplateTypeControllerSuite11ImplTest extends MockWebSecurityConfig {
    @Autowired
    @Qualifier("templateTypeGeneralService")
    private GeneralService templateTypeGenService;


    /**
     * Method: getAll()
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetAll() throws Exception {
        templateTypeGenService.save(EntityGenerator.generateTestTemplateType());
        templateTypeGenService.save(EntityGenerator.generateTestTemplateType());

        mvc.perform(get("/template-types/")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.", Matchers.hasSize(6)))
                .andReturn();

    }

    /**
     * Method: getById(@PathVariable Long id)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetById() throws Exception {
        TemplateType templateType = (TemplateType) templateTypeGenService.save(EntityGenerator.generateTestTemplateType());
        Long testId = templateType.getId();
        mvc.perform(get("/template-types/" + testId)
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.userRolesList", Matchers.notNullValue()))
                .andExpect(jsonPath("$.description", Matchers.notNullValue()))
                .andReturn();

    }

    /**
     * Method: createTemplateType(@RequestBody TemplateType templateType)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testCreateTemplateType() throws Exception {
        mvc.perform(post("/template-types/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.generateTestTemplateType())))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.userRolesList", Matchers.notNullValue()))
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void createChildPositiveTest() throws Exception {
        TemplateType templateType = (TemplateType) templateTypeGenService.save(EntityGenerator.generateTestTemplateType());
        mvc.perform(post("/template-types/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.generateChildTemplateType(templateType.getId()))))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.userRolesList", Matchers.notNullValue()))
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void getByIdWithChild() throws Exception {
        TemplateType templateType = (TemplateType) templateTypeGenService.save(EntityGenerator.generateTestTemplateType());
        templateTypeGenService.save(EntityGenerator.generateChildTemplateType(templateType.getId()));
        templateType = (TemplateType) templateTypeGenService.getById(templateType.getId());
        Assert.assertNotNull(templateType.getChildTemplatesTypes());
        Assert.assertEquals(1, templateType.getChildTemplatesTypes().size());

        mvc.perform(get("/template-types/" + templateType.getId() + "/childs")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].parentId", Matchers.is(templateType.getId().intValue())))
                .andReturn();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testCreateTemplateTypeNegative() throws Exception {
        mvc.perform(post("/template-types/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.generateTestTemplateTypeNegative())))
                .andExpect(status().isConflict());

    }

    /**
     * Method: updateTemplateType(@PathVariable Long id, @RequestBody TemplateType templateType)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testUpdateTemplateType() throws Exception {
        TemplateType templateType = (TemplateType) templateTypeGenService.save(EntityGenerator.generateTestTemplateType());
        templateType.setUserRolesList(Arrays.asList(UserRoles.SALES_AGENT_FREELANCER_LEAD_GEN, UserRoles.ADMIN));

        mvc.perform(put("/template-types/" + templateType.getId())
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(templateType)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.userRolesList", Matchers.hasSize(2)))
                .andReturn();
    }

    /**
     * Method: deleteById(@PathVariable Long id)
     */
    @Test(expected = NotFoundException.class)
    @Sql("classpath:data.sql")
    public void testDeleteById() throws Exception {
        TemplateType templateType = (TemplateType) templateTypeGenService.save(EntityGenerator.generateTestTemplateType());

        mvc.perform(delete("/template-types/" + templateType.getId())
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk());

        Assert.assertNull(templateTypeGenService.getById(templateType.getId()));

    }


} 
