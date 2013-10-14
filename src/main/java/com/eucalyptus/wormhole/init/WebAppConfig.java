package com.eucalyptus.wormhole.init;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.eucalyptus.wormhole")
@PropertySource("classpath:wormhole.properties")
@EnableJpaRepositories("com.eucalyptus.wormhole.repository")
public class WebAppConfig extends WebMvcConfigurationSupport {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

  private static final String PROPERTY_NAME_AWS_REGION = "aws.region";
  private static final String PROPERTY_NAME_AWS_PROXY_TYPE = "aws.proxy.region";
  private static final String PROPERTY_NAME_AWS_PROXY_PROTOCOL = "aws.proxy.protocol";
  private static final String PROPERTY_NAME_AWS_PROXY_HOST = "aws.proxy.host";
  private static final String PROPERTY_NAME_AWS_PROXY_PORT = "aws.proxy.port";

  private static final String PROPERTY_NAME_BLACKHOLE_PREFIX = "blackhole.prefix";

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		
		entityManagerFactoryBean.setJpaProperties(hibProperties());
		
		return entityManagerFactoryBean;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,	env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename(env.getRequiredProperty("message.source.basename"));
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

  @Bean
  @Override
  public RequestMappingHandlerMapping requestMappingHandlerMapping() {
    RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
    handlerMapping.setUseSuffixPatternMatch(false);
//    handlerMapping.setUseTrailingSlashMatch(false);
    return handlerMapping;
  }

  @Bean
  public Properties awsProperties() {
    Properties properties = new Properties();
    properties.put(PROPERTY_NAME_AWS_REGION, env.getRequiredProperty(PROPERTY_NAME_AWS_REGION));
    properties.put(PROPERTY_NAME_AWS_PROXY_TYPE, env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_TYPE));
    properties.put(PROPERTY_NAME_AWS_PROXY_PROTOCOL, env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PROTOCOL));
    properties.put(PROPERTY_NAME_AWS_PROXY_HOST, env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_HOST));
    properties.put(PROPERTY_NAME_AWS_PROXY_PORT, env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PORT));
    return properties;
  }

  @Bean
  public Properties blackholeProperties() {
    Properties properties = new Properties();
    properties.put(PROPERTY_NAME_BLACKHOLE_PREFIX, env.getRequiredProperty(PROPERTY_NAME_BLACKHOLE_PREFIX));
    return properties;
  }

}