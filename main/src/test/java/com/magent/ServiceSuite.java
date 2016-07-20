package com.magent;

import com.magent.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by artomov.ihor on 20.05.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GeneralServiceParametrizedTest.class,
        ReasonServiceImplTest.class,
        TemplateAttributeServiceImplTest.class,
        TemplateTaskControlServiceImplTest.class,
        TemplateTaskServiceImplTest.class,
        TrackingServiceImplTest.class,
        UserServiceImplTest.class,
        DataServiceImplTest.class,
        TransactionServiceImplTest.class,
        TmpTypeServiceImplTest.class

})
public class ServiceSuite {
}
