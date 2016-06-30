package com.ds;

import com.ds.controller.*;
import com.ds.securitycheck.AssignmentsSecControllerTest;
import com.ds.securitycheck.TemplatesSecControllerTest;
import com.ds.securitycheck.UsersSecControllerTest;
import com.ds.service.OauthServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by artomov.ihor on 20.05.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AssignmentControllerImplTest.class,
        DataControllerImplTest.class,
        DeviceControllerImplTest.class,
        LoginControllerTest.class,
        TemplateControllerImplTest.class,
        TrackingControllerImplTest.class,
        ReasonControllerImplTest.class,
        UserControllerTest.class,
        ReportsControllerImplTest.class,
        AssignmentsSecControllerTest.class,
        TemplatesSecControllerTest.class,
        UsersSecControllerTest.class,
        OauthServiceTest.class

})
public class ControllerSuite {
}
