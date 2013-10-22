package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.model.AwsProperties;
import com.eucalyptus.wormhole.model.BlackholeProperties;
import com.eucalyptus.wormhole.util.BlackholeUtil;
import com.eucalyptus.wormhole.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

/**
 */
@Controller
@RequestMapping("/bucket")
public class BucketController {
  final Logger logger = LoggerFactory.getLogger(BucketController.class);

  @Autowired
  AwsProperties awsProperties;

  @Autowired
  BlackholeProperties blackholeProperties;

  @RequestMapping(value="/list", method= RequestMethod.GET)
  public ModelAndView listAllBucketsPage() {
    ModelAndView modelAndView = new ModelAndView("buckets-list");

    List<String> bucketList = S3Util.listBuckets(awsProperties, blackholeProperties.getPrefix());

    modelAndView.addObject("bucketList", bucketList);
    return modelAndView;
  }

  @RequestMapping(value="/list/{bucketName}", method= RequestMethod.GET)
  public ModelAndView listBucketContentsPage(@PathVariable String bucketName) {
    ModelAndView modelAndView = new ModelAndView("bucket-objects-list");

    Map<String, Long> objectList = S3Util.listObjects(awsProperties, bucketName);

    modelAndView.addObject("bucket", bucketName);
    modelAndView.addObject("objectList", objectList);
    return modelAndView;
  }

  @RequestMapping(value="/redirect/{bucketName}/{key}", method= RequestMethod.GET)
  public ModelAndView redirectBucketObjectPage(@PathVariable String bucketName, @PathVariable String key) {
    ModelAndView modelAndView = new ModelAndView("bucket-object-redirect");

    Calendar expiration = Calendar.getInstance();
    expiration.setTime(new Date());
    expiration.add(Calendar.HOUR_OF_DAY, 6);
    URL url = S3Util.getURL(awsProperties, bucketName, key, expiration);

    modelAndView.addObject("objectUrl", url.toString());
    return modelAndView;
  }
}
