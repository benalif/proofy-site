package dz.wta.web.ooredoo.frontend.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This is landingpage persitance config class, this configuration is set 
 * to communicate with landing page database where the international_axa_voucher table is defined
 * 
 * @author benalif
 * @Date 06/03/2019
 */
 
@Configuration
@EnableJpaRepositories(basePackages = "dz.wta.web.ooredoo.frontend.repository", entityManagerFactoryRef = "entityManagerFactoryForLandingPage", transactionManagerRef = "transactionManagerForLandingPage")
@EnableJpaAuditing
public class PersistenceConfig {
 
	@Autowired
	Environment env;
    
	// data source config which is defined in the config-properties file
	@Bean
	public DataSource landingPageDateSource() throws NamingException {
 
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("landingpage.database.driverclass"));
		dataSource.setUrl(env.getProperty("landingpage.database.url"));
		dataSource.setUsername(env.getProperty("landingpage.database.username"));
		dataSource.setPassword(env.getProperty("landingpage.database.password"));
 
		return dataSource;
	}
    // scanning dz.ooredoo.web.vasco.landingpage.entity package
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryForLandingPage() throws NamingException {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(landingPageDateSource());
		entityManagerFactory.setPackagesToScan(new String[] { "dz.wta.web.ooredoo.frontend.entity" });
 
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactory.setJpaProperties(additionalProperties());
 
		return entityManagerFactory;
	}
 
	@Bean
	public PlatformTransactionManager transactionManagerForLandingPage(EntityManagerFactory entityManagerFactory) throws NamingException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryForLandingPage().getObject());
 
		return transactionManager;
	}
 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslationForLandingPage() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
 
	// db dialect config which is defined in the config-properties file
	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("hibernate.oracle.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return properties;
	}
 
}
