package config;

import com.zaxxer.hikari.HikariDataSource;
import exceptions.ApplicationConfigException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"service", "validator", "dao", "reader", "writer", "converter", "printer"})
@EnableTransactionManagement
@PropertySource("application.properties")
public class ApplicationConfig {

    @Value("${database.username}")
    private String dbUsername;

    @Value("${database.password}")
    private String dbPassword;

    @Value("${database.url}")
    private String dbUrl;

    @Value("${hibernate.connection.driver_class}")
    private String dbDriver;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

    @Value("${hibernate.format_sql}")
    private String hibernateFormat;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.format_sql", hibernateFormat);
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        return properties;
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) throws ApplicationConfigException {
        try {
            LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
            factory.setDataSource(dataSource);
            factory.setPackagesToScan("entity");
            factory.setHibernateProperties(hibernateProperties());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IOException e) {
            throw new ApplicationConfigException(e);
        }
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
