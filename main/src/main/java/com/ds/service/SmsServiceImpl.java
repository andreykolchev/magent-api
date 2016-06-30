package com.ds.service;

import com.ds.domain.SmsPassword;
import com.ds.domain.User;
import com.ds.repository.SmsPasswordRepository;
import com.ds.repository.UserRepository;
import com.ds.service.interfaces.SmsService;
import com.ds.utils.SecurityUtils;
import com.ds.utils.dateutils.DateUtils;
import com.ds.utils.otpgenerator.OtpGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

/**
 * Created by artomov.ihor on 13.06.2016.
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Value(("${sms.host}"))
    private String smsHost;
    @Autowired
    private UserRepository userRepository;
    //send and store sms in db
    @Autowired
    private OtpGenerator generator;
    @Autowired
    private SmsPasswordRepository otpRepository;
    @Autowired
    private DateUtils dateUtils;
    private static final RestTemplate template = new RestTemplate();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void isConfirmationSended(String toPhone) throws IOException {
        //storing
        User user = userRepository.findByLogin(toPhone);
        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);
        storeSms = SecurityUtils.hashPassword(storeSms);
        otpRepository.save(new SmsPassword(user.getId(), user.getId(), storeSms, dateUtils.add15Minutes(new Date())));
        //Sending sms via sms gate
        template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, toPhone, OtpConstants.SEND_LOGIN_CONFIRMATION + sendSms);
    }

    private static final class OtpConstants {

        private static final String SEND_LOGIN_CONFIRMATION = "mAgent vash vremeniy parol dlya vhoda ";
        private static final String PATTERN_FOR_SMS_GATE = "/?phone={phone}&message={sms}";
    }
}
