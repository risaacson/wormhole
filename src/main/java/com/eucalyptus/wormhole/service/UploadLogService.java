package com.eucalyptus.wormhole.service;

import com.eucalyptus.wormhole.exception.UploadLogNotFound;
import com.eucalyptus.wormhole.model.UploadLog;

import java.util.List;

public interface UploadLogService {

  public UploadLog create(UploadLog uploadLog);
  public UploadLog delete(int id) throws UploadLogNotFound;
  public List<UploadLog> findAll();
  public UploadLog update(UploadLog uploadLog) throws UploadLogNotFound;
  public UploadLog findById(int id);

}
