package com.magent.service;

import com.magent.config.MockWebSecurityConfig;
import com.magent.domain.TemporaryUser;
import com.magent.domain.UserPersonal;
import com.magent.reportmodule.utils.dateutils.DateUtils;
import com.magent.service.scheduleservice.SheduleService;
import com.magent.servicemodule.service.interfaces.SmsService;
import com.magent.servicemodule.service.interfaces.TimeIntervalService;
import com.magent.servicemodule.service.interfaces.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;

import static com.magent.domain.enums.TimeIntervalConstants.*;

/**
 * SheduleService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 3, 2016</pre>
 */
public class SheduleServiceTest extends MockWebSecurityConfig {
    @Autowired
    private SheduleService sheduleService;

    @Autowired
    @Qualifier("smsServiceImpl")
    private SmsService smsService;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private UserService userService;

    /**
     * Method: cleanOldOtpPasswords()
     */
    @Test
    @Sql("classpath:data.sql")
    public void testCleanOldOtpPasswords() throws Exception {
        //pre condition
        //start test
        String timeInterval = timeIntervalService.getByName(OTP_INTERVAL_NAME.toString()).getTimeInterval();
        String date = dateUtils.formatToSqlDateTimeInterval(new Date());
        //get size before delete
        int beforeSheduler=smsService.getOldSmsPass(date, dateUtils.converToTimeStamp(timeInterval, OTP_INTERVAL_NAME)).size();;
        //test
        sheduleService.cleanOldOtpPasswords();
        int afterSheduler=smsService.getOldSmsPass(date, dateUtils.converToTimeStamp(timeInterval, OTP_INTERVAL_NAME)).size();
        Assert.assertTrue("check is sheduler works fine",beforeSheduler>afterSheduler);
    }

    /**
     * Method: cleanOldNonRegisteredUsers()
     */
    @Test
    @Sql("classpath:data.sql")
    public void testCleanOldNonRegisteredUsers() throws Exception {
        String timeInt = timeIntervalService.getByName(TMP_UNREGISTERED_USER_INTERVAL.toString()).getTimeInterval();
        String date = dateUtils.formatToSqlDateTimeInterval(new Date());
        List<TemporaryUser> temporaryUserList = userService.getUsersWithExpiredTerm(date, dateUtils.converToTimeStamp(timeInt, TMP_UNREGISTERED_USER_INTERVAL));
        int beforeSheduler=temporaryUserList.size();
        //test
        sheduleService.cleanOldNonRegisteredUsers();
        temporaryUserList = userService.getUsersWithExpiredTerm(date, dateUtils.converToTimeStamp(timeInt, TMP_UNREGISTERED_USER_INTERVAL));
        int afterSheduler=temporaryUserList.size();

        Assert.assertTrue("check is sheduler works fine",beforeSheduler>afterSheduler);
    }

    /**
     * Method: unlockBlockedUsers()
     */
    @Test
    @Sql("classpath:data.sql")
    public void testUnlockBlockedUsers() throws Exception {
        String timeInt = timeIntervalService.getByName(BLOCK_INTERVAL.toString()).getTimeInterval();
        String date = dateUtils.formatToSqlDateTimeInterval(new Date());
        List<UserPersonal> userPersonalList = userService.getBlockedUsers(date,
                dateUtils.converToTimeStamp(timeInt, BLOCK_INTERVAL));

        int beforeSheduler=userPersonalList.size();
        //test
        sheduleService.unlockBlockedUsers();
        userPersonalList = userService.getBlockedUsers(date,
                dateUtils.converToTimeStamp(timeInt, BLOCK_INTERVAL));
        int afterSheduler=userPersonalList.size();

        Assert.assertTrue("check is sheduler works fine",beforeSheduler>afterSheduler);

    }

} 
