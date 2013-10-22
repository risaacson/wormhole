package com.eucalyptus.wormhole.factory;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.eucalyptus.wormhole.model.AwsProperties;
import com.eucalyptus.wormhole.model.BlackholeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 */
public class BlackholeFactory {

  final Logger logger = LoggerFactory.getLogger(BlackholeFactory.class);

  @Autowired
  private BlackholeProperties blackholeProperties;

  public String getPrefix() {
    return blackholeProperties.getPrefix();
  }
}