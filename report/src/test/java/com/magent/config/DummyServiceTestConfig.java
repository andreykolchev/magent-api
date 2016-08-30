package com.magent.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by lezha on 19.04.2015.
 */
@Configuration
@ComponentScan({"com.magent.service", "com.magent.reportmodule.utils.xlsutil", "com.magent.reportmodule.utils.comparators", "com.magent.reportmodule.utils.dateutils", "com.magent.reportmodule.reportservice"})
@Import({JpaTestConfig.class})
@PropertySources({
        @PropertySource("classpath:reportModuleTestConfig.properties"),
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
