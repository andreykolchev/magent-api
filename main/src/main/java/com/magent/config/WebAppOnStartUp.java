package com.magent.config;

import com.magent.domain.Roles;
import com.magent.domain.TimeInterval;
import com.magent.domain.enums.UserRoles;
import com.magent.reportmodule.utils.dateutils.DateUtils;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.magent.domain.enums.TimeIntervalConstants.*;

/**
 * @version 1.00
 *          configuration when startup application in servlet container.
 * @see ServletContextListener
 */
@Component
public class WebAppOnStartUp implements ServletContextListener {
    @Autowired
    @Qualifier("rolesGeneralService")
    private GeneralService<Roles> rolesGeneralService;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    @Qualifier("timeIntervalServiceImpl")
    private TimeIntervalService timeIntervalService;

    /**
     * method adds roles and time intervals in to database
     *
     * @see GeneralService
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        rolesGeneralService.saveAll(insertOrUpdateRoles());
        checkAndReplaceIfBadConditions();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * method checks valid values in ma_time_config table in database and if values less than in  com.magent.domain.enums.TimeIntervalConstants enum it replace values for a minimum required
     *
     * @see com.magent.servicemodule.service.interfaces.TimeIntervalService
     * @see com.magent.domain.enums.TimeIntervalConstants
     * @see com.magent.reportmodule.utils.dateutils.DateUtils
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = NullPointerException.class)
    private void checkAndReplaceIfBadConditions() {
        TimeInterval otp = timeIntervalService.getByName(OTP_INTERVAL_NAME.toString());
        TimeInterval unregisteredUsers = timeIntervalService.getByName(TMP_UNREGISTERED_USER_INTERVAL.toString());
        TimeInterval block = timeIntervalService.getByName(BLOCK_INTERVAL.toString());
        TimeInterval forgotPwd = timeIntervalService.getByName(FORGOT_PASS_INTERVAL.toString());
        //check and validate if exist
        if (Objects.nonNull(otp)) {
            otp.setTimeInterval(dateUtils.converToTimeStamp(otp.getTimeInterval(), OTP_INTERVAL_NAME));
            timeIntervalService.save(otp);
        }
        if (Objects.nonNull(unregisteredUsers)) {
            unregisteredUsers.setTimeInterval(dateUtils.converToTimeStamp(unregisteredUsers.getTimeInterval(), TMP_UNREGISTERED_USER_INTERVAL));
            timeIntervalService.save(unregisteredUsers);
        }
        if (Objects.nonNull(block)) {
            block.setTimeInterval(dateUtils.converToTimeStamp(block.getTimeInterval(), BLOCK_INTERVAL));
            timeIntervalService.save(block);
        }
        if (Objects.nonNull(forgotPwd)) {
            forgotPwd.setTimeInterval(dateUtils.converToTimeStamp(forgotPwd.getTimeInterval(), FORGOT_PASS_INTERVAL));
            timeIntervalService.save(forgotPwd);
        }
        //create new if not exist
        if (Objects.isNull(otp)) timeIntervalService.save(new TimeInterval(OTP_INTERVAL_NAME));
        if (Objects.isNull(unregisteredUsers))
            timeIntervalService.save(new TimeInterval(TMP_UNREGISTERED_USER_INTERVAL));
        if (Objects.isNull(block)) timeIntervalService.save(new TimeInterval(BLOCK_INTERVAL));
        if (Objects.isNull(forgotPwd)) timeIntervalService.save(new TimeInterval(FORGOT_PASS_INTERVAL));
    }

    private List<Roles> insertOrUpdateRoles() {
        List<Roles> list = new ArrayList<>();
        list.add(new Roles(UserRoles.ADMIN));
        list.add(new Roles(UserRoles.BACK_OFFICE_EMPLOYEE));
        list.add(new Roles(UserRoles.REMOTE_SELLER_STAFFER));
        list.add(new Roles(UserRoles.SALES_AGENT_FREELANCER));
        list.add(new Roles(UserRoles.SALES_AGENT_FREELANCER_LEAD_GEN));
        return list;
    }


}
