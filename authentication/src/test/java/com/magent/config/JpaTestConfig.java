package com.magent.config;


import com.magent.testconfig.testconfig.GeneralJpaTestConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by lezha on 17.02.2015.
 */
@Configuration
@Import(GeneralJpaTestConfig.class)
@ComponentScan({"com.magent.service, com.magent.domain","com.magent.authmodule.service","com.magent.authmodule.utils"})
@EnableJpaRepositories(value = "com.magent.repository", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class JpaTestConfig {

}
