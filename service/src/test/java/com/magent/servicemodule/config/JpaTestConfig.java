package com.magent.servicemodule.config;


import com.magent.testconfig.GeneralJpaTestConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created on 17.02.2015.
 * Modified on 20.10.2016
 */
@Configuration
@Import(GeneralJpaTestConfig.class)
@ComponentScan({"com.magent.repository", "com.magent.domain", "com.magent.servicemodule"})
@EnableJpaRepositories(value = "com.magent.repository", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class JpaTestConfig {

}
