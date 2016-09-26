package com.magent.servicemodule.config;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created on 26.09.2016.
 * Class for encapsulating entityManager
 */
public final class EntityManagerConfig {
    private EntityManagerConfig() {
    }

    public static LocalContainerEntityManagerFactoryBean buildEntitymanager(Boolean showSql, DataSource dataSource, int poolSize, String hbm2ddlAuto, String hiberDialect, String charsetEncoding, Boolean useUnicode, int batchSize, boolean orderInserts, boolean orderUpdates) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setShowSql(showSql);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.magent.repository", "com.magent.domain");
        factory.setDataSource(dataSource);
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
}
