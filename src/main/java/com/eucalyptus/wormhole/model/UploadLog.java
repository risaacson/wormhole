package com.eucalyptus.wormhole.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "upload_log")
public class UploadLog {
  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "tracker_id", nullable = false)
  private String trackerId;

  @Column(name = "date_time", nullable = false)
  private String dateTime;

  @Column(name = "bucket", nullable = false)
  private String bucket;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Column(name = "email", nullable = false)
  private String eMail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
}
