package com.ds.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by lezha on 17.02.2015.
 */
@Configuration
@ImportResource({"classpath:serviceBeans.xml","classpath:objectmapper.xml"})
@ComponentScan({"com.ds.service"})
@EnableAspectJAutoProxy
//@Import({DsIntegrationConfig.class})
@EnableAsync
@PropertySources({
        @PropertySource(value = "classpath:distancesales.properties",ignoreResourceNotFound = true),
        //@PropertySource(value = "file:${catalina.home}/config/distancesales.properties", ignoreResourceNotFound = true)
})
public class DistanceSalesServiceConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

}
