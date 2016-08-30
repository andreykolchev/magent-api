package com.magent.config;

import com.magent.domain.Roles;
import com.magent.domain.TimeInterval;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.RolesRepository;
import com.magent.repository.TimeIntervalRepository;
import com.magent.reportmodule.utils.dateutils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created  on 07.06.2016.
 */
@Component
public class WebAppOnStartUp implements ServletContextListener {
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private TimeIntervalRepository intervalRepository;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        rolesRepository.save(insertOrUpdateRoles());
        checkAndReplaceIfBadConditions();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = NullPointerException.class)
    private void checkAndReplaceIfBadConditions() {
        TimeInterval otp = intervalRepository.getByName(OTP_INTERVAL_NAME.toString());
        TimeInterval unregisteredUsers = intervalRepository.getByName(TMP_UNREGISTERED_USER_INTERVAL.toString());
        TimeInterval block = intervalRepository.getByName(BLOCK_INTERVAL.toString());
        TimeInterval forgotPwd = intervalRepository.getByName(FORGOT_PASS_INTERVAL.toString());
        //check and validate if exist
        if (Objects.nonNull(otp)) {
            otp.setTimeInterval(dateUtils.converToTimeStamp(otp.getTimeInterval(), OTP_INTERVAL_NAME));
            intervalRepository.save(otp);
        }
        if (Objects.nonNull(unregisteredUsers)) {
            unregisteredUsers.setTimeInterval(dateUtils.converToTimeStamp(unregisteredUsers.getTimeInterval(), TMP_UNREGISTERED_USER_INTERVAL));
            intervalRepository.save(unregisteredUsers);
        }
        if (Objects.nonNull(block)) {
            block.setTimeInterval(dateUtils.converToTimeStamp(block.getTimeInterval(), BLOCK_INTERVAL));
            intervalRepository.save(block);
        }
        if (Objects.nonNull(forgotPwd)) {
            forgotPwd.setTimeInterval(dateUtils.converToTimeStamp(forgotPwd.getTimeInterval(), FORGOT_PASS_INTERVAL));
            intervalRepository.save(forgotPwd);
        }
        //create new if not exist
        if (Objects.isNull(otp)) intervalRepository.save(new TimeInterval(OTP_INTERVAL_NAME));
        if (Objects.isNull(unregisteredUsers))
            intervalRepository.save(new TimeInterval(TMP_UNREGISTERED_USER_INTERVAL));
        if (Objects.isNull(block)) intervalRepository.save(new TimeInterval(BLOCK_INTERVAL));
        if (Objects.isNull(forgotPwd)) intervalRepository.save(new TimeInterval(FORGOT_PASS_INTERVAL));
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
