package com.eucalyptus.wormhole.validation;

import com.eucalyptus.wormhole.model.CompanyToBucket;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 */
public class CompanyToBucketValidator implements Validator{
  @Override
  public boolean supports(Class<?> aClass) {
    return CompanyToBucket.class.isAssignableFrom(aClass);
  }
  @Override
  public void validate(Object target, Errors errors) {
    CompanyToBucket companytoBucket = (CompanyToBucket) target;
    ValidationUtils.rejectIfEmpty(errors, "company", "companyToBucket.company.empty");
    ValidationUtils.rejectIfEmpty(errors, "bucket", "companyToBucket.bucket.empty");
  }
}
