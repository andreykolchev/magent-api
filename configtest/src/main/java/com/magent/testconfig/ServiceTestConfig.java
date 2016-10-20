package com.magent.testconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created on 20.10.2016.
 */
@Configuration
public class ServiceTestConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
