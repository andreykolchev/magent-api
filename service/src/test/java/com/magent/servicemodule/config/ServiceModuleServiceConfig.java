package com.magent.servicemodule.config;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by artomov.ihor on 26.04.2016.
 * Modified on 20.10.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DummyServiceTestConfig.class})
public class ServiceModuleServiceConfig {
    @Before
    public void setUp(){}

    protected final static String testLogin = "user1";//ADMIN
}
