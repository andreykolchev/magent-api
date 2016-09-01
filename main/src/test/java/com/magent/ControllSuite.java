package com.magent;

import com.magent.controller.*;
import com.magent.securitycheck.AssignmentsSecControllerSuite11Test;
import com.magent.securitycheck.TemplatesSecControllerSuite11Test;
import com.magent.securitycheck.UsersSecControllerSuite11Test;
import com.magent.service.SmsDemoServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by artomov.ihor on 20.05.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AssignmentControllerImplTest.class,
        MobileControllerImplTest.class,
        OnboardsControllerImplTest.class,
        DeviceControllerImplTest.class,
        LoginControllerTest.class,
        TemplateControllerImplTest.class,
        ReasonControllerImplTest.class,
        UserControllerTest.class,
        ReportsControllerImplTest.class,
        AssignmentsSecControllerSuite11Test.class,
        TemplatesSecControllerSuite11Test.class,
        UsersSecControllerSuite11Test.class,
        TemplateTypeControllerImplTest.class,
        UserServiceForgotPwdFullContextTest.class,
        SmsDemoServiceImplTest.class

})
public class ControllSuite {
}
