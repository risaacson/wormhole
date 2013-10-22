package com.eucalyptus.wormhole.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
  final Logger logger = LoggerFactory.getLogger(AdminController.class);

  @RequestMapping(value="/", method= RequestMethod.GET)
  public ModelAndView index() { return new ModelAndView("admin"); }
}
