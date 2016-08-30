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
        AssignmentControllerSuiteImplTest.class,
        MobileControllerSuiteImplTest.class,
        OnboardsControllerSuiteImplTest.class,
        DeviceControllerSuiteImplTest.class,
        LoginControllerSuiteTest.class,
        TemplateControllerSuiteImplTest.class,
        ReasonControllerSuiteImplTest.class,
        UserControllerSuiteTest.class,
        ReportsControllerSuiteImplTest.class,
        AssignmentsSecControllerSuite11Test.class,
        TemplatesSecControllerSuite11Test.class,
        UsersSecControllerSuite11Test.class,
        TemplateTypeControllerSuiteImplTest.class,
        OauthServiceTest.class,
        UserServiceForgotPwdFullContextTest.class,
        SmsDemoServiceImplTest.class

})
public class ControllSuite {
}
