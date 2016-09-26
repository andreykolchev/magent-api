package com.magent.config;

import com.magent.servicemodule.config.EntityManagerConfig;
import com.magent.servicemodule.config.RepositoryConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created on 17.02.2015.
 * @version 1.00
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"com.magent.domain","com.magent.service"})
@Import({RepositoryConfig.class})
@PropertySources({
        @PropertySource(value = "classpath:magent.properties",ignoreResourceNotFound = true),
        @PropertySource(value = "file:/opt/tomcat/config/magentDemo.properties",ignoreResourceNotFound = true)
})
public class JpaConfig {

    @Value("${db.url}")
    private String jdbcUrl;
    @Value("${db.user}")
    private String dbuser;
    @Value("${db.password}")
    private String dbpassword;
    @Value("${db.driver.classname}")
    private String driverClassName;
    @Value("${db.hibernate.connection.pool_size}")
    private int poolSize;
    @Value("${db.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;
    @Value("${db.hibernate.connection.charset}")
    private String charsetEncoding;
    @Value("${db.hibernate.batch.size}")
    private int batchSize;
    @Value("${db.hibernate.sql.dialect}")
    private String hiberDialect;
    @Value("${db.show.sql}")
    private boolean showSql;
    @Value("${db.hibernate.use.unicode}")
    private boolean useUnicode;
    @Value("${db.hibernate.order.inserts}")
    private boolean orderInserts;
    @Value("${db.hibernate.order.updates}")
    private boolean orderUpdates;
    @Value("${dm.conn.max.life}")
    private long connTimeOut;
    @Bean
    @Profile("production")
    public DataSource dataSource() {
        Logger.getLogger(this.getClass()).debug("init production DataSource ");

        //configuration
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(dbuser);
        hikariConfig.setPassword(dbpassword);
        hikariConfig.setConnectionTimeout(connTimeOut);

        //datasource
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        return EntityManagerConfig.buildEntitymanager(showSql,dataSource(),poolSize,hbm2ddlAuto,hiberDialect,charsetEncoding,useUnicode,batchSize,orderInserts,orderUpdates);
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
