package com.magent.config;

import com.magent.domain.Roles;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 07.06.2016.
 */
@Component
public class WebAppOnStartUp implements ServletContextListener {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        rolesRepository.save(insertOrUpdateRoles());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

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
