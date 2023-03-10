package com.magent.servicemodule.service.impl;

import com.magent.authmodule.utils.SecurityUtils;
import com.magent.authmodule.utils.otpgenerator.OtpGenerator;
import com.magent.domain.SmsPassword;
import com.magent.domain.TemporaryUser;
import com.magent.domain.User;
import com.magent.domain.UserPersonal;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.TemporaryUserRepository;
import com.magent.repository.UserPersonalRepository;
import com.magent.repository.UserRepository;
import com.magent.servicemodule.service.interfaces.SmsService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.Objects;

/**
 * Created on 17.08.2016.
 * @author artomov.ihor
 * @since 29/04/2016
 * service for Sms demo operations
 */
@Service
class SmsDemoServiceImpl implements SmsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpGenerator generator;

    @Autowired
    private SmsPasswordRepository smsPasswordRepository;

    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    @Autowired
    private UserPersonalRepository userPersonalRepository;

    @Value("${attempt.quantity}")
    private int maxAttemptQuantity;

    /**
     * send OTP for the sign in operation
     * @param toPhone Phone number
     * @return sms
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendOtpForRegisteredUser(String toPhone) {
        User user = userRepository.findByLogin(toPhone);
        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);
        storeSms = SecurityUtils.hashPassword(storeSms);
        //storing
        smsPasswordRepository.save(new SmsPassword(user.getId(), user.getId(), storeSms, new Date()));
        return sendSms;
    }

    /**
     * send OTP for the sign up operation and save TemporaryUser
     * @param temporaryUser TemporaryUser entity for persist
     * @return sms as String
     * @throws ValidationException if temporaryUser or user already present in db
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendConfirmationAndSaveUser(TemporaryUser temporaryUser) throws ValidationException {
        if (Objects.nonNull(temporaryUserRepository.getByLogin(temporaryUser.getUsername())))
            throw new ValidationException("current user already waited for registration");
        if (Objects.nonNull(userRepository.findByLogin(temporaryUser.getUsername())))
            throw new ValidationException("current user already registered");

        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);
        String hashedPwd = SecurityUtils.hashPassword(temporaryUser.getHashedPwd());
        //password hashed 2 times
        //saved current date. For more information how it's works see SheduleService.class
        TemporaryUser storedUser = new TemporaryUser(temporaryUser, new Date(), storeSms, SecurityUtils.hashPassword(hashedPwd));
        temporaryUserRepository.save(storedUser);
        return sendSms;
    }

    /**
     * resend confirmation OTP
     * @param login login(phone number)
     * @return sms(OTP)
     * @throws NotFoundException if temporaryUser not found by login
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String recentConfirmation(String login) throws NotFoundException {
        TemporaryUser temporaryUser = temporaryUserRepository.getByLogin(login);
        if (Objects.isNull(temporaryUser)) throw new NotFoundException("user not present in db. Please register again");
        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);

        temporaryUser.setHashedOtp(storeSms);
        temporaryUser.setEndPeriod(new Date());

        temporaryUserRepository.save(temporaryUser);
        return sendSms;
    }

    /**
     * send confirmation OTP for the password change operation
     * @param toPhone - user login (Phone)
     * @return - non hashed otp number as String
     * @throws ValidationException if (UserPersonal.getAttemptCounter() < maxAttemptQuantity) attempt.quantity=5
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendForgotPassword(String toPhone) throws ValidationException {
        UserPersonal personal = userRepository.findByLogin(toPhone).getUserPersonal();
        if (personal.getAttemptCounter() < maxAttemptQuantity) {
            String sendSms = generator.generate();
            String storeSms = SecurityUtils.hashPassword(sendSms);
            storeSms = SecurityUtils.hashPassword(storeSms);

            smsPasswordRepository.save(new SmsPassword(personal.getUserId(), personal.getUserId(), storeSms, new Date()));
            Date startDateForSheduler = personal.getAttemptCounter() == 0 ? new Date() : personal.getForgotPwdExpireAttempt();
            int counter = personal.getAttemptCounter();
            personal.setAttemptCounter(++counter);
            personal.setForgotPwdExpireAttempt(startDateForSheduler);
            userPersonalRepository.save(personal);
            return sendSms;
        } else throw new ValidationException("user can change only " + maxAttemptQuantity + " times in a day");
    }
}
