package com.magent.servicemodule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by lezha on 19.04.2015.
 * Modified on 20.10.2016
 */
@Configuration
@ImportResource({"classpath:serviceBeans.xml"})
@ComponentScan({"com.magent.servicemodule.service","com.magent.servicemodule.utils","com.magent.authmodule.utils","com.magent.repository", "com.magent.authmodule","com.magent.domain"})
@Import({JpaTestConfig.class})
public class DummyServiceTestConfig {

}
