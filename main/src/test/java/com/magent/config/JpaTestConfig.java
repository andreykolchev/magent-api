package com.magent.config;


import com.magent.servicemodule.config.EntityManagerConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by lezha on 17.02.2015.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"com.magent.service, com.magent.domain","com.magent.servicemodule.service.impl"})
@PropertySources({
        @PropertySource("classpath:magentTest.properties"),
})
public class JpaTestConfig {

    @Value("${db.url.test}")
    private String jdbcUrl;
    @Value("${db.user}")
    private String dbuser;
    @Value("${db.password}")
    private String dbpassword;
    @Value("${db.driver.classname}")
    private String driverClassName;
    @Value("${db.show.sql}")
    private boolean showSql;
    @Value("${db.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;
    @Value("${db.hibernate.connection.pool_size}")
    private int poolSize;
    @Value("${db.hibernate.sql.dialect}")
    private String hiberDialect;
    @Value("${db.hibernate.connection.charset}")
    private String charsetEncoding;
    @Value("${db.hibernate.use.unicode}")
    private boolean useUnicode;
    @Value("${db.hibernate.order.inserts}")
    private boolean orderInserts;
    @Value("${db.hibernate.order.updates}")
    private boolean orderUpdates;

    @Bean
    public DataSource dataSource() {
        Logger.getLogger(this.getClass()).debug("init test DataSource");
        //configuration
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(dbuser);
        hikariConfig.setPassword(dbpassword);
        //datasource
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return EntityManagerConfig.buildEntitymanager(showSql,dataSource(),poolSize,hbm2ddlAuto,hiberDialect,charsetEncoding,useUnicode,2,orderInserts,orderUpdates);
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }
}
