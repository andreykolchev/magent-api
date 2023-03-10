package com.magent.config;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created on 26.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DummyServiceTestConfig.class})
@WebAppConfiguration
public class ServiceConfig {
    @Before
    @Sql("classpath:data.sql")
    public void setUp(){}

    @Value("${resource.path}")
    protected String resourcePath;
}
