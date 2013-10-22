package com.eucalyptus.wormhole.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.eucalyptus.wormhole.factory.AmazonS3Factory;
import com.eucalyptus.wormhole.model.AwsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

/**
 */
public class S3Util {

  final Logger logger = LoggerFactory.getLogger(S3Util.class);

  public static List<String> listBuckets(final AwsProperties awsProperties, final String bucketPrefix) {
    AmazonS3 s3 = AmazonS3Factory.generateAmazonS3(awsProperties);

    List<String> bucketList = new ArrayList<>();

    for(Bucket bucket : s3.listBuckets()) {
      if(bucket.getName().startsWith(bucketPrefix)) bucketList.add(bucket.getName());
    }
      return bucketList;
  }

  public static Map<String, Long> listObjects(final AwsProperties awsProperties, final String bucketName) {
    AmazonS3 s3 = AmazonS3Factory.generateAmazonS3(awsProperties);

    Map<String, Long> objectList =  new TreeMap<>();
    ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName));
    for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
      objectList.put(objectSummary.getKey(), objectSummary.getSize());
    }
    return objectList;
  }

  public static URL getURL(final AwsProperties awsProperties, final String bucketName, final String key, Calendar expiration) {
    AmazonS3 s3 = AmazonS3Factory.generateAmazonS3(awsProperties);

    if(expiration == null) {
      expiration = Calendar.getInstance();
      expiration.setTime(new Date());
      expiration.add(Calendar.HOUR_OF_DAY, 1);
    }
    Date expirationDate = expiration.getTime();
    return s3.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, key).withMethod(HttpMethod.GET).withExpiration(expirationDate));
  }

}
