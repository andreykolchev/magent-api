package com.magent.config;

import com.magent.domain.Roles;
import com.magent.domain.Template;
import com.magent.domain.TemplateType;
import com.magent.domain.TimeInterval;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.RolesRepository;
import com.magent.repository.TemplateRepository;
import com.magent.repository.TemplateTypeRpository;
import com.magent.repository.TimeIntervalRepository;
import com.magent.utils.dateutils.DateUtils;
import javassist.NotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (Objects.isNull(otp)) intervalRepository.save(new TimeInterval(OTP_INTERVAL_NAME));
        if (Objects.isNull(unregisteredUsers))
            intervalRepository.save(new TimeInterval(TMP_UNREGISTERED_USER_INTERVAL));
        if (Objects.isNull(block)) intervalRepository.save(new TimeInterval(BLOCK_INTERVAL));
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
