package com.magent;

import com.magent.controller.*;
import com.magent.securitycheck.AssignmentsSecControllerTest;
import com.magent.securitycheck.TemplatesSecControllerTest;
import com.magent.securitycheck.UsersSecControllerTest;
import com.magent.service.OauthServiceTest;
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
        TemplateTypeControllerImplTest.class,
        OauthServiceTest.class

})
public class ControllerSuite {
}
