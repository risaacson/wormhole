package com.eucalyptus.wormhole.model;

/**
 */
import javax.persistence.*;

@Entity
@Table(name = "email_to_bucket")
public class EmailToBucket {

  @Id
  @GeneratedValue
  private Integer id;

  private String email;

  private String bucket;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }
}