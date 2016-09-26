package com.magent.config.emulatingmethod;

import com.magent.authmodule.utils.otpgenerator.OtpGenerator;
import com.magent.domain.SmsPassword;
import com.magent.domain.UserPersonal;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.UserService;
import com.magent.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.ValidationException;
import java.util.Date;

/**
 * Created on 11.08.2016.
 */

public class MethodEmulator {
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("smsPasswordGeneralService")
    private GeneralService<SmsPassword> otpRepository;

    @Value("${attempt.quantity}")
    private int maxAttemptQuantity;

    @Autowired
    private OtpGenerator generator;

    @Autowired
    @Qualifier("userPersonalGeneralService")
    private GeneralService<UserPersonal> userPersonalGeneralService;

    protected MethodEmulator() {
    }

    //same method sendForgotPassword in sms service
    protected String getSendSemeEmulator(String toPhone, Date expiredDate) throws ValidationException {
        UserPersonal personal = userService.findByLogin(toPhone).getUserPersonal();
        if (personal.getAttemptCounter() < maxAttemptQuantity) {
            String sendSms = generator.generate();
            String storeSms = SecurityUtils.hashPassword(sendSms);
            storeSms = SecurityUtils.hashPassword(storeSms);
            //Sending sms via sms gate
            otpRepository.save(new SmsPassword(personal.getUserId(), personal.getUserId(), storeSms, new Date()));
            int counter = personal.getAttemptCounter();
            personal.setAttemptCounter(++counter);
            personal.setForgotPwdExpireAttempt(expiredDate);
            userPersonalGeneralService.save(personal);
            return sendSms;
        } else throw new ValidationException("user can change only " + maxAttemptQuantity + " times in a day");
    }

}
