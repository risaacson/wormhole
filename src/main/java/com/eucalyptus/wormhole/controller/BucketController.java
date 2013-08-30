package com.eucalyptus.wormhole.controller;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@Controller
@RequestMapping("/bucket")
public class BucketController {

  AmazonS3 s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());

  @RequestMapping(value="/list", method= RequestMethod.GET)
  public ModelAndView listAllBucketsPage() {

    ModelAndView modelAndView = new ModelAndView("buckets-list");

    s3.listBuckets();

    return modelAndView;

  }

  @RequestMapping(value="/list/{bucket}", method= RequestMethod.GET)
  public ModelAndView listBucketContentsPage(@PathVariable String bucketName) {

    ModelAndView modelAndView = new ModelAndView("bucket-contents-list");

    ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
        .withBucketName(bucketName));
    objectListing.getObjectSummaries();

    return modelAndView;

  }

  @RequestMapping(value="/redirect/{bucket}/{file}", method= RequestMethod.GET)
  public ModelAndView redirectBucketObjectPage(@PathVariable String bucketName, @PathVariable String fileName) {

    ModelAndView modelAndView = new ModelAndView("bucket-object-redirect");

    S3Object s3Object = s3.getObject("foo", "bar");
    s3Object.getRedirectLocation();

    return modelAndView;

  }

}
