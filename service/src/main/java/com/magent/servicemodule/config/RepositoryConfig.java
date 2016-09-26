package com.magent.servicemodule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created on 26.09.2016.
 */
@Configuration
@ComponentScan({"com.magent.repository", "com.magent.servicemodule.service, com.magent.domain"})
@ImportResource("classpath:serviceBeans.xml")
@EnableJpaRepositories(value = "com.magent.repository", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class RepositoryConfig {

}
