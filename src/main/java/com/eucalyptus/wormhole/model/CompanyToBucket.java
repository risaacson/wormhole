package com.eucalyptus.wormhole.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "company_to_bucket")
public class CompanyToBucket {
  @Id
  @GeneratedValue
  private Integer id;

  @NotEmpty
  @Column(name = "company", nullable = false)
  private String company;

  @NotEmpty
  @Column(name = "bucket", nullable = false)
  private String bucket;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }
}