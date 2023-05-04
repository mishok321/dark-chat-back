package com.misha.darkchatback.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class HibernateConfig {
    public static final String ENTITY_MANAGER_FACTORY_BEAN_NAME = "entityManagerFactory";
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.jpa.database-platform}")
    private String dialect;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.jpa.generate-ddl}")
    private Boolean generateDdl;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = ENTITY_MANAGER_FACTORY_BEAN_NAME)
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", generateDdl);
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.hdm2ddl.auto", ddlAuto);

        factoryBean.setHibernateProperties(properties);
        factoryBean.setPackagesToScan("com.misha.darkchatback");
        return factoryBean;
    }
}
