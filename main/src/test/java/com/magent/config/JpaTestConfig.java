package com.magent.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by lezha on 17.02.2015.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"com.magent.service, com.magent.domain", "com.magent.servicemodule.service.impl"})
@PropertySources({
        @PropertySource("classpath:magentTest.properties"),
})
public class JpaTestConfig {

    @Value("${db.url}")
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

    @Value("classpath:/db_patch/db_path_01_09_2016.sql")
    private Resource initScript;
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
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScript(initScript);
        return databasePopulator;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
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
//        props.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);

//        factory.getJpaPropertyMap().put("hibernate.hbm2ddl.import_files", "/data.sql");

        props.put("hibernate.dialect", hiberDialect);
        props.put("hibernate.connection.charSet", charsetEncoding);
        props.put("connection.characterEncoding", charsetEncoding);
        props.put("hibernate.connection.Useunicode", useUnicode);
        props.put("hibernate.jdbc.batch_size", 100);
        props.put("hibernate.order_inserts", orderInserts);
        props.put("hibernate.order_updates", orderUpdates);
        factory.setJpaProperties(props);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
}
