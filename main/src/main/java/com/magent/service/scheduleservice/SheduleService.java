package com.magent.service.scheduleservice;

import com.magent.domain.SmsPassword;
import com.magent.domain.TemporaryUser;
import com.magent.domain.UserPersonal;
import com.magent.repository.SmsPasswordRepository;
import com.magent.repository.TemporaryUserRepository;
import com.magent.repository.UserPersonalRepository;
import com.magent.servicemodule.service.interfaces.SmsService;
import com.magent.servicemodule.service.interfaces.TimeIntervalService;
import com.magent.servicemodule.service.interfaces.UserService;
import com.magent.reportmodule.utils.dateutils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.magent.domain.enums.TimeIntervalConstants.*;

/**
 * Created on 15.06.2016.
 * cleaner service for otp password
 * this service wake up every 3 minutes and delete old otp passwords from db
 * old otp considered if current time more than ds_sms_pass end period
 * end period for otp password calculated (current time + 15 minutes)
 */
@Service
public class SheduleService {

    private static final Logger LOGGER = Logger.getLogger(SheduleService.class);
    @Autowired
    @Qualifier("smsServiceImpl")
    private SmsService smsService;

    @Autowired
    private SmsPasswordRepository passwordRepository;

    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    @Autowired
    private UserPersonalRepository userPersonalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    private DateUtils dateUtils;

    @Scheduled(fixedRate = (1000 * 60 * 3))
    public void startShedule() throws ParseException {
        cleanOldOtpPasswords();
        cleanOldNonRegisteredUsers();
        unlockBlockedUsers();
        setToZeroForgotPasswordCounter();
    }

    //method cleans old otp passwords from SmsPassword.class
    //fixed rate in milliseconds
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void cleanOldOtpPasswords() {
        List<SmsPassword> list = smsService.getOldSmsPass(dateUtils.formatToSqlDateTimeInterval(new Date()),
                dateUtils.converToTimeStamp(timeIntervalService.getByName(OTP_INTERVAL_NAME.toString()).getTimeInterval(), OTP_INTERVAL_NAME));
        if (list.size() > 0) {
            passwordRepository.delete(list);
            LOGGER.info("old otp password are cleaned " + list.toString());
        }
    }

    //method cleans old non registered users
    //fixed rate in milliseconds
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cleanOldNonRegisteredUsers() {
        List<TemporaryUser> list = userService.getUsersWithExpiredTerm(dateUtils.formatToSqlDateTimeInterval(new Date()),
                dateUtils.converToTimeStamp(timeIntervalService.getByName(TMP_UNREGISTERED_USER_INTERVAL.toString()).getTimeInterval(), TMP_UNREGISTERED_USER_INTERVAL));
        if (list.size() > 0) {
            temporaryUserRepository.delete(list);
            LOGGER.info("old non registered users are cleaned " + list.toString());
        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void unlockBlockedUsers() throws ParseException {
        List<UserPersonal> list = userService.getBlockedUsers(dateUtils.formatToSqlDateTimeInterval(new Date()),
                dateUtils.converToTimeStamp(timeIntervalService.getByName(BLOCK_INTERVAL.toString()).getTimeInterval(), BLOCK_INTERVAL));
        if (list.size() > 0) {
            for (UserPersonal personal : list) {
                personal.setBlocked(false);
                personal.setWrongEntersEntering(0);
                personal.setBlockExpired(null);
                userPersonalRepository.save(personal);
            }
            LOGGER.info("blocked users are unlock " + list.toString());
        }

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void setToZeroForgotPasswordCounter() {
        List<UserPersonal> list = userService.setToZeroForgotPassword(dateUtils.formatToSqlDateTimeInterval(new Date()),
                dateUtils.converToTimeStamp(timeIntervalService.getByName(FORGOT_PASS_INTERVAL.toString()).getTimeInterval(), FORGOT_PASS_INTERVAL));
        if (list.size() > 0) {
            for (UserPersonal personal : list) {
                personal.setForgotPwdExpireAttempt(null);
                personal.setAttemptCounter(0);
                userPersonalRepository.save(personal);
            }
            LOGGER.info("set to zero forgot password attempt for  " + list.toString() + " users");
        }
    }

}
