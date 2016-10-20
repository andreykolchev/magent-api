package com.magent.config;


import com.magent.testconfig.GeneralJpaTestConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by lezha on 17.02.2015.
 */
@Configuration
@Import(GeneralJpaTestConfig.class)
@ComponentScan({"com.magent.service, com.magent.domain", "com.magent.servicemodule.service.impl"})
public class JpaTestConfig {

}
