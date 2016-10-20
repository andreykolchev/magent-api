package com.magent.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created on 19.04.2015.
 * Modify on 20.10.2016
 */
@Configuration
@ComponentScan({"com.magent.service","com.magent.repository", "com.magent.authmodule.utils", "com.magent.authmodule","com.magent.authmodule.config"})
@Import({JpaTestConfig.class})
public class DummyServiceTestConfig {

}
