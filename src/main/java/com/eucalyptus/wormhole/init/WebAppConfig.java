package com.eucalyptus.wormhole.init;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.eucalyptus.wormhole.model.AwsProperties;
import com.eucalyptus.wormhole.model.BlackholeProperties;
import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
//@Import({ WebAppMappingConfig.class })
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.eucalyptus.wormhole")
@PropertySource("classpath:wormhole.properties")
@EnableJpaRepositories("com.eucalyptus.wormhole.repository")
public class WebAppConfig extends WebMvcConfigurerAdapter {

  final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);

  private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
  private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
  private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
  private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

  private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
  private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
  private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

  private static final String PROPERTY_NAME_AWS_REGION = "aws.region";
  private static final String PROPERTY_NAME_AWS_PROXY_TYPE = "aws.proxy.type";
  private static final String PROPERTY_NAME_AWS_PROXY_PROTOCOL = "aws.proxy.protocol";
  private static final String PROPERTY_NAME_AWS_PROXY_HOST = "aws.proxy.host";
  private static final String PROPERTY_NAME_AWS_PROXY_PORT = "aws.proxy.port";

  private static final String PROPERTY_NAME_BLACKHOLE_PREFIX = "blackhole.prefix";

  @Resource
  private Environment env;

  @Bean
  public DataSource dataSource() {
    logger.debug("ENTER: Bean");
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
    dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
    dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
    dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    logger.debug("ENTER: Bean");
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
    entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
    entityManagerFactoryBean.setJpaProperties(hibProperties());
    return entityManagerFactoryBean;
  }

  private Properties hibProperties() {
    logger.debug("ENTER: Method");
    Properties properties = new Properties();
    properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,	env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
    properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
    return properties;
  }

  @Bean
  public JpaTransactionManager transactionManager() {
    logger.debug("ENTER: Bean");
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    logger.debug("ENTER: Method");
    registry.addResourceHandler("/resources/images/**").addResourceLocations("/resources/images/").setCachePeriod(31556926);
//    registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
//    registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
//    registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
//    registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
    defaultServletHandlerConfigurer.enable();
  }

  @Bean
  public UrlBasedViewResolver setupViewResolver() {
    logger.debug("ENTER: Bean");
    UrlBasedViewResolver resolver = new UrlBasedViewResolver();
    resolver.setPrefix("/WEB-INF/pages/");
    resolver.setSuffix(".jsp");
    resolver.setViewClass(JstlView.class);
    return resolver;
  }
	
  @Bean
  public ResourceBundleMessageSource messageSource() {
    logger.debug("ENTER: Bean");
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasename(env.getRequiredProperty("message.source.basename"));
    source.setUseCodeAsDefaultMessage(true);
    return source;
  }

  @Bean
  public AwsProperties awsProperties() {
    logger.debug("ENTER: Bean");
    AwsProperties properties = new AwsProperties();
    logger.debug( PROPERTY_NAME_AWS_REGION  + " " + env.getRequiredProperty(PROPERTY_NAME_AWS_REGION) );
    properties.setRegion(env.getRequiredProperty(PROPERTY_NAME_AWS_REGION));
    logger.debug( PROPERTY_NAME_AWS_PROXY_TYPE  + " " + env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_TYPE) );
    properties.setProxyType(env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_TYPE));
    logger.debug( PROPERTY_NAME_AWS_PROXY_PROTOCOL  + " " + env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PROTOCOL) );
    properties.setProxyProtocol(env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PROTOCOL));
    logger.debug( PROPERTY_NAME_AWS_PROXY_HOST  + " " + env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_HOST) );
    properties.setProxyHost(env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_HOST));
    logger.debug( PROPERTY_NAME_AWS_PROXY_PORT  + " " + env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PORT) );
    properties.setProxyPort(Integer.parseInt(env.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PORT)));
    return properties;
  }

  @Bean
  public BlackholeProperties blackholeProperties() {
    logger.debug("ENTER: Bean");
    BlackholeProperties properties = new BlackholeProperties();
    logger.debug( PROPERTY_NAME_BLACKHOLE_PREFIX  + " " + env.getRequiredProperty(PROPERTY_NAME_BLACKHOLE_PREFIX) );
    properties.setPrefix(env.getRequiredProperty(PROPERTY_NAME_BLACKHOLE_PREFIX));
    return properties;
  }

}