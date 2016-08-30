package com.magent.service;

import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.SmsPassword;
import com.magent.domain.User;
import com.magent.domain.enums.TimeIntervalConstants;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.TimeIntervalRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.SmsService;
import com.magent.service.interfaces.TimeIntervalService;
import com.magent.reportmodule.utils.dateutils.DateUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.magent.domain.enums.TimeIntervalConstants.OTP_INTERVAL_NAME;

/**
 * Created by artomov.ihor on 13.06.2016.
 */
public class SmsServiceImplTest extends MockWebSecurityConfig {
    @Autowired
    @Qualifier("smsServiceImpl")
    private SmsService smsService;
    @Autowired
    private SmsPasswordRepository smsPasswordRepository;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimeIntervalRepository timeIntervalRepository;
    @Autowired
    private TimeIntervalService timeIntervalService;

    private final DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //only sms sender test
    @Test
    @Ignore
    @Sql("classpath:data.sql")
    public void sensSimpleSmsTest() throws IOException, ParseException {
        smsService.sendOtpForRegisteredUser("+380978090838");
    }
    @Test
    @Sql("classpath:data.sql")
    public void deleteOldSms(){
        int userSize=userRepository.findAll().size();
        smsPasswordRepository.save(new SmsPassword(1L,1L,"123",new Date()));
        List<SmsPassword>passwordList=smsService.getOldSmsPass(dateUtils.formatToSqlDate(new Date()),dateUtils.converToTimeStamp(timeIntervalRepository.getByName(TimeIntervalConstants.OTP_INTERVAL_NAME.toString()).getTimeInterval(),TimeIntervalConstants.OTP_INTERVAL_NAME));
        Assert.assertTrue(passwordList.size()>0);
        smsPasswordRepository.delete(passwordList);
        passwordList=smsService.getOldSmsPass(dateUtils.formatToSqlDate(new Date()),dateUtils.converToTimeStamp(timeIntervalRepository.getByName(TimeIntervalConstants.OTP_INTERVAL_NAME.toString()).getTimeInterval(),TimeIntervalConstants.OTP_INTERVAL_NAME));
        Assert.assertEquals(0,passwordList.size());
        Assert.assertEquals(userSize,userRepository.findAll().size());
    }

    @Test
    public void saveSms(){
        User user=userRepository.findByLogin("+380506847580");
        SmsPassword password=new SmsPassword(user.getId(),user.getId(),"123456789",new Date());
        smsPasswordRepository.save(password);
        Assert.assertNotNull(smsPasswordRepository.findOne(user.getId()));
    }
    @Test
    public void getOldSmsPassTest() throws ParseException {
        //pre conditions
        User user=userRepository.findByLogin("+380506847580");
        SmsPassword password=new SmsPassword(user.getId(),user.getId(),"123456789",dbFormat.parse("2015-01-01 00:00:00"));
        smsPasswordRepository.save(password);
        //test
        String timeInterval = timeIntervalService.getByName(OTP_INTERVAL_NAME.toString()).getTimeInterval();
        String date = dateUtils.formatToSqlDateTimeInterval(new Date());
        List<SmsPassword> smsPasswordList = smsService.getOldSmsPass(date, dateUtils.converToTimeStamp(timeInterval, OTP_INTERVAL_NAME));
        Assert.assertEquals(1,smsPasswordList.size());
    }
}
