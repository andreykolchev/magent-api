package com.magent.service;

import com.magent.config.MockWebSecurityConfig;
import com.magent.config.ServiceConfig;
import com.magent.domain.SmsPassword;
import com.magent.domain.TemporaryUser;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.TemporaryUserRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.SmsService;
import com.magent.utils.EntityGenerator;
import com.magent.utils.SecurityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

/**
 * SmsDemoServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>August. 17, 2016</pre>
 */
public class SmsDemoServiceImplTest extends MockWebSecurityConfig {
    @Autowired
    @Qualifier("smsDemoServiceImpl")
    private SmsService smsDemoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsPasswordRepository otpRepository;

    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    /**
     * Method: sendOtpForRegisteredUser(String toPhone)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testSendOtpForRegisteredUser() throws Exception {
        String answer = smsDemoService.sendOtpForRegisteredUser(testLogin);

        Assert.assertEquals("check generate numbers there are should contains 8 characters", 8, answer.length());
        Long userId = userRepository.findByLogin(testLogin).getId();
        SmsPassword actual = otpRepository.findOne(userId);
        //check it saved and it save current otp
        String hashedOtp = SecurityUtils.hashPassword(SecurityUtils.hashPassword(answer));
        Assert.assertEquals("check it saved and it save current otp", hashedOtp, actual.getSmsPass());
    }

    /**
     * Method: sendConfirmationAndSaveUser(TemporaryUser temporaryUser)
     * temp otp hashed one time for temp user
     */
    @Test
    @Sql("classpath:deleteTmpUsers.sql")
    public void testSendConfirmationAndSaveUser() throws Exception {
        TemporaryUser temporaryUser = EntityGenerator.generateUserForRegistration();
        String answer = (String) smsDemoService.sendConfirmationAndSaveUser(temporaryUser);

        Assert.assertEquals("check generate numbers there are should contains 8 characters", 8, answer.length());
        Assert.assertNotNull("check it stored in db ", temporaryUserRepository.getByLogin(temporaryUser.getUsername()));
        String actualHashedOtp = SecurityUtils.hashPassword(answer);
        Assert.assertEquals("check is otp correct", actualHashedOtp, temporaryUserRepository.getByLogin(temporaryUser.getUsername()).getHashedOtp());
    }

    /**
     * Method: recentConfirmation(String login)
     */
    @Test
    @Sql("classpath:deleteTmpUsers.sql")
    public void testRecentConfirmation() throws Exception {
        //pre conditions
        TemporaryUser temporaryUser = EntityGenerator.generateUserForRegistration();
        String answer = (String) smsDemoService.sendConfirmationAndSaveUser(temporaryUser);
        Assert.assertEquals("check generate numbers there are should contains 8 characters", 8, answer.length());
        //test
        answer = smsDemoService.recentConfirmation(temporaryUser.getUsername());
        Assert.assertEquals("check generate numbers there are should contains 8 characters", 8, answer.length());
        temporaryUser = temporaryUserRepository.getByLogin(temporaryUser.getUsername());
        Assert.assertEquals("check it chamge otp", SecurityUtils.hashPassword(answer), temporaryUser.getHashedOtp());
    }

    /**
     * Method: sendForgotPassword(String toPhone)
     * sms hashed 2 times see SecurityUtils
     */
    @Test
    @Transactional
    @Sql("classpath:data.sql")
    public void testSendForgotPassword() throws Exception {
        String answer = smsDemoService.sendForgotPassword(testLogin);
        Assert.assertEquals("check generate numbers there are should contains 8 characters", 8, answer.length());

        Long userId=userRepository.findByLogin(testLogin).getId();
        SmsPassword smsPassword=otpRepository.getOne(userId);

        Assert.assertEquals("check it saved otp which sended ",
                SecurityUtils.hashPassword(SecurityUtils.hashPassword(answer)),
                smsPassword.getSmsPass());

    }


} 
