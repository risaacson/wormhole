package com.eucalyptus.wormhole.controller;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
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

  private AmazonS3 s3;
  private String bucketPrefix;

  @PostConstruct
  public void init() {
    s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
    //TODO pull out the region and put into a config file.
    Region usWest2 = Region.getRegion(Regions.US_WEST_2);
    s3.setRegion(usWest2);

    //TODO pull out the prefix and put into a config file.
    bucketPrefix = "blackhole-dev";
  }

  @RequestMapping(value="/list", method= RequestMethod.GET)
  public ModelAndView listAllBucketsPage() {

    ModelAndView modelAndView = new ModelAndView("buckets-list");

    List<String> bucketList = new LinkedList<>();

    for(Bucket bucket : s3.listBuckets()) {
      //TODO Remove this.
      System.out.println(bucket.getName());
      if(bucket.getName().startsWith(bucketPrefix)) bucketList.add(bucket.getName());
    }

    //TODO remove over pump into logger.
    System.out.println("bucketList size: " + bucketList.size());

    modelAndView.addObject("bucketList", bucketList);
    return modelAndView;

  }

  @RequestMapping(value="/list/{bucket}", method= RequestMethod.GET)
  public ModelAndView listBucketContentsPage(@PathVariable String bucketName) {

    ModelAndView modelAndView = new ModelAndView("bucket-objects-list");

    Map<String, Long> objectList = new TreeMap<>();

    ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName));
    for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
      objectList.put(objectSummary.getKey(), objectSummary.getSize());
    }

    //TODO remove over pump into logger.
    System.out.println("objectList size: " + objectList.size());

    modelAndView.addObject("bucket", bucketName);
    modelAndView.addObject("objectList", objectList);
    return modelAndView;

  }

  @RequestMapping(value="/redirect/{bucket}/{key}", method= RequestMethod.GET)
  public ModelAndView redirectBucketObjectPage(@PathVariable String bucketName, @PathVariable String key) {

    ModelAndView modelAndView = new ModelAndView("bucket-object-redirect");

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.HOUR_OF_DAY, 1);
    Date expirationDate = cal.getTime();
    URL url = s3.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, key).withMethod(HttpMethod.GET).withExpiration(expirationDate));

    modelAndView.addObject("objectUrl", url.toString());
    return modelAndView;

  }

}
