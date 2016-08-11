package com.magent.controller;

import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.User;
import com.magent.domain.UserPersonal;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.UserPersonalRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.SmsService;
import com.magent.service.interfaces.TimeIntervalService;
import com.magent.service.interfaces.UserService;
import com.magent.service.scheduleservice.SheduleService;
import com.magent.utils.SecurityUtils;
import com.magent.utils.dateutils.DateUtils;
import com.magent.utils.otpgenerator.OtpGenerator;
import com.magent.utils.validators.UserValidatorImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.ValidationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.magent.domain.enums.TimeIntervalConstants.FORGOT_PASS_INTERVAL;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 11.08.2016.
 */
public class UserServiceForgotPwdFullContextTest extends MockWebSecurityConfig {
    @Autowired
    private UserRepository userRepository;

    @Value("${attempt.quantity}")
    private int maxAttemptQuantity;

    @Autowired
    private UserService userService;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    SheduleService sheduleService;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    private UserPersonalRepository userPersonalRepository;

    private final DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    @Sql("classpath:data.sql")
    public void changePasswordPossitiveTest() throws ValidationException, UserValidatorImpl.UserIsBlockedException {
        User whoChangePwd = userRepository.findOne(3L);
        String oldPassword = whoChangePwd.getUserPersonal().getPassword();
        String otp= getSendSemeEmulator(whoChangePwd.getLogin(),new Date());
        String newPassword="newPossitive";
        UserPersonal resultAfterChange=userService.changePassword(whoChangePwd.getLogin(),newPassword,SecurityUtils.hashPassword(otp));
        Assert.assertNotEquals(oldPassword,resultAfterChange.getPassword());
        Assert.assertEquals(resultAfterChange.getPassword(),
                SecurityUtils.hashPassword(SecurityUtils.hashPassword(newPassword)));


    }

    @Test(expected = ValidationException.class)
    @Sql("classpath:data.sql")
    public void changePasswordNegative() throws ValidationException, UserValidatorImpl.UserIsBlockedException {
        User whoChangePwd = userRepository.findOne(3L);
        String otp=getSendSemeEmulator(whoChangePwd.getLogin(),new Date());;
        UserPersonal resultAfterChange=userService.changePassword(whoChangePwd.getLogin(),"newPossitive",SecurityUtils.hashPassword("badOtp"));
    }
    @Test(expected = ValidationException.class)
    @Sql("classpath:data.sql")
    public void changeMoreThanTimeTest() throws ValidationException {
        User whoChangePwd = userRepository.findOne(3L);
        String login=whoChangePwd.getLogin();
        for (int i=0;i<6;i++){
            getSendSemeEmulator(login,new Date());
        }
    }

    @Test
    @Sql("classpath:data.sql")
    public void setToZeroForgotPasswordCounterTest() throws ValidationException, ParseException {

        User whoChangePwd = userRepository.findOne(3L);
        getSendSemeEmulator(whoChangePwd.getLogin(),dbFormat.parse("2001-01-01 00:00:00"));
        int before=userService.setToZeroForgotPassword(dateUtils.formatToSqlDateTimeInterval(new Date()),
                dateUtils.converToTimeStamp(timeIntervalService.getByName(FORGOT_PASS_INTERVAL.toString()).getTimeInterval(), FORGOT_PASS_INTERVAL)).size();
        sheduleService.setToZeroForgotPasswordCounter();
        int after=userService.setToZeroForgotPassword(dateUtils.formatToSqlDateTimeInterval(new Date()),
                dateUtils.converToTimeStamp(timeIntervalService.getByName(FORGOT_PASS_INTERVAL.toString()).getTimeInterval(), FORGOT_PASS_INTERVAL)).size();
        Assert.assertTrue(before>after);
    }
}
