package com.magent.controller;

import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.TemporaryUser;
import com.magent.domain.User;
import com.magent.repository.DeviceRepository;
import com.magent.repository.TemporaryUserRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.SmsService;
import com.magent.service.interfaces.UserService;
import com.magent.utils.EntityGenerator;
import com.magent.utils.SecurityUtils;
import com.magent.utils.dateutils.DateUtils;
import com.magent.utils.otpgenerator.OtpGenerator;
import javassist.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by artomov.ihor on 13.05.2016.
 */
public class LoginControllerTest extends MockWebSecurityConfig {

    @Autowired
    private OtpGenerator generator;

    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    @Autowired
    private DateUtils dateUtils;

    private String sendSms;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserService userService;

    @Test
    public void loginNegativeTest() throws Exception {
        mvc.perform(post("/login")
                .param("username", "user1")
                .param("password", "bad password"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql("classpath:data.sql")
    public void loginNegativeWithOtpTest() throws Exception {
        String request = mvc.perform(post("/login")
                .param("username", "user1")
                .param("password", "bad password")
                .param("withSms", String.valueOf(true)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("message password incorrect", request.trim());
    }

    @Test
    public void loginPositiveTest() throws Exception {
        mvc.perform(post("/login")
                .param("username", testLogin)
                .param("password", testPassword))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void refreshTokenPositiveTest() throws Exception {
        mvc.perform(post("/refresh").param(header, getRefreshToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", Matchers.notNullValue()))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void refreshTokenNegativeTest() throws Exception {
        mvc.perform(post("/refresh").param(header, "bad refresh header"))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();

    }

    @Test
    @Sql("classpath:deleteTmpUsers.sql")
    @Transactional
    public void registerServiceNewUserTest() throws Exception {
        SmsService smsService = mock(SmsService.class);
        TemporaryUser temporaryUser = EntityGenerator.generateUserForRegistration();
        MockitoAnnotations.initMocks(SmsService.class);
        when(smsService.sendConfirmationAndSaveUser(temporaryUser)).thenReturn(createAndSave(temporaryUser));
        //check it saves
        Assert.assertNotNull(smsService.sendConfirmationAndSaveUser(temporaryUser));
        Assert.assertNotNull(temporaryUserRepository.getByLogin(temporaryUser.getUsername()));
        //and than confirm registration.

        Assert.assertNotNull(userService.confirmRegistration(temporaryUser.getUsername(), SecurityUtils.hashPassword(sendSms)));

    }


    private TemporaryUser createAndSave(TemporaryUser temporaryUser) {
        sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);
        String hashedPwd = SecurityUtils.hashPassword(temporaryUser.getHashedPwd());

        TemporaryUser tmpUser = new TemporaryUser(temporaryUser, dateUtils.add5Minutes(new Date()), storeSms, hashedPwd);
        return temporaryUserRepository.save(tmpUser);

    }


    public User confirmRegistration(String login, String otp) throws NotFoundException {
        TemporaryUser tmpUser = temporaryUserRepository.getByLoginAndOtp(login, otp);
        if (Objects.isNull(tmpUser)) throw new NotFoundException("current user not found");

        User user = new User(tmpUser);
        userRepository.save(user);
        deviceRepository.save(user.getDevices());

        //delete from temp users
        TemporaryUser temporaryUser = temporaryUserRepository.getByLogin(login);
        if (Objects.nonNull(temporaryUser)) temporaryUserRepository.delete(temporaryUser);

        return user;
    }

    @Test
    @Sql("classpath:data.sql")
    public void sendOtpForForgotPasswordPossitiveTest() throws Exception {

        User whoChangePwd = userRepository.findOne(3L);
        String otp = getSendSemeEmulator(whoChangePwd.getLogin(), new Date());

        mvc.perform(post("/login/changePasswordConfirm")
                .param("username", whoChangePwd.getLogin())
                .param("password", "testChange")
                .param("otp", SecurityUtils.hashPassword(otp)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:data.sql")
    public void sendOtpForForgotPasswordNegativeTest() throws Exception {

        User whoChangePwd = userRepository.findOne(3L);
        String otp = getSendSemeEmulator(whoChangePwd.getLogin(), new Date());

        mvc.perform(post("/login/changePasswordConfirm")
                .param("username", whoChangePwd.getLogin())
                .param("password", "testChange")
                .param("otp", "badOtp"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("classpath:data.sql")
    public void sendOtpForForgotPasswordNegativePassNotValidTest() throws Exception {

        User whoChangePwd = userRepository.findOne(3L);
        String otp = getSendSemeEmulator(whoChangePwd.getLogin(), new Date());

        mvc.perform(post("/login/changePasswordConfirm")
                .param("username", whoChangePwd.getLogin())
                .param("password", "badpassword")
                .param("otp", SecurityUtils.hashPassword(otp)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

