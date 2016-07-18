package com.magent.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by lezha on 17.02.2015.
 */
@Configuration
@EnableTransactionManagement
@ImportResource({"classpath:dbinicialize.xml"})
@ComponentScan({"com.magent.repository", "com.magent.service, com.magent.domain"})
@EnableJpaRepositories(value = "com.magent.repository", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@PropertySources({
        @PropertySource("classpath:magent.properties"),
        //@PropertySource(value = "file:/opt/esb/config/magent.properties", ignoreResourceNotFound = true)
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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setShowSql(showSql);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.magent.domain");
        factory.setDataSource(dataSource());

        Properties props = new Properties();
        props.put("connection.pool_size", poolSize);
        props.put("hibernate.show_sql", showSql);
        props.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        props.put("hibernate.dialect", hiberDialect);
        props.put("hibernate.connection.charSet", charsetEncoding);
        props.put("connection.characterEncoding", charsetEncoding);
        props.put("hibernate.connection.Useunicode", useUnicode);
        props.put("hibernate.jdbc.batch_size", batchSize);
        props.put("hibernate.order_inserts", orderInserts);
        props.put("hibernate.order_updates", orderUpdates);
        factory.setJpaProperties(props);
        factory.afterPropertiesSet();
        return factory;
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
