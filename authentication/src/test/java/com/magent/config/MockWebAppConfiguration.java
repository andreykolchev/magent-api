package com.magent.config;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author artomov.ihor
 * @since 20.04.2016.
 * //
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DummyServiceTestConfig.class})
@WebAppConfiguration
public class MockWebAppConfiguration {
    protected final static String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mvc;

    @Before
    @Sql("data.sql")
    public void setup() {
        this.mvc = webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

}
