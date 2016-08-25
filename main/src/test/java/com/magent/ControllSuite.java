package com.magent;

import com.magent.controller.*;
import com.magent.securitycheck.AssignmentsSecControllerSuite11Test;
import com.magent.securitycheck.TemplatesSecControllerSuite11Test;
import com.magent.securitycheck.UsersSecControllerSuite11Test;
import com.magent.service.OauthServiceTest;
import com.magent.service.SmsDemoServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by artomov.ihor on 20.05.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AssignmentControllerSuite11ImplTest.class,
        MobileControllerSuite11ImplTest.class,
        OnboardsControllerSuite11ImplTest.class,
        DeviceControllerSuite11ImplTest.class,
        LoginControllerSuite11Test.class,
        TemplateControllerSuite11ImplTest.class,
        ReasonControllerSuite11ImplTest.class,
        UserControllerSuite11Test.class,
        ReportsControllerSuite11ImplTest.class,
        AssignmentsSecControllerSuite11Test.class,
        TemplatesSecControllerSuite11Test.class,
        UsersSecControllerSuite11Test.class,
        TemplateTypeControllerSuite11ImplTest.class,
        OauthServiceTest.class,
        UserServiceForgotPwdFullContextTest.class,
        SmsDemoServiceImplTest.class

})
public class ControllSuite {
}
