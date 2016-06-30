package com.ds.service.generalservicetest;

import com.ds.service.interfaces.GeneralService;
import com.ds.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by artomov.ihor on 27.04.2016.
 */
@Component
public class GeneralServiceBeansConfig extends ServiceConfig {
    @Autowired
    @Qualifier("assignmentGeneralService")
    protected GeneralService assignmentService;

    @Autowired
    @Qualifier("assignmentAttributeGeneralService")
    protected GeneralService assignmentAtributeService;

    @Autowired
    @Qualifier("assignmentTaskGeneralService")
    protected GeneralService assignmentTaskService;

    @Autowired
    @Qualifier("assignmentTaskControlGeneralService")
    protected GeneralService assignmentTaskControlService;

    @Autowired
    @Qualifier("templateGeneralService")
    protected GeneralService templateService;

    @Autowired
    @Qualifier("templateAttributeGeneralService")
    protected GeneralService templateAttributeService;

    @Autowired
    @Qualifier("templateTaskGeneralService")
    protected GeneralService templateTaskService;

    @Autowired
    @Qualifier("templateTaskControlGeneralService")
    protected GeneralService templateTaskControlService;

    @Autowired
    @Qualifier("activitytGeneralService")
    protected GeneralService activityService;

    @Autowired
    @Qualifier("callGeneralService")
    protected GeneralService callService;

    @Autowired
    @Qualifier("deviceGeneralService")
    protected GeneralService deviceService;

    @Autowired
    @Qualifier("locationGeneralService")
    protected GeneralService locationService;

    @Autowired
    @Qualifier("reasonGeneralService")
    protected GeneralService reasonService;

    @Autowired
    @Qualifier("settingsGeneralService")
    protected GeneralService settingsService;

    @Autowired
    @Qualifier("userGeneralService")
    protected GeneralService userService;

    @Autowired
    protected DataSource dataSource;

    private final List<GeneralService>beanList=new ArrayList<>();

    protected List<GeneralService> getBeanList(){
        beanList.clear();
        beanList.add(assignmentService);
        beanList.add(activityService);
        beanList.add(assignmentAtributeService);
        beanList.add(assignmentTaskService);
        beanList.add(assignmentTaskControlService);
        beanList.add(callService);
        beanList.add(locationService);
        beanList.add(reasonService);
        beanList.add(settingsService);
        beanList.add(templateService);
        beanList.add(templateAttributeService);
        beanList.add(templateTaskService);
        beanList.add(templateTaskControlService);
        beanList.add(userService);
        return beanList;

    }
}
