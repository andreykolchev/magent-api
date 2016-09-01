package com.magent.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by lezha on 19.04.2015.
 */
@Configuration
@ComponentScan({"com.magent.service","com.magent.repository", "com.magent.authmodule.utils", "com.magent.authmodule","com.magent.authmodule.config"})
@Import({JpaTestConfig.class})
@PropertySources({
        @PropertySource("classpath:authmoduleConfig.properties"),
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
