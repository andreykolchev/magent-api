package com.magent.service;

import com.magent.domain.SmsPassword;
import com.magent.domain.TemporaryUser;
import com.magent.domain.User;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.TemporaryUserRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.SmsService;
import com.magent.utils.SecurityUtils;
import com.magent.utils.dateutils.DateUtils;
import com.magent.utils.otpgenerator.OtpGenerator;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

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

    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemporaryUser sendConfirmationAndSaveUser(TemporaryUser temporaryUser) throws ValidationException {
        if (Objects.nonNull(temporaryUserRepository.getByLogin(temporaryUser.getLogin())))
            throw new ValidationException("current user already waited for registration");
        if (Objects.nonNull(userRepository.findByLogin(temporaryUser.getLogin())))
            throw new ValidationException("current user already registered");

        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);
        String hashedPwd = SecurityUtils.hashPassword(temporaryUser.getHashedPwd());

        TemporaryUser tmpUser = new TemporaryUser(temporaryUser, dateUtils.add5Minutes(new Date()), storeSms, hashedPwd);
        tmpUser = temporaryUserRepository.save(tmpUser);
        template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, temporaryUser.getLogin(), OtpConstants.REGISTER_CONFIRMATION + sendSms);

        return tmpUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemporaryUser recentConfirmation(String login) throws NotFoundException {
        TemporaryUser temporaryUser = temporaryUserRepository.getByLogin(login);
        if (Objects.isNull(temporaryUser)) throw new NotFoundException("user not present in db. Please register again");
        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);
        temporaryUser.setHashedOtp(storeSms);
        temporaryUser.setEndPeriod(dateUtils.add5Minutes(new Date()));
        template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, temporaryUser.getLogin(), OtpConstants.REGISTER_CONFIRMATION + sendSms);
        temporaryUserRepository.save(temporaryUser);
        return temporaryUser;
    }

    @Override
    public void sendSuccessfullRegistration(String login) {
        template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, login, OtpConstants.SUCCESS_REGISTRATION + login);
    }

    private static final class OtpConstants {

        private static final String SEND_LOGIN_CONFIRMATION = "mAgent your temporary password is  ";
        private static final String PATTERN_FOR_SMS_GATE = "/?phone={phone}&message={sms}";
        private static final String REGISTER_CONFIRMATION = "mAgent your activation code for account is ";
        private static final String SUCCESS_REGISTRATION = " mAgent successful registration for login ";
    }
}
