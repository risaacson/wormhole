package com.eucalyptus.wormhole.model;

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
