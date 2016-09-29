package com.magent.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.magent.authmodule.service.interfaces.OauthService;
import com.magent.authmodule.utils.SecurityUtils;
import com.magent.authmodule.utils.otpgenerator.OtpGenerator;
import com.magent.authmodule.utils.validators.UserValidatorImpl;
import com.magent.config.MockWebAppConfiguration;
import com.magent.domain.SmsPassword;
import com.magent.domain.User;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.UserRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artomov.ihor on 14.06.2016.
 * Requirements before skip ignore testing:
 * - ds_oauth server must looks in same database with test because auth_server compare otp and pass together ang get otp from DB
 * - this tests not cover all filter configuration because it general configuration for modules which contains controllers
 */
public class OauthServiceTest extends MockWebAppConfiguration {
    @Autowired
    @Qualifier("oauthSimple")
    private OauthService simpleOauth;

    @Autowired
    @Qualifier("oauthOtp")
    private OauthService otpOauth;

    @Autowired
    private OtpGenerator generator;

    @Autowired
    private SmsPasswordRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("classpath:data.sql")
    public void getTokenTest() throws UserValidatorImpl.UserIsBlockedException {
        String user = "user1";
        String pass = "user1";
        String hashpass = SecurityUtils.hashPassword(pass);
        OAuth2AccessToken token = simpleOauth.getToken(user, hashpass);
        Assert.assertNotNull(token);
    }

    @Test(expected = UserValidatorImpl.UserIsBlockedException.class)
    @Sql("classpath:data.sql")
    public void checkBlockingUsers() throws UserValidatorImpl.UserIsBlockedException {
        String user = "user1";
        String pass = "badPassword";
        String hashpass = SecurityUtils.hashPassword(pass);
        //first time
        tryGetToken(user, pass);
        //second time
        tryGetToken(user, pass);
        //third time
        tryGetToken(user, pass);
        //fourth time must throw exception UserIsBlockedException.class
        tryGetToken(user, pass);
    }

    @Test
    @Ignore
    @Sql("classpath:data.sql")
    public void testWithSms() throws UserValidatorImpl.UserIsBlockedException {
        String login = "user1";
        String pass = "user1";
        String sms = "12345678";
        User user1 = userRepository.findByLogin(login);

        String hashpass = SecurityUtils.hashPassword(pass);
        hashpass = SecurityUtils.hashPassword(hashpass);
        String hashSms = SecurityUtils.hashPassword(sms);
        hashSms = SecurityUtils.hashPassword(hashSms);
        //saving hashed 2 times only
        otpRepository.save(new SmsPassword(user1.getId(), user1.getId(), hashSms, new Date()));
        OAuth2AccessToken token = otpOauth.getToken(login, (hashpass + hashSms));
        Assert.assertNotNull(token);
    }

    @Test
    @Ignore
    @Sql("classpath:data.sql")
    public void viaSmsMvcTest() throws Exception {
        String login = "user1";
        String pass = "user1";
        String sms = "12345678";
        User user1 = userRepository.findByLogin(login);

        String hashpass = SecurityUtils.hashPassword(pass);
        String hashSms = SecurityUtils.hashPassword(sms);
        hashSms = SecurityUtils.hashPassword(hashSms);
        //saving hashed 2 times only
        otpRepository.save(new SmsPassword(user1.getId(), user1.getId(), hashSms, new Date()));

        //test begin
        String tokenAsString = mvc.perform(post("/login/otp")
                .param("username", login)
                .param("password", hashpass)
                .param("otpPass", SecurityUtils.hashPassword(sms)))
                .andDo(print())
                .andReturn()
                .getResponse().getContentAsString();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(tokenAsString);
        String token = "bearer" + object.get("access_token").getAsString();

        //check is access works
        mvc.perform(get("/assignments/")
                .header(AUTHORIZATION_HEADER, token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }


    @Test
    public void testSmsGenerator() {
        Set<String> smsList = new HashSet<>();
        for (int i = 0; i < 10; i++) smsList.add(generator.generate());
        for (String s : smsList) Assert.assertEquals(s.length(), 8);
        Assert.assertEquals(10, smsList.size());
    }

    //for block testing
    private void tryGetToken(String user, String hashpass) throws UserValidatorImpl.UserIsBlockedException {
        try {
            simpleOauth.getToken(user, hashpass);
        } catch (OAuth2AccessDeniedException e) {

        }
    }
}

