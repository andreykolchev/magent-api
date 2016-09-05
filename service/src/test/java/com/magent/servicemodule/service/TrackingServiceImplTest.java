package com.magent.servicemodule.service;

import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.TrackingService;
import com.magent.servicemodule.utils.EntityGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

/**
 * TrackingServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 18, 2016</pre>
 */
public class TrackingServiceImplTest extends ServiceModuleServiceConfig {

    @Autowired
    private TrackingService trackingService;
    @Autowired
    @Qualifier("callGeneralService")
    private GeneralService callGenService;
    @Autowired
    @Qualifier("locationGeneralService")
    private GeneralService locationGenService;

    @Autowired
    @Qualifier("activitytGeneralService")
    private GeneralService activityGenService;

    /**
     * Method: getSettings()
     */
    @Test
    public void testGetSettings() throws Exception {
//TODO: ComissionCalculatorImplTest goes here...
    }

    /**
     * Method: createCalls(List<Call> callList)
     */
    @Test
    public void testCreateCalls() throws Exception {
//TODO: ComissionCalculatorImplTest goes here...
    }

    /**
     * Method: createLocations(Location location)
     */
    @Test
    public void testCreateLocations() throws Exception {
        int sizeBefore = locationGenService.getAll().size();
        trackingService.createLocations(EntityGenerator.getNewTestLocation(),1L);
        int sizeAfter = locationGenService.getAll().size();
        Assert.assertNotSame(sizeBefore, sizeAfter);
        Assert.assertTrue(sizeBefore < sizeAfter);
    }

    /**
     * Method: createActivities(List<Activity> activityList)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testCreateActivities() throws Exception {
        int sizeBefore = activityGenService.getAll().size();
        trackingService.createActivities(Arrays.asList(EntityGenerator.getNewTestActivity()),1L);
        int sizeAfter = activityGenService.getAll().size();
        Assert.assertNotSame(sizeBefore, sizeAfter);
        Assert.assertTrue(sizeBefore < sizeAfter);
    }


    /**
     * Method: createCall(Call dto)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testCreateCall() throws Exception {
        int sizeBefore = callGenService.getAll().size();
        trackingService.createCalls(Arrays.asList(EntityGenerator.getNewCall()),1L);
        int sizeAfter = callGenService.getAll().size();
        Assert.assertNotSame(sizeBefore, sizeAfter);
        Assert.assertTrue(sizeBefore < sizeAfter);
    }
} 
