package com.magent.config.emulatingmethod;

import com.magent.domain.SmsPassword;
import com.magent.domain.UserPersonal;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.UserPersonalRepository;
import com.magent.repository.UserRepository;
import com.magent.utils.SecurityUtils;
import com.magent.utils.otpgenerator.OtpGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.Date;

/**
 * Created on 11.08.2016.
 */

public class MethodEmulator {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SmsPasswordRepository otpRepository;
    @Value("${attempt.quantity}")
    private int maxAttemptQuantity;
    @Autowired
    private OtpGenerator generator;
    @Autowired
    private UserPersonalRepository userPersonalRepository;

    protected MethodEmulator(){}
    //same method sendForgotPassword in sms service
    protected String getSendSemeEmulator(String toPhone, Date expiredDate) throws ValidationException {
        UserPersonal personal = userRepository.findByLogin(toPhone).getUserPersonal();
        if (personal.getAttemptCounter() < maxAttemptQuantity) {
            String sendSms = generator.generate();
            String storeSms = SecurityUtils.hashPassword(sendSms);
            storeSms = SecurityUtils.hashPassword(storeSms);
            //Sending sms via sms gate
            otpRepository.save(new SmsPassword(personal.getUserId(), personal.getUserId(), storeSms, new Date()));
            int counter=personal.getAttemptCounter();
            personal.setAttemptCounter(++counter);
            personal.setForgotPwdExpireAttempt(expiredDate);
            userPersonalRepository.save(personal);
            return sendSms;
        } else throw new ValidationException("user can change only " + maxAttemptQuantity + " times in a day");
    }

}
