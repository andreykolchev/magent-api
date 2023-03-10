package com.magent.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by artomov.ihor on 16.05.2016.
 */
@Configuration
@Import(value = {DummyServiceTestConfig.class})
@ComponentScan(basePackages = {"com.magent.controller"})
public class MvcConfig {
}
