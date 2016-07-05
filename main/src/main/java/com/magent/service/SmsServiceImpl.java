package com.magent.service;

import com.magent.domain.SmsPassword;
import com.magent.domain.User;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.SmsService;
import com.magent.utils.SecurityUtils;
import com.magent.utils.dateutils.DateUtils;
import com.magent.utils.otpgenerator.OtpGenerator;
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
