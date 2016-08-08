package com.magent.service;

import com.magent.config.DummyServiceTestConfig;
import com.magent.domain.*;
import com.magent.domain.interfaces.Identifiable;
import com.magent.service.interfaces.GeneralService;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by artomov.ihor on 10.05.2016.
 */
@RunWith(Parameterized.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class GeneralServiceParametrizedTest {
    private static final ApplicationContext context= new AnnotationConfigApplicationContext(DummyServiceTestConfig.class);
    private GeneralService generalService;
    private String beanName;

    public GeneralServiceParametrizedTest(String beanName) throws SQLException, IOException {
        this.beanName = beanName;
        this.generalService = (GeneralService) context.getBean(beanName);
        String content = new String(Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("data.sql"))))));
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        Connection connection=dataSource.getConnection();
        try {
            connection.prepareStatement(content).execute();
        }finally {
            connection.close();
        }
    }
    //device service skipped
    @Parameterized.Parameters
    public static List<String> getBeans() {
        return Arrays.asList("assignmentGeneralService", "activitytGeneralService", "assignmentAttributeGeneralService"
                , "assignmentTaskGeneralService", "assignmentTaskControlGeneralService", "assignmentAttributeGeneralService"
                , "callGeneralService", "locationGeneralService", "reasonGeneralService"
                , "settingsGeneralService", "templateGeneralService", "templateAttributeGeneralService"
                , "templateTaskGeneralService", "templateTaskControlGeneralService", "userGeneralService" );
    }

    /**
     * Method: getAll()
     */
    @Test
    public void testGetAll() throws Exception {
        Logger.getLogger(GeneralServiceParametrizedTest.class).debug("testing method testGetAll() in" + beanName + " service !");
        Assert.assertNotNull(generalService.getAll());
        Assert.assertTrue(generalService.getAll().size() > 0);
    }

    /**
     * Method: getById(Number id)
     */
    @Test
    public void testGetById() throws Exception {
        Logger.getLogger(GeneralServiceParametrizedTest.class).debug("testing method testGetById() in " + beanName + " service !");
        List<Identifiable> list = generalService.getAll();
        Assert.assertNotNull(generalService.getById((Number) list.get(0).getId()));
    }

    /**
     * Method: save(T enums)
     */
    @Test
    public void testSave() throws Exception {
        Logger.getLogger(GeneralServiceParametrizedTest.class).debug("testing method save() in " + beanName + " service !");

        int sizeBefore = generalService.getAll().size();
        List<Identifiable> list = generalService.getAll();

        Identifiable identifiable = (Identifiable) generalService.getById((Number) list.get(0).getId());
        identifiable.setId(0L);
        if (beanName.equals("templateGeneralService")) {
            Template template = (Template) identifiable;
            template.setTemplateTypeId(4L);
        }
        if (beanName.equals("userGeneralService")) {
            User user = (User) identifiable;
            user.setLogin("new test login");
        }
        generalService.save(identifiable);
        Assert.assertNotSame("checking it added", sizeBefore, generalService.getAll().size());
        Assert.assertTrue("it added", sizeBefore < generalService.getAll().size());

    }

    /**
     * Method: update(T enums, Number id)
     * update test for all repositories services
     */
    @Test
    public void testUpdate() throws Exception {
        Logger.getLogger(GeneralServiceParametrizedTest.class).debug("testing method update() in " + beanName + " service !");

        String updateValue = "Update test";
        if (beanName.equals("assignmentGeneralService")) {
            Assignment assignment = (Assignment) generalService.getAll().get(0);
            assignment.setDesc(updateValue);
            generalService.update(assignment, assignment.getId());
            Assert.assertEquals("check update", assignment, generalService.getAll().get(0));
            Assert.assertEquals("check value", assignment.getDesc(), ((Assignment) generalService.getAll().get(0)).getDesc());
        }
        if (beanName.equals("assignmentAttributeGeneralService")) {
            AssignmentAttribute assignmentAttribute = (AssignmentAttribute) generalService.getAll().get(0);
            long atributeId = assignmentAttribute.getId();
            assignmentAttribute.setValue(updateValue);
            generalService.update(assignmentAttribute, assignmentAttribute.getId());
            Assert.assertEquals("check update", assignmentAttribute, generalService.getById(atributeId));
            Assert.assertEquals("check value", assignmentAttribute.getValue(), ((AssignmentAttribute) generalService.getById(atributeId)).getValue());
        }
        if (beanName.equals("assignmentTaskGeneralService")) {
            AssignmentTask assignmentTask = (AssignmentTask) generalService.getAll().get(0);
            assignmentTask.setDesc(updateValue);
            long id = assignmentTask.getId();
            generalService.update(assignmentTask, assignmentTask.getId());
            Assert.assertEquals("check update", assignmentTask, generalService.getById(id));
            Assert.assertEquals("check value", assignmentTask.getDesc(), ((AssignmentTask) generalService.getById(id)).getDesc());
        }
        if (beanName.equals("assignmentTaskControlGeneralService")) {
            AssignmentTaskControl assignmentTaskControl = (AssignmentTaskControl) generalService.getAll().get(0);
            assignmentTaskControl.setValue(updateValue);
            long id = assignmentTaskControl.getId();
            generalService.update(assignmentTaskControl, assignmentTaskControl.getId());
            Assert.assertEquals("check update", assignmentTaskControl, generalService.getById(id));
            Assert.assertEquals("check value", assignmentTaskControl.getValue(), ((AssignmentTaskControl) generalService.getById(id)).getValue());
        }
        if (beanName.equals("templateGeneralService")) {
            Template template = (Template) generalService.getAll().get(0);
            template.setDesc(updateValue);
            long id = template.getId();
            generalService.update(template, template.getId());
            Assert.assertEquals("check update", template, generalService.getById(id));
            Assert.assertEquals("check value", template.getDesc(), ((Template) generalService.getById(id)).getDesc());
        }
        if (beanName.equals("activitytGeneralService")) {
            Activity activity = (Activity) generalService.getAll().get(0);
            activity.setAppName(updateValue);
            generalService.update(activity, activity.getId());
            Assert.assertEquals("check update", activity, generalService.getAll().get(0));
            Assert.assertEquals("check value", activity.getAppName(), ((Activity) generalService.getAll().get(0)).getAppName());
        }
        if (beanName.equals("callGeneralService")) {
            Call call = (Call) generalService.getAll().get(0);
            call.setCallNumber(updateValue);
            long id = call.getId();
            generalService.update(call, call.getId());
            Assert.assertEquals("check update", call, generalService.getById(id));
            Assert.assertEquals("check value", call.getCallNumber(), ((Call) generalService.getById(id)).getCallNumber());
        }
        if (beanName.equals("locationGeneralService")) {
            Location location = (Location) generalService.getAll().get(0);
            Date locationDate = new Date();
            location.setLocationDate(locationDate);
            generalService.update(location, location.getId());
            Assert.assertEquals("check update", location, generalService.getAll().get(0));
            Assert.assertEquals("check value", location.getLocationDate(), ((Location) generalService.getAll().get(0)).getLocationDate());
        }
        if (beanName.equals("reasonGeneralService")) {
            Reason reason = (Reason) generalService.getAll().get(0);
            reason.setDesc(updateValue);
            long id = reason.getId();
            generalService.update(reason, reason.getId());
            Assert.assertEquals("check update", reason, generalService.getById(id));
            Assert.assertEquals("check value", reason.getDesc(), ((Reason) generalService.getById(id)).getDesc());
        }
        if (beanName.equals("settingsGeneralService")) {
            Settings settings = (Settings) generalService.getAll().get(0);
            Long uploadStartTime = LocalDate.now().toEpochDay();
            settings.setUploadStatsStartTime(uploadStartTime);
            long id = settings.getId();
            generalService.update(settings, settings.getId());
            Assert.assertEquals("check update", settings, generalService.getById(id));
            Assert.assertEquals("check value", settings.getUploadStatsStartTime(), ((Settings) generalService.getById(id)).getUploadStatsStartTime());
        }
    }

    @Test
    public void testDelete() throws SQLException, IOException, NotFoundException {
        Logger.getLogger(GeneralServiceParametrizedTest.class).debug("testing method delete() in " + beanName + " service !");
        if (!beanName.equals("userGeneralService")
                &&!beanName.equals("templateGeneralService")) {
            int sizeBefore = generalService.getAll().size();
            Identifiable identifiable = (Identifiable) generalService.getAll().get(0);
            generalService.delete((Number) identifiable.getId());
            int sizeAfterDelete = generalService.getAll().size();
            Assert.assertNotSame("it deleted", sizeBefore, sizeAfterDelete);
            Assert.assertTrue(sizeAfterDelete < sizeBefore);
        }

        //template deleted if it don't have any assignments
        if (beanName.equals("templateGeneralService")){
            int sizeBefore = generalService.getAll().size();
            generalService.delete(2L);
            int sizeAfterDelete = generalService.getAll().size();
            Assert.assertNotSame("it deleted", sizeBefore, sizeAfterDelete);
            Assert.assertTrue(sizeAfterDelete < sizeBefore);
        }
    }
}
