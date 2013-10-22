package com.eucalyptus.wormhole.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
@Controller
public class DefaultController {

  final Logger logger = LoggerFactory.getLogger(DefaultController.class);

  @RequestMapping(value={"/", "index"}, method=RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("index");
  }

}