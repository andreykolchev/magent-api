package com.magent.service;

import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.SmsPassword;
import com.magent.domain.User;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.SmsService;
import com.magent.utils.dateutils.DateUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by artomov.ihor on 13.06.2016.
 */
public class SmsServiceImplTest extends MockWebSecurityConfig {
    @Autowired
    private SmsService smsService;
    @Autowired
    private SmsPasswordRepository smsPasswordRepository;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private UserRepository userRepository;
    //only sms sender test
    @Test
    @Ignore
    @Sql("classpath:data.sql")
    public void sensSimpleSmsTest() throws IOException {
        smsService.isConfirmationSended("+380978090838");
    }
    @Test
    @Sql("classpath:data.sql")
    public void deleteOldSms(){
        int userSize=userRepository.findAll().size();
        smsPasswordRepository.save(new SmsPassword(1L,1L,"123",new Date()));
        List<SmsPassword>passwordList=smsPasswordRepository.getOldSmsPass(new Date());
        Assert.assertTrue(passwordList.size()>0);
        smsPasswordRepository.delete(passwordList);
        passwordList=smsPasswordRepository.getOldSmsPass(new Date());
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
}
