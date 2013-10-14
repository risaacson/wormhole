package com.eucalyptus.wormhole.model;

/**
 */
public class AwsProperties {
  private String awsRegion;
  private String awsProxyType;
  private String awsProxyProtocol;
  private String awsProxyHost;
  private int awsProxyPort;

  public String getAwsRegion() {
    return awsRegion;
  }

  public void setAwsRegion(String awsRegion) {
    this.awsRegion = awsRegion;
  }

  public String getAwsProxyType() {
    return awsProxyType;
  }

  public void setAwsProxyType(String awsProxyType) {
    this.awsProxyType = awsProxyType;
  }

  public String getAwsProxyProtocol() {
    return awsProxyProtocol;
  }

  public void setAwsProxyProtocol(String awsProxyProtocol) {
    this.awsProxyProtocol = awsProxyProtocol;
  }

  public String getAwsProxyHost() {
    return awsProxyHost;
  }

  public void setAwsProxyHost(String awsProxyHost) {
    this.awsProxyHost = awsProxyHost;
  }

  public int getAwsProxyPort() {
    return awsProxyPort;
  }

  public void setAwsProxyPort(int awsProxyPort) {
    this.awsProxyPort = awsProxyPort;
  }
}
