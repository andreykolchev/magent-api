package com.ds.config;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author  artomov.ihor
 * @since 20.04.2016.
 * //
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DistanceSalesDummyServiceTestConfig.class})
@WebAppConfiguration
public class MockWebAppConfiguration {

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mvc;

    @Before
    @Sql("data.sql")
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

}
