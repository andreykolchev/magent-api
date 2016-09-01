package com.magent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.AssignmentAttribute;
import com.magent.domain.dto.UpdateDataDto;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.AssignmentAttributeService;
import com.magent.service.interfaces.GeneralService;
import com.magent.utils.AssignmentAttributesGenerator;
import com.magent.utils.CommissionUtils;
import com.magent.utils.EntityGenerator;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by user on 16.05.16.
 */
public class MobileControllerImplTest extends MockWebSecurityConfig {
    @Value("${tmp.excel.file}")
    private String uploadPath;


    @Autowired
    @Qualifier("assignmentAttributeGeneralService")
    private GeneralService assignmentAttrGenService;

    @Autowired
    private AssignmentAttributeService attributeService;

    @Autowired
    @Qualifier("onBoardingGeneralService")
    private GeneralService onBoardGeneralService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetData() throws Exception {

        mvc.perform(get("/mobile/")
                .header(authorizationHeader, getRemoteStafferAccessToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.assignments", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.assignments[0].attributes", Matchers.notNullValue()))
                .andExpect(jsonPath("$.assignments[0].tasks", Matchers.notNullValue()))
                .andExpect(jsonPath("$.assignments[0].tasks.controls", Matchers.notNullValue()))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testUpdateData() throws Exception {
        mvc.perform(put("/mobile/")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getUpdateDataDto())))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(get("/mobile/")
                .header(authorizationHeader, getRemoteStafferAccessToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void getValueType() throws Exception {
        mvc.perform(get("/data/valueType")
                .header(authorizationHeader, getRemoteStafferAccessToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getAssignmentStatus() throws Exception {

        mvc.perform(get("/data/assignmentStatus")
                .header(authorizationHeader, getRemoteStafferAccessToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @Sql("classpath:data.sql")
    public void testUpdateDataWithCommission() throws Exception {

        Double expected = 25.98;
        List<AssignmentAttribute> attributeList = AssignmentAttributesGenerator.getTestDataExcpected6();
        assignmentAttrGenService.saveAll(attributeList);
        UpdateDataDto dataDto = EntityGenerator.getUpdateDataDto();

        mvc.perform(put("/mobile/")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(dataDto)))
                .andExpect(status().isOk())
                .andDo(print());

        Double cost = CommissionUtils.getCommissionCost(attributeService.getByAssignmentId(1L));
        Assert.assertEquals(expected, cost);
    }

    @Test
    @Ignore
    public void testUploadFile() throws Exception {
        //file name should be with addition
        String fileName = "testFile.jpg";
        // pre conditions
        byte[] fileBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("small.jpg")))));

        String url = mvc.perform(fileUpload("/mobile/uploadFile")
                .file("file", fileBody)
                .param("name", fileName)
                .param("controlId", "1")
                .header(authorizationHeader, getRemoteStafferAccessToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        //asserts
        String change = url.replace("\"", "");
        byte[] saved = Files.readAllBytes(Paths.get(change));

        Assert.assertTrue("checking size after uploading", fileBody.length == saved.length);
        //clear file after checking
        Files.delete(Paths.get(change));
    }



    @Test
    @Sql("classpath:data.sql")
    public void getCurrentUserBalanceTest() throws Exception {
        String expectedBalance = "1350.01";//for user user2

        String actualBalance = mvc.perform(get("/mobile/user-balance")
                .header(authorizationHeader, getRemoteStafferAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assert.assertEquals(expectedBalance, actualBalance.replace("\"", ""));

    }


    @Test
    public void testGetSettings() throws Exception {
        mvc.perform(get("/mobile/tracking/settings/")
                .header(authorizationHeader, getAccessAdminToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    @Test
    public void testCreateCalls() throws Exception {
        mvc.perform(post("/mobile/tracking/calls/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewCallList())))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testCreateLocations() throws Exception {
        mvc.perform(post("/mobile/tracking/locations/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewTestLocation())))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testCreateActivities() throws Exception {
        mvc.perform(post("/mobile/tracking/apps/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getNewActivityList())))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    @Sql("classpath:data.sql")
    public void createByTemplateIdTestPositive() throws Exception {
        Long userIdWhoCreate=userRepository.findByLogin(remoteStaffer).getId();
        Long templateId=EntityGenerator.getTemplateAssignment().getTemplateId();
        mvc.perform(post("/mobile/assignments/createByTemplateId")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getTemplateAssignment())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId",Matchers.is(userIdWhoCreate.intValue())))
                .andExpect(jsonPath("$.templateId",Matchers.is(templateId.intValue())))
                .andReturn();
    }
    @Test
    @Sql("classpath:data.sql")
    public void createByTemplateIdTestNegative() throws Exception {

        mvc.perform(post("/mobile/assignments/createByTemplateId")
                .header(authorizationHeader, getBackOfficeAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getTemplateAssignment())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}