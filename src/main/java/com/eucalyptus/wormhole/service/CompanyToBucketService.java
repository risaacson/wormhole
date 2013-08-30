package com.eucalyptus.wormhole.service;

import java.util.List;

import com.eucalyptus.wormhole.exception.CompanyToBucketNotFound;
import com.eucalyptus.wormhole.model.CompanyToBucket;

public interface CompanyToBucketService {

  public CompanyToBucket create(CompanyToBucket companyToBucket);
  public CompanyToBucket delete(int id) throws CompanyToBucketNotFound;
  public List<CompanyToBucket> findAll();
  public CompanyToBucket update(CompanyToBucket companyToBucket) throws CompanyToBucketNotFound;
  public CompanyToBucket findById(int id);

}
