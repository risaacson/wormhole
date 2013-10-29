/*
 * Copyright 2009-2013 Eucalyptus Systems, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Please contact Eucalyptus Systems, Inc., 6755 Hollister Ave., Goleta
 * CA 93117, USA or visit http://www.eucalyptus.com/licenses/ if you need
 * additional information or have any questions.
 *
 * This file may incorporate work covered under the following copyright
 * and permission notice:
 *
 *   Software License Agreement (BSD License)
 *
 *   Copyright (c) 2008, Regents of the University of California
 *   All rights reserved.
 *
 *   Redistribution and use of this software in source and binary forms,
 *   with or without modification, are permitted provided that the
 *   following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer
 *     in the documentation and/or other materials provided with the
 *     distribution.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *   FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *   COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *   INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *   BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *   CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *   LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *   ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE. USERS OF THIS SOFTWARE ACKNOWLEDGE
 *   THE POSSIBLE PRESENCE OF OTHER OPEN SOURCE LICENSED MATERIAL,
 *   COPYRIGHTED MATERIAL OR PATENTED MATERIAL IN THIS SOFTWARE,
 *   AND IF ANY SUCH MATERIAL IS DISCOVERED THE PARTY DISCOVERING
 *   IT MAY INFORM DR. RICH WOLSKI AT THE UNIVERSITY OF CALIFORNIA,
 *   SANTA BARBARA WHO WILL THEN ASCERTAIN THE MOST APPROPRIATE REMEDY,
 *   WHICH IN THE REGENTS' DISCRETION MAY INCLUDE, WITHOUT LIMITATION,
 *   REPLACEMENT OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO
 *   IDENTIFIED, OR WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT
 *   NEEDED TO COMPLY WITH ANY SUCH LICENSES OR RIGHTS.
 */

package com.eucalyptus.wormhole.init;

import com.eucalyptus.wormhole.model.AwsProperties;
import com.eucalyptus.wormhole.model.BlackholeProperties;
import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 *
 *
 * @author richard@eucalyptus.com (Richard Isaacson)
 */
@Configuration
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
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
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