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

@Configuration
@EnableJpaRepositories(basePackages = "dz.wta.web.ooredoo.frontend.repository", entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager")
@EnableJpaAuditing
public class PersistenceConfig {

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty(""));
		dataSource.setUrl(env.getProperty(""));
		dataSource.setUsername(env.getProperty(""));
		dataSource.setPassword(env.getProperty(""));
		return dataSource;
	}
	
	// scanning dz.ooredoo.web.vasco.landingpage.entity package
		@Bean
		public LocalContainerEntityManagerFactoryBean entityManager() throws NamingException {
			LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
			entityManagerFactory.setDataSource(dataSource());
			entityManagerFactory.setPackagesToScan(new String[] { "dz.wta.web.ooredoo.frontend.entity" });

			JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
			entityManagerFactory.setJpaProperties(additionalProperties());

			return entityManagerFactory;
		}

		@Bean
		public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws NamingException {
			JpaTransactionManager transactionManager = new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(entityManager().getObject());

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
