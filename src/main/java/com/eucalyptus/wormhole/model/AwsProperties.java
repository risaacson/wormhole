package com.eucalyptus.wormhole.model;

/**
 */
public class AwsProperties {
  private String region;
  private String proxyType;
  private String proxyProtocol;
  private String proxyHost;
  private int proxyPort;

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getProxyType() {
    return proxyType;
  }

  public void setProxyType(String proxyType) {
    this.proxyType = proxyType;
  }

  public String getProxyProtocol() {
    return proxyProtocol;
  }

  public void setProxyProtocol(String proxyProtocol) {
    this.proxyProtocol = proxyProtocol;
  }

  public String getProxyHost() {
    return proxyHost;
  }

  public void setProxyHost(String proxyHost) {
    this.proxyHost = proxyHost;
  }

  public int getProxyPort() {
    return proxyPort;
  }

  public void setProxyPort(int proxyPort) {
    this.proxyPort = proxyPort;
  }
}