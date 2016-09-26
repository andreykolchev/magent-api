package com.magent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.User;
import com.magent.reportmodule.utils.xlsutil.interfaces.TransactionsXlsReader;
import com.magent.servicemodule.service.interfaces.UserService;
import com.magent.utils.JsonConverter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artomov.ihor on 03.06.2016.
 */
public class ReportsControllerImplTest extends MockWebSecurityConfig {
    @Value("${tmp.excel.file}")
    private String uploadPath;
    @Autowired
    private UserService userRepository;
    @Autowired
    private JsonConverter converter;

    @Autowired
    TransactionsXlsReader transactionsXlsReader;

    @Test
    @Ignore
    @Sql("classpath:data.sql")
    public void uploadTransactionsXLSTest() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("test.xls", Files.readAllBytes(Paths.get(uploadPath + "updateTransactionSecond.xls")));
        MvcResult res = mvc.perform(fileUpload("/reports/transactions/upload")
                .file("file", multipartFile.getBytes())
                .param("fileName", "test.xls")
                .param("separateSign", "")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(res.getResponse().getContentAsString().replaceAll("\"", ""), "upload success");
    }

    @Test
    @Ignore
    @Sql("classpath:data.sql")
    public void uploadTransactionsCSVTest() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("test.csv", Files.readAllBytes(Paths.get(uploadPath + "test.csv")));
        MvcResult res = mvc.perform(fileUpload("/reports/transactions/upload")
                .file("file", multipartFile.getBytes())
                .param("fileName", "test.csv")
                .param("separateSign", ";")
                .header(authorizationHeader, getAccessAdminToken()))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(res.getResponse().getContentAsString().replaceAll("\"", ""), "upload success");

    }

    @Test
    @Sql("classpath:data.sql")
    public void createXlsReport() throws Exception {
        List<User> userList = userRepository.getAllUsersWithAccount();
        userList = converter.convertList(userList);
        mvc.perform(post("/reports/balance/create")
                .header(authorizationHeader, getAccessAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(userList)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

    @Test
    @Ignore
    @Sql("classpath:data.sql")
    public void getXlsTransactionReportByDateTest() throws Exception {
        mvc.perform(get("/reports/transactions/bydate")
                .header(authorizationHeader, getAccessAdminToken())
                .param("date", "2016-04-10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

    @Test
    @Ignore
    @Sql("classpath:data.sql")
    public void getXlsTransactionReportByPeriodTest() throws Exception {
        mvc.perform(get("/reports/transactions/byperiod")
                .header(authorizationHeader, getAccessAdminToken())
                .param("date1", "2016-04-10")
                .param("date2", "2016-04-14"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }
}
