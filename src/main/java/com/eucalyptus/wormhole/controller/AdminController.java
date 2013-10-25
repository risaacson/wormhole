package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.model.EmailToBucket;
import com.eucalyptus.wormhole.model.UploadLog;
import com.eucalyptus.wormhole.service.UploadLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
  final Logger logger = LoggerFactory.getLogger(AdminController.class);

  @Autowired
  private UploadLogService uploadLogService;

  @RequestMapping(value="/", method= RequestMethod.GET)
  public ModelAndView index() { return new ModelAndView("admin"); }

  @RequestMapping(value="/recent", method= RequestMethod.GET)
  public ModelAndView uploadLogListPage() {
    ModelAndView modelAndView = new ModelAndView("recent-uploads-list");

    List<UploadLog> uploadLogList = uploadLogService.findRecentTwenty();
    modelAndView.addObject("uploadLogList", uploadLogList);

    return modelAndView;
  }
}