package com.eucalyptus.wormhole.util;

import com.eucalyptus.wormhole.factory.BlackholeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class BlackholeUtil {

  final Logger logger = LoggerFactory.getLogger(BlackholeUtil.class);

  public static String getPrefix() {
  BlackholeFactory bf = new BlackholeFactory();
    return bf.getPrefix();
  }

}
