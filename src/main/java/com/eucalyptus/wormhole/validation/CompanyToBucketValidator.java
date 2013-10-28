package com.eucalyptus.wormhole.validation;

import com.eucalyptus.wormhole.model.CompanyToBucket;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 */
@Component
public class CompanyToBucketValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return CompanyToBucket.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CompanyToBucket companyToBucket = (CompanyToBucket) target;
    ValidationUtils.rejectIfEmpty(errors, "company", "companyToBucket.company.empty");
    ValidationUtils.rejectIfEmpty(errors, "bucket", "companyToBucket.bucket.empty");
  }

}