package com.eucalyptus.wormhole.controller;

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
 *
 */
@Controller
public class DefaultController {
  final Logger logger = LoggerFactory.getLogger(DefaultController.class);

  @Autowired
  private UploadLogService uploadLogService;

  @RequestMapping(value="/", method= RequestMethod.GET)
  public ModelAndView uploadLogListPage() {
    List<UploadLog> uploadLogList = uploadLogService.findRecentTwenty();
    return new ModelAndView("index", "uploadLogList", uploadLogList);
  }
}