package com.eucalyptus.wormhole.model;

import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class BlackholeProperties {
  private String prefix;

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
}