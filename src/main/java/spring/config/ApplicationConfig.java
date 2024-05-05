package spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

import static spring.config.AppProperties.DRIVER_CLASS_NAME;
import static spring.config.AppProperties.JPA_DATABASE_PLATFORM;
import static spring.config.AppProperties.JPA_SHOW_SQL;
import static spring.config.AppProperties.PACKAGES_TO_SCAN;
import static spring.config.AppProperties.PASSWORD;
import static spring.config.AppProperties.URL;
import static spring.config.AppProperties.USERNAME;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("spring")
@EnableJpaRepositories("spring.repository")
@EnableTransactionManagement
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {

    private final Environment env;

    @Autowired
    public ApplicationConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(DRIVER_CLASS_NAME.getName()));
        dataSource.setUrl(env.getRequiredProperty(URL.getName()));
        dataSource.setUsername(env.getRequiredProperty(USERNAME.getName()));
        dataSource.setPassword(env.getRequiredProperty(PASSWORD.getName()));

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(JPA_DATABASE_PLATFORM.getName(), env.getRequiredProperty(JPA_DATABASE_PLATFORM.getName()));
        properties.put(JPA_SHOW_SQL.getName(), env.getRequiredProperty(JPA_SHOW_SQL.getName()));

        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(PACKAGES_TO_SCAN.getName());

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
}