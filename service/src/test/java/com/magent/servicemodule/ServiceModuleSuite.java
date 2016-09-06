package com.magent.servicemodule;

import com.magent.servicemodule.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by artomov.ihor on 20.05.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataServiceImplTest.class,
        GeneralServiceParametrizedTest.class,
        ReasonServiceImplTest.class,
        SmsDemoServiceImplTest.class,
        SmsServiceImplTest.class,
        TemplateAttributeServiceImplTest.class,
        TemplateTaskControlServiceImplTest.class,
        TemplateTaskServiceImplTest.class,
        TmpTypeServiceImplTest.class,
        TrackingServiceImplTest.class,
        UserServiceImplTest.class,
        AssignmentServiceImplTest.class,
        AssignmentTaskControlServiceImplTest.class,
        AssignmentTaskServiceImplTest.class

})
public class ServiceModuleSuite {
}
