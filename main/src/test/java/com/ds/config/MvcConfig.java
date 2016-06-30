package com.ds.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by artomov.ihor on 16.05.2016.
 */
@Configuration
@Import(value = {DistanceSalesDummyServiceTestConfig.class})
@ComponentScan({"com.ds"})
public class MvcConfig {
}
