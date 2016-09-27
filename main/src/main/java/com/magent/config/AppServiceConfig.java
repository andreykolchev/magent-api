package com.magent.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by lezha on 17.02.2015.
 * configuration for service layer.
 * see  magentDemo.properties - current properties used for production or demo environment
 * see magent.properties - current properties used for locat testing (for developers)
 */
@Configuration
@ImportResource({"classpath:objectmapper.xml"})
@ComponentScan({"com.magent.servicemodule.service", "com.magent.reportmodule.reportservice","com.magent.authmodule.service","com.magent.service","com.magent.servicemodule.utils"})
@EnableAspectJAutoProxy
@EnableAsync
@PropertySources({
        @PropertySource(value = "classpath:magent.properties",ignoreResourceNotFound = true),
        @PropertySource(value = "file:/opt/tomcat/config/magentDemo.properties",ignoreResourceNotFound = true)
})
public class AppServiceConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

}
