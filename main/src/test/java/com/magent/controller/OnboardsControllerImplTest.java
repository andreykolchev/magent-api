package com.magent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.OnBoarding;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.utils.EntityGenerator;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * OnboardsControllerImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 1, 2016</pre>
 */
public class OnboardsControllerImplTest extends MockWebSecurityConfig {

    @Value("${resource.path}")
    private String resourcepath;
    @Autowired
    @Qualifier("onBoardingGeneralService")
    private GeneralService onBoardGeneralService;

    /**
     * Method: getOnBoardingInformation()
     */
    @Test
    @Sql("classpath:data.sql")
    public void getOnBoardingInformationTestPositive() throws Exception {
        //pre conditions
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng(resourcepath);
        onBoardGeneralService.save(onBoarding);
        //test
        mvc.perform(get("/onboards/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].fullFileName", Matchers.is(onBoarding.getFullFileName())))
                .andReturn();

    }

    @Test
    @Sql("classpath:data.sql")
    public void getOnboardByIdTestPositive() throws Exception {
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng(resourcepath);
        onBoardGeneralService.save(onBoarding);
        List<OnBoarding> onBoardingList = onBoardGeneralService.getAll();
        Assert.assertEquals(1, onBoardingList.size());
        //test
        mvc.perform(get("/onboards/" + onBoardingList.get(0).getId())
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
        mvc.perform(get("/onboards/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createOnBoardEntityTestPositivePNG() throws Exception {
        mvc.perform(post("/onboards/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardPossitivePng(resourcepath))))
                .andExpect(status().isCreated());
        //additional assert for db
        Assert.assertEquals(1, onBoardGeneralService.getAll().size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createOnBoardEntityTestPositiveSVG() throws Exception {
        mvc.perform(post("/onboards/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardPositiveSVG(resourcepath))))
                .andExpect(status().isCreated())
                .andDo(print());
        //additional assert for db
        Assert.assertEquals(1, onBoardGeneralService.getAll().size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createOnBoardEntityTestPositiveSVGNullFields() throws Exception {
        mvc.perform(post("/onboards/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardPositiveSVGWithNullFields(resourcepath))))
                .andExpect(status().isCreated());
        //additional assert for db
        Assert.assertEquals(1, onBoardGeneralService.getAll().size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createOnBoardEntityTestNegativeSVG() throws Exception {
        mvc.perform(post("/onboards/")
                .header(authorizationHeader, getRemoteStafferAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardNegativeSVG(resourcepath))))
//                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void createOnBoardEntityTestNegativeSeq() throws Exception {
        mvc.perform(post("/onboards/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardPossitivePng(resourcepath))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createOnBoardEntityTestNegativeWrongImage() throws Exception {
        mvc.perform(post("/onboards/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(EntityGenerator.getOnBoardWrongImage(resourcepath))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("classpath:data.sql")
    public void updateOnBoardEntityTestPositive() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng(resourcepath);
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        fromDb.setImage(EntityGenerator.getOnBoardPositiveSVG(resourcepath).getImage());
        fromDb.setFullFileName(EntityGenerator.getOnBoardPositiveSVG(resourcepath).getFullFileName());
        //test
        mvc.perform(put("/onboards/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(fromDb)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullFileName", Matchers.is(fromDb.getFullFileName())));
    }

    @Test
    @Sql("classpath:data.sql")
    public void updateOnBoardEntityTestNegative() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng(resourcepath);
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        fromDb.setImage(EntityGenerator.getOnBoardWrongImage(resourcepath).getImage());
        fromDb.setFullFileName(EntityGenerator.getOnBoardWrongImage(resourcepath).getFullFileName());
        //test
        mvc.perform(put("/onboards/")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(fromDb)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Sql("classpath:data.sql")
    public void updateOnBoardEntityTestNegativeWrongImageSize() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng(resourcepath);
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        fromDb.setImage(EntityGenerator.getOnBoardNegativeSVG(resourcepath).getImage());
        fromDb.setFullFileName(EntityGenerator.getOnBoardNegativeSVG(resourcepath).getFullFileName());
        //test
        mvc.perform(put("/onboards/")
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
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng(resourcepath);
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        fromDb.setImage(EntityGenerator.getOnBoardNegativeSVG(resourcepath).getImage());
        fromDb.setFullFileName(EntityGenerator.getOnBoardNegativeSVG(resourcepath).getFullFileName());
        //test
        mvc.perform(put("/onboards/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(fromDb)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql("classpath:data.sql")
    public void deleteOnboardEntityTestPositive() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng(resourcepath);
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        //test
        mvc.perform(delete("/onboards/" + fromDb.getId().intValue())
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk());
        //additional assert
        Assert.assertEquals(0, onBoardGeneralService.getAll().size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void deleteOnboardEntityTestNegativeSeq() throws Exception {
        //pre condition
        OnBoarding onBoarding = EntityGenerator.getOnBoardPossitivePng(resourcepath);
        onBoardGeneralService.save(onBoarding);
        OnBoarding fromDb = (OnBoarding) onBoardGeneralService.getAll().get(0);
        //test
        mvc.perform(delete("/onboards/" + fromDb.getId().intValue()))
                .andExpect(status().isUnauthorized());

    }
} 
