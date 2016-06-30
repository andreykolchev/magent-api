package com.ds.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by lezha on 19.04.2015.
 */
@Configuration
@ImportResource("classpath:serviceBeans.xml")
@ComponentScan({"com.ds.service","com.ds.utils.ariphmeticbeans","com.ds.utils.xlsutil","com.ds.utils.comparators","com.ds.utils.otpgenerator","com.ds.utils.dateutils", "com.ds.utils.validators"})
@Import({DistanceSalesJpaTestConfig.class})
@PropertySources({
        @PropertySource("classpath:distancesalesTest.properties"),
        //@PropertySource(value = "file:${catalina.home}/config/distancesalesTest.properties", ignoreResourceNotFound = true)
})
public class DistanceSalesDummyServiceTestConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
