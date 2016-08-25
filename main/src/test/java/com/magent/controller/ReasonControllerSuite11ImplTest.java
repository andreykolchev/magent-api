package com.magent.controller;

import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.Reason;
import com.magent.service.interfaces.GeneralService;
import com.magent.utils.EntityGenerator;
import com.magent.utils.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ReasonControllerImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 20, 2016</pre>
 */
public class ReasonControllerSuite11ImplTest extends MockWebSecurityConfig {
    @Autowired
    @Qualifier("reasonGeneralService")
    private GeneralService reasonGenService;

    @Autowired
    private JsonConverter converter;

    @Before
    @Sql("classpath:data.sql")
    public void setUp() {
    }

    /**
     * Method: getList()
     */
    @Test
    public void testGetList() throws Exception {
        mvc.perform(get("/reasons/")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andReturn();
    }

    /**
     * Method: getReasonById(@PathVariable("id") Long id)
     */
    @Test
    public void testGetReasonById() throws Exception {
        mvc.perform(get("/reasons/1")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andReturn();
    }

    /**
     * Method: create(@RequestBody Reason reason)
     */
    @Test
    @Rollback
    public void testCreate() throws Exception {
        Reason reason = EntityGenerator.getNewReasoon();
        reason = (Reason) converter.convert(reason, Reason.class);
        int sizeBefore = reasonGenService.getAll().size();

        mvc.perform(post("/reasons/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(reason)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.desc", Matchers.is(reason.getDesc())))
                .andExpect(jsonPath("$.name", Matchers.is(reason.getName())))
                .andReturn();


        int sizeAfter = reasonGenService.getAll().size();

        Assert.assertTrue(sizeAfter > sizeBefore);
    }

    /**
     * Method: update(@RequestBody Reason reason)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testUpdate() throws Exception {
        Reason reason = (Reason) reasonGenService.getById(1L);
        reason = (Reason) converter.convert(reason, Reason.class);
        reason.setDesc("ComissionCalculatorImplTest Update description");

        mvc.perform(put("/reasons/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(reason)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc", Matchers.is(reason.getDesc())))
                .andReturn();
    }

    /**
     * Method: delete(@PathVariable("id") Long id)
     */
    @Test
    public void testDelete() throws Exception {
        int sizeBefore = reasonGenService.getAll().size();

        mvc.perform(delete("/reasons/1")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andReturn();

        int sizeAfter = reasonGenService.getAll().size();

        Assert.assertTrue(sizeBefore > sizeAfter);
    }

    /**
     * Method: getListByParentId(@PathVariable("id") Long id)
     */
    @Test
    public void testGetListByParentId() throws Exception {
        mvc.perform(get("/reasons/1/children")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andReturn();
    }
} 
