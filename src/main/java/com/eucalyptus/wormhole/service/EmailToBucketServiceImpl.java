package com.eucalyptus.wormhole.service;

import com.eucalyptus.wormhole.exception.EmailToBucketNotFound;
import com.eucalyptus.wormhole.model.EmailToBucket;
import com.eucalyptus.wormhole.repository.EmailToBucketRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmailToBucketServiceImpl implements EmailToBucketService {

  @Resource
  private EmailToBucketRepository emailToBucketRepository;

  @Override
  @Transactional
  public EmailToBucket create(EmailToBucket emailToBucket) {
    EmailToBucket anEmailToBucket = emailToBucket;
    return emailToBucketRepository.save(anEmailToBucket);
  }

  @Override
  @Transactional
  public EmailToBucket delete(int id) throws EmailToBucketNotFound {
    EmailToBucket anEmailToBucket = emailToBucketRepository.findOne(id);

    if(anEmailToBucket == null) throw new EmailToBucketNotFound();

    emailToBucketRepository.delete(anEmailToBucket);
    return anEmailToBucket;
  }

  @Override
  @Transactional
  public List<EmailToBucket> findAll() {
    return emailToBucketRepository.findAll();
  }

  @Override
  @Transactional
  public EmailToBucket update(EmailToBucket emailToBucket) throws EmailToBucketNotFound {
    EmailToBucket anEmailToBucket = emailToBucketRepository.findOne(emailToBucket.getId());

    if(anEmailToBucket == null) throw new EmailToBucketNotFound();

    anEmailToBucket.setEmail(emailToBucket.getEmail());
    anEmailToBucket.setBucket(emailToBucket.getBucket());

    return anEmailToBucket;
  }

  @Override
  @Transactional
  public EmailToBucket findById(int id) {
    return emailToBucketRepository.findOne(id);
  }

}
