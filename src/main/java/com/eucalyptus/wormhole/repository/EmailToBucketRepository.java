package com.eucalyptus.wormhole.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eucalyptus.wormhole.model.EmailToBucket;

/**
 */
public interface EmailToBucketRepository extends JpaRepository<EmailToBucket, Integer> {}
