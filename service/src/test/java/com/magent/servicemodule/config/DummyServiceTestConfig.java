package com.magent.servicemodule.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by lezha on 19.04.2015.
 */
@Configuration
@ImportResource({"classpath:serviceBeans.xml"})
@ComponentScan({"com.magent.authmodule.utils","com.magent.repository", "com.magent.authmodule","com.magent.servicemodule","com.magent.domain.enums"})
@Import({JpaTestConfig.class})
@PropertySources({
        @PropertySource("classpath:servicetestconf.properties")
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
