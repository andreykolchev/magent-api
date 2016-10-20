package com.magent.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by lezha on 19.04.2015.
 */
@Configuration
@ComponentScan({"com.magent.service", "com.magent.reportmodule.utils.xlsutil", "com.magent.reportmodule.utils.comparators", "com.magent.reportmodule.utils.dateutils", "com.magent.reportmodule.reportservice"})
@Import({JpaTestConfig.class})
public class DummyServiceTestConfig {

}
