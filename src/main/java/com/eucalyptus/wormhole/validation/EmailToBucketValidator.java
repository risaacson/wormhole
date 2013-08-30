package com.eucalyptus.wormhole.validation;

import com.eucalyptus.wormhole.model.EmailToBucket;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 */
@Component
public class EmailToBucketValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return EmailToBucket.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    EmailToBucket emailtoBucket = (EmailToBucket) target;
    ValidationUtils.rejectIfEmpty(errors, "email", "emailToBucket.email.empty");
    ValidationUtils.rejectIfEmpty(errors, "bucket", "emailToBucket.bucket.empty");
  }

}