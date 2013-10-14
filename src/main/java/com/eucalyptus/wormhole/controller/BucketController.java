package com.eucalyptus.wormhole.controller;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.eucalyptus.wormhole.init.WebAppConfig;
import com.eucalyptus.wormhole.model.AwsProperties;
import com.eucalyptus.wormhole.model.BlackholeProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.*;

/**
 */
@Controller
@RequestMapping("/bucket")
public class BucketController {

  private static final String PROPERTY_NAME_AWS_REGION = "aws.region";
  private static final String PROPERTY_NAME_AWS_PROXY_TYPE = "aws.proxy.type";
  private static final String PROPERTY_NAME_AWS_PROXY_PROTOCOL = "aws.proxy.protocol";
  private static final String PROPERTY_NAME_AWS_PROXY_HOST = "aws.proxy.host";
  private static final String PROPERTY_NAME_AWS_PROXY_PORT = "aws.proxy.port";

  private static final String PROPERTY_NAME_BLACKHOLE_PREFIX = "blackhole.prefix";

  private AmazonS3 s3;
  private String bucketPrefix;

  @PostConstruct
  public void init() {
    ApplicationContext context = new AnnotationConfigApplicationContext();
    Environment environment = context.getEnvironment();
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    String proxyProtocol = environment.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PROTOCOL).toLowerCase();
    s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider(), clientConfiguration);
    String awsProxyType = environment.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_TYPE).toLowerCase();
    Region region;
    switch (awsProxyType) {
      case "none":
        region = Region.getRegion(Regions.fromName(environment.getRequiredProperty(PROPERTY_NAME_AWS_REGION)));
        s3.setRegion(region);
        break;
      case "aws":
        region = Region.getRegion(Regions.fromName(environment.getRequiredProperty(PROPERTY_NAME_AWS_REGION)));
        s3.setRegion(region);
        if (proxyProtocol.equals("http")) {
          clientConfiguration.setProtocol(Protocol.HTTP);
        } else if (proxyProtocol.equals("https")) {
          clientConfiguration.setProtocol(Protocol.HTTPS);
        }
        clientConfiguration.setProxyHost(environment.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_HOST));
        clientConfiguration.setProxyPort(Integer.getInteger(environment.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PORT)));
        break;
      case "riakcs":
        if (proxyProtocol.equals("http")) {
          clientConfiguration.setProtocol(Protocol.HTTP);
        } else if (proxyProtocol.equals("https")) {
          clientConfiguration.setProtocol(Protocol.HTTPS);
        }
        clientConfiguration.setProxyHost(environment.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_HOST));
        clientConfiguration.setProxyPort(Integer.getInteger(environment.getRequiredProperty(PROPERTY_NAME_AWS_PROXY_PORT)));
        break;
    }

    bucketPrefix = environment.getRequiredProperty(PROPERTY_NAME_BLACKHOLE_PREFIX);
  }

  @RequestMapping(value="/list", method= RequestMethod.GET)
  public ModelAndView listAllBucketsPage() {

    ModelAndView modelAndView = new ModelAndView("buckets-list");

    List<String> bucketList = new LinkedList<>();

    for(Bucket bucket : s3.listBuckets()) {
      //TODO Remove this.
      //System.out.println(bucket.getName());
      if(bucket.getName().startsWith(bucketPrefix)) bucketList.add(bucket.getName());
    }

    //TODO remove or pump into logger.
    //System.out.println("bucketList size: " + bucketList.size());

    modelAndView.addObject("bucketList", bucketList);
    return modelAndView;

  }

  @RequestMapping(value="/list/{bucketName}", method= RequestMethod.GET)
  public ModelAndView listBucketContentsPage(@PathVariable String bucketName) {

    ModelAndView modelAndView = new ModelAndView("bucket-objects-list");

    Map<String, Long> objectList = new TreeMap<>();

    ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName));
    for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
      objectList.put(objectSummary.getKey(), objectSummary.getSize());
    }

    //TODO remove or pump into logger.
    System.out.println("objectList size: " + objectList.size());

    modelAndView.addObject("bucket", bucketName);
    modelAndView.addObject("objectList", objectList);
    return modelAndView;

  }

  @RequestMapping(value="/redirect/{bucketName}/{key}", method= RequestMethod.GET)
  public ModelAndView redirectBucketObjectPage(@PathVariable String bucketName, @PathVariable String key) {

    ModelAndView modelAndView = new ModelAndView("bucket-object-redirect");

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.HOUR_OF_DAY, 6);
    Date expirationDate = cal.getTime();
    URL url = s3.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, key).withMethod(HttpMethod.GET).withExpiration(expirationDate));

    modelAndView.addObject("objectUrl", url.toString());
    return modelAndView;

  }

}
