package com.eucalyptus.wormhole.validation;

import com.eucalyptus.wormhole.model.UploadLog;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 */
@Component
public class UploadLogValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return UploadLog.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    UploadLog companytoBucket = (UploadLog) target;
      ValidationUtils.rejectIfEmpty(errors, "trackerId", "uploadLog.trackerId.empty");
      ValidationUtils.rejectIfEmpty(errors, "dateTime", "uploadLog.dateTime.empty");
      ValidationUtils.rejectIfEmpty(errors, "eMail", "uploadLog.eMail.empty");
      ValidationUtils.rejectIfEmpty(errors, "bucket", "uploadLog.bucket.empty");
      ValidationUtils.rejectIfEmpty(errors, "fileName", "uploadLog.fileName.empty");
  }
}