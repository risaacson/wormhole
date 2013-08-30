package com.eucalyptus.wormhole.service;

import java.util.List;

import com.eucalyptus.wormhole.exception.EmailToBucketNotFound;
import com.eucalyptus.wormhole.model.EmailToBucket;

public interface EmailToBucketService {

  public EmailToBucket create(EmailToBucket emailToBucket);
  public EmailToBucket delete(int id) throws EmailToBucketNotFound;
  public List<EmailToBucket> findAll();
  public EmailToBucket update(EmailToBucket emailToBucket) throws EmailToBucketNotFound;
  public EmailToBucket findById(int id);

}
