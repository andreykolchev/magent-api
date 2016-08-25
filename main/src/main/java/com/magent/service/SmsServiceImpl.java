package com.magent.service;

import com.magent.domain.SmsPassword;
import com.magent.domain.TemporaryUser;
import com.magent.domain.User;
import com.magent.domain.UserPersonal;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.TemporaryUserRepository;
import com.magent.repository.UserPersonalRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.SmsService;
import com.magent.service.interfaces.TimeIntervalService;
import com.magent.utils.SecurityUtils;
import com.magent.utils.dateutils.DateUtils;
import com.magent.utils.otpgenerator.OtpGenerator;
import javassist.NotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.magent.domain.enums.TimeIntervalConstants.*;

/**
 * Created  on 13.06.2016.
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Value(("${sms.host}"))
    private String smsHost;

    @Value("${attempt.quantity}")
    private int maxAttemptQuantity;

    @Value("${sms.send.real}")
    private boolean isSmsGateActive;

    @Autowired
    private UserRepository userRepository;
    //send and store sms in db
    @Autowired
    private OtpGenerator generator;
    @Autowired
    private SmsPasswordRepository otpRepository;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private UserPersonalRepository userPersonalRepository;



    private final RestTemplate template=new RestTemplate();

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendOtpForRegisteredUser(String toPhone) throws IOException, ParseException {

        User user = userRepository.findByLogin(toPhone);
        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);
        storeSms = SecurityUtils.hashPassword(storeSms);
        //Sending sms via sms gate
        template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, toPhone, OtpConstants.SEND_LOGIN_CONFIRMATION + sendSms);
        //storing
        otpRepository.save(new SmsPassword(user.getId(), user.getId(), storeSms, new Date()));
        return getEndSmsPeriod();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendConfirmationAndSaveUser(TemporaryUser temporaryUser) throws ValidationException, ParseException {
        if (Objects.nonNull(temporaryUserRepository.getByLogin(temporaryUser.getUsername())))
            throw new ValidationException("current user already waited for registration");
        if (Objects.nonNull(userRepository.findByLogin(temporaryUser.getUsername())))
            throw new ValidationException("current user already registered");

        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);
        String hashedPwd = SecurityUtils.hashPassword(temporaryUser.getHashedPwd());
        //password hashed 2 times
        //saved current date. For more information how it's works see SheduleService.class
        TemporaryUser tmpUser = new TemporaryUser(temporaryUser, new Date(), storeSms, SecurityUtils.hashPassword(hashedPwd));
        tmpUser = temporaryUserRepository.save(tmpUser);
        template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, temporaryUser.getUsername(), OtpConstants.REGISTER_CONFIRMATION + sendSms);

        return getEndSmsPeriod();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String recentConfirmation(String login) throws NotFoundException, ParseException {
        TemporaryUser temporaryUser = temporaryUserRepository.getByLogin(login);
        if (Objects.isNull(temporaryUser)) throw new NotFoundException("user not present in db. Please register again");
        String sendSms = generator.generate();
        String storeSms = SecurityUtils.hashPassword(sendSms);

        temporaryUser.setHashedOtp(storeSms);
        temporaryUser.setEndPeriod(new Date());
        template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, temporaryUser.getUsername(), OtpConstants.REGISTER_CONFIRMATION + sendSms);
        temporaryUserRepository.save(temporaryUser);
        return getEndSmsPeriod();
    }

    @Override
    public void sendSuccessfullRegistration(String login) {
        template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, login, OtpConstants.SUCCESS_REGISTRATION + login);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SmsPassword> getOldSmsPass(String sqlDate, String timeFromConfig) {
        Session session = entityManager.unwrap(Session.class);
        String queryStr = "SELECT oldsms.* FROM ma_sms_pass oldsms WHERE oldsms.endperiod+" + "'" + timeFromConfig + "'" + "<" + "'" + sqlDate + "'";
        Query query = session.createSQLQuery(queryStr).addEntity(SmsPassword.class);
        return query.list();
    }


    @Override
    @Transactional(readOnly = true)
    public String getEndSmsPeriod() throws ParseException {
        return dateUtils.converToTimeStamp(timeIntervalService.getByName(OTP_INTERVAL_NAME.toString()).getTimeInterval(), OTP_INTERVAL_NAME);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendForgotPassword(String toPhone) throws ValidationException, ParseException {
        UserPersonal personal = userRepository.findByLogin(toPhone).getUserPersonal();
        if (personal.getAttemptCounter() < maxAttemptQuantity) {
            String sendSms = generator.generate();
            String storeSms = SecurityUtils.hashPassword(sendSms);
            storeSms = SecurityUtils.hashPassword(storeSms);
            //Sending sms via sms gate
            template.getForObject(smsHost + OtpConstants.PATTERN_FOR_SMS_GATE, String.class, toPhone, OtpConstants.SEND_LOGIN_CONFIRMATION + sendSms);
            otpRepository.save(new SmsPassword(personal.getUserId(), personal.getUserId(), storeSms, new Date()));
            Date startDateForSheduler = personal.getAttemptCounter() == 0 ? new Date() : personal.getForgotPwdExpireAttempt();
            int counter=personal.getAttemptCounter();
            personal.setAttemptCounter(++counter);
            personal.setForgotPwdExpireAttempt(startDateForSheduler);
            userPersonalRepository.save(personal);
            return getEndSmsPeriod();
        }
        else throw new ValidationException("user can change only "+maxAttemptQuantity+" times in a day");
    }

    private static final class OtpConstants {

        private static final String SEND_LOGIN_CONFIRMATION = "mAgent your temporary password is  ";
        private static final String PATTERN_FOR_SMS_GATE = "/?phone={phone}&message={sms}";
        private static final String REGISTER_CONFIRMATION = "mAgent your activation code for account is ";
        private static final String SUCCESS_REGISTRATION = " mAgent successful registration for login ";
    }
}
