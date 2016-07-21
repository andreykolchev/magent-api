package com.magent.controller;

import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.AssignmentAttribute;
import com.magent.domain.OnBoarding;
import com.magent.domain.dto.UpdateDataDto;
import com.magent.service.interfaces.AssignmentAttributeService;
import com.magent.service.interfaces.GeneralService;
import com.magent.utils.AssignmentAttributesGenerator;
import com.magent.utils.CommissionUtils;
import com.magent.utils.EntityGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class DataControllerImplTest extends MockWebSecurityConfig {
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

    @Test
    public void testGetData() throws Exception {

        mvc.perform(get("/data/")
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
        mvc.perform(put("/data/")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getUpdateDataDto())))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(get("/data/")
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

        mvc.perform(put("/data/")
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

        String url = mvc.perform(fileUpload("/data/uploadFile")
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
    public void getOnBoardingInformationTestPositive() throws Exception {
        //pre conditions
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng();
        onBoardGeneralService.save(onBoarding);
        //test
        mvc.perform(get("/data/onboards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].fullFileName", Matchers.is(onBoarding.getFullFileName())))
                .andReturn();

    }

    @Test
    @Sql("classpath:data.sql")
    public void getOnboardByIdTestPositive() throws Exception {
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng();
        onBoardGeneralService.save(onBoarding);
        List<OnBoarding> onBoardingList = onBoardGeneralService.getAll();
        Assert.assertEquals(1, onBoardingList.size());
        //test
        mvc.perform(get("/data/onboards/" + onBoardingList.get(0).getId())
                .header(authorizationHeader, getRemoteStafferAccessToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.fullFileName", Matchers.is(onBoarding.getFullFileName())))
                .andExpect(jsonPath("$.id", Matchers.is(onBoardingList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.content", Matchers.is(onBoarding.getContent())))
                .andReturn();
    }

    @Test
    public void getOnboardByIdTestNegative() throws Exception {
        mvc.perform(get("/data/onboards/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createOnBoardEntityTestPositivePNG() throws Exception {
        mvc.perform(post("/data/onboards")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardPossitivePng())))
                .andExpect(status().isCreated());
        //additional assert for db
        Assert.assertEquals(1, onBoardGeneralService.getAll().size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createOnBoardEntityTestPositiveSVG() throws Exception {
        mvc.perform(post("/data/onboards")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardPositiveSVG())))
                .andExpect(status().isCreated())
                .andDo(print());
        //additional assert for db
        Assert.assertEquals(1, onBoardGeneralService.getAll().size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createOnBoardEntityTestPositiveSVGNullFields() throws Exception {
        mvc.perform(post("/data/onboards")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardPositiveSVGWithNullFields())))
                .andExpect(status().isCreated());
        //additional assert for db
        Assert.assertEquals(1, onBoardGeneralService.getAll().size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createOnBoardEntityTestNegativeSVG() throws Exception {
        mvc.perform(post("/data/onboards")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardNegativeSVG())))
//                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void createOnBoardEntityTestNegativeSeq() throws Exception {
        mvc.perform(post("/data/onboards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardPossitivePng())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createOnBoardEntityTestNegativeWrongImage() throws Exception {
        mvc.perform(post("/data/onboards")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardWrongImage())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("classpath:data.sql")
    public void updateOnBoardEntityTestPositive() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng();
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        fromDb.setImage(EntityGenerator.getOnBoardPositiveSVG().getImage());
        fromDb.setFullFileName(EntityGenerator.getOnBoardPositiveSVG().getFullFileName());
        //test
        mvc.perform(put("/data/onboards")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(fromDb)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullFileName", Matchers.is(fromDb.getFullFileName())));
    }

    @Test
    @Sql("classpath:data.sql")
    public void updateOnBoardEntityTestNegative() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng();
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        fromDb.setImage(EntityGenerator.getOnBoardWrongImage().getImage());
        fromDb.setFullFileName(EntityGenerator.getOnBoardWrongImage().getFullFileName());
        //test
        mvc.perform(put("/data/onboards")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(fromDb)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Sql("classpath:data.sql")
    public void updateOnBoardEntityTestNegativeWrongImageSize() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng();
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        fromDb.setImage(EntityGenerator.getOnBoardNegativeSVG().getImage());
        fromDb.setFullFileName(EntityGenerator.getOnBoardNegativeSVG().getFullFileName());
        //test
        mvc.perform(put("/data/onboards")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(fromDb)))
//                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Sql("classpath:data.sql")
    public void updateOnBoardEntityTestNegativeSeq() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng();
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        fromDb.setImage(EntityGenerator.getOnBoardNegativeSVG().getImage());
        fromDb.setFullFileName(EntityGenerator.getOnBoardNegativeSVG().getFullFileName());
        //test
        mvc.perform(put("/data/onboards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(fromDb)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql("classpath:data.sql")
    public void deleteOnboardEntityTestPositive() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng();
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        //test
        mvc.perform(delete("/data/onboards/" + fromDb.getId().intValue())
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk());
        //additional assert
        Assert.assertEquals(0, onBoardGeneralService.getAll().size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void deleteOnboardEntityTestNegativeSeq() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng();
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        //test
        mvc.perform(delete("/data/onboards/" + fromDb.getId().intValue()))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @Sql("classpath:data.sql")
    public void getCurrentUserBalanceTest() throws Exception {
        String expectedBalance = "1350.01";//for user user2

        String actualBalance = mvc.perform(get("/data/user-balance")
                .header(authorizationHeader, getRemoteStafferAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assert.assertEquals(expectedBalance, actualBalance.replace("\"", ""));

    }

}