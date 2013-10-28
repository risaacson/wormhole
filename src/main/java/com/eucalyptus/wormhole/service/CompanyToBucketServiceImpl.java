package com.eucalyptus.wormhole.service;

import com.eucalyptus.wormhole.exception.CompanyToBucketNotFound;
import com.eucalyptus.wormhole.model.CompanyToBucket;
import com.eucalyptus.wormhole.repository.CompanyToBucketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyToBucketServiceImpl implements CompanyToBucketService {

  @Resource
  private CompanyToBucketRepository companyToBucketRepository;

  @Override
  @Transactional
  public CompanyToBucket create(CompanyToBucket companyToBucket) {
//    CompanyToBucket anCompanyToBucket = companyToBucket;
//    return companyToBucketRepository.save(anCompanyToBucket);
    return companyToBucketRepository.save(companyToBucket);
  }

  @Override
  @Transactional
  public CompanyToBucket delete(int id) throws CompanyToBucketNotFound {
    CompanyToBucket anCompanyToBucket = companyToBucketRepository.findOne(id);

    if(anCompanyToBucket == null) throw new CompanyToBucketNotFound();

    companyToBucketRepository.delete(anCompanyToBucket);
    return anCompanyToBucket;
  }

  @Override
  @Transactional
  public List<CompanyToBucket> findAll() {
    return companyToBucketRepository.findAll();
  }

  @Override
  @Transactional
  public CompanyToBucket update(CompanyToBucket companyToBucket) throws CompanyToBucketNotFound {
    CompanyToBucket anCompanyToBucket = companyToBucketRepository.findOne(companyToBucket.getId());

    if(anCompanyToBucket == null) throw new CompanyToBucketNotFound();

    anCompanyToBucket.setCompany(companyToBucket.getCompany());
    anCompanyToBucket.setBucket(companyToBucket.getBucket());

    return anCompanyToBucket;
  }

  @Override
  @Transactional
  public CompanyToBucket findById(int id) {
    return companyToBucketRepository.findOne(id);
  }

}