package com.eucalyptus.wormhole.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "company_to_bucket")
public class CompanyToBucket {

  @Id
  @GeneratedValue
  private Integer id;

  private String company;

  private String bucket;

}
