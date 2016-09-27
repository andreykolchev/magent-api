package com.magent.config;

import com.magent.servicemodule.config.RepositoryConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by lezha on 19.04.2015.
 */
@Configuration
@ComponentScan({"com.magent.service.scheduleservice","com.magent.service","com.magent.servicemodule","com.magent.authmodule", "com.magent.utils","com.magent.controller","com.magent.utils","com.magent.servicemodule.service.impl","com.magent.reportmodule", "com.magent.reportmodule.utils.xlsutil", "com.magent.reportmodule.utils.comparators", "com.magent.authmodule.utils.otpgenerator", "com.magent.reportmodule.utils.dateutils", "com.magent.authmodule.utils.validators","com.magent.service.scheduleservice"})
@Import({JpaTestConfig.class,OauthFilterConfig.class})
@ImportResource({"classpath:objectmapper.xml"})
@ContextConfiguration(classes = RepositoryConfig.class)
public class DummyServiceTestConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
