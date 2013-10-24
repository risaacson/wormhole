package com.eucalyptus.wormhole.service;

import com.eucalyptus.wormhole.exception.UploadLogNotFound;
import com.eucalyptus.wormhole.model.UploadLog;
import com.eucalyptus.wormhole.repository.UploadLogRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UploadLogServiceImpl implements UploadLogService {

  @Resource
  private UploadLogRepository uploadLogRepository;

    @Override
    public UploadLog create(UploadLog uploadLog) {
        UploadLog anUploadLog = uploadLog;
      return uploadLogRepository.save(anUploadLog);
    }

    @Override
    public UploadLog delete(int id) throws UploadLogNotFound {
        UploadLog anUploadLog = uploadLogRepository.findOne(id);

        if(anUploadLog == null) throw new UploadLogNotFound();

        uploadLogRepository.delete(anUploadLog);
        return anUploadLog;
    }

    @Override
    public List<UploadLog> findAll() {
        return uploadLogRepository.findAll();
    }

    @Override
    public UploadLog update(UploadLog uploadLog) throws UploadLogNotFound {
        UploadLog anUploadLog = uploadLogRepository.findOne(uploadLog.getId());

        if(anUploadLog == null) throw new UploadLogNotFound();

        anUploadLog.setTrackerId(uploadLog.getTrackerId());
        anUploadLog.setDateTime(uploadLog.getDateTime());
        anUploadLog.setBucket(uploadLog.getBucket());
        anUploadLog.setFileName(uploadLog.getFileName());
        anUploadLog.setEMail(uploadLog.getEMail());

        return anUploadLog;
    }

    @Override
    public UploadLog findById(int id) {
        return uploadLogRepository.findOne(id);
    }
}