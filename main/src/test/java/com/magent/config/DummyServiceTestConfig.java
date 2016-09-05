package com.magent.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by lezha on 19.04.2015.
 */
@Configuration
@ImportResource("classpath:serviceBeans.xml")
@ComponentScan({"com.magent.service", "com.magent.servicemodule", "com.magent.reportmodule.utils.xlsutil", "com.magent.reportmodule.utils.comparators", "com.magent.authmodule.utils.otpgenerator", "com.magent.reportmodule.utils.dateutils", "com.magent.authmodule.utils.validators", "com.magent.servicemodule.utils.validators"})
@Import({JpaTestConfig.class})
@PropertySources({
        @PropertySource("classpath:magentTest.properties"),
        //@PropertySource(value = "file:${catalina.home}/config/magentTest.properties", ignoreResourceNotFound = true)
})
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
