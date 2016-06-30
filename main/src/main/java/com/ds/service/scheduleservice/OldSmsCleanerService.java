package com.ds.service.scheduleservice;

import com.ds.domain.SmsPassword;
import com.ds.repository.SmsPasswordRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by artomov.ihor on 15.06.2016.
 * cleaner service for otp password
 * this service wake up every 5 minutes and delete old otp passwords from db
 * old otp considered if current time more than ds_sms_pass end period
 * end period for otp password calculated (current time + 15 minutes)
 */
@Service
public class OldSmsCleanerService {

    private static final Logger LOGGER = Logger.getLogger(OldSmsCleanerService.class);
    @Autowired
    private SmsPasswordRepository smsPasswordRepository;
    //fixed rate in milliseconds
    @Scheduled(fixedRate = (1000*60*5))
    public void cleanOldOtpPasswords() {
        List<SmsPassword> list = smsPasswordRepository.getOldSmsPass(new Date());
        if (list.size() > 0) {
            smsPasswordRepository.delete(list);
            LOGGER.info("old otp password are cleaned " + list.toString());
        }
    }


}
