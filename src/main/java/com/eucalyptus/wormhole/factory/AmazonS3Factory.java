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
public class AmazonS3Factory {

  final static Logger logger = LoggerFactory.getLogger(AmazonS3Factory.class);

  public static AmazonS3 generateAmazonS3(final AwsProperties awsProperties) {
    AmazonS3 s3 = null;
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    String proxyProtocol = awsProperties.getProxyProtocol().toLowerCase();
    String proxyType = awsProperties.getProxyType().toLowerCase();
    Region region;
    switch(proxyType) {
      case "none":
        region = Region.getRegion(Regions.fromName(awsProperties.getRegion()));
        s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider(), clientConfiguration);
        s3.setRegion(region);
        break;
      case "aws":
        region = Region.getRegion(Regions.fromName(awsProperties.getRegion()));
        if(proxyProtocol.equals("http")) clientConfiguration.setProtocol(Protocol.HTTP);
        else if(proxyProtocol.equals("https")) clientConfiguration.setProtocol(Protocol.HTTP);
        clientConfiguration.setProxyHost(awsProperties.getProxyHost());
        clientConfiguration.setProxyPort(awsProperties.getProxyPort());
        s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider(), clientConfiguration);
        s3.setRegion(region);
        break;
      case "riakcs":
        if(proxyProtocol.equals("http")) clientConfiguration.setProtocol(Protocol.HTTP);
        else if(proxyProtocol.equals("https")) clientConfiguration.setProtocol(Protocol.HTTP);
        clientConfiguration.setProxyHost(awsProperties.getProxyHost());
        clientConfiguration.setProxyPort(awsProperties.getProxyPort());
        s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider(), clientConfiguration);
        break;
    }
    return s3;
  }
}