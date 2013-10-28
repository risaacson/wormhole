package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.model.UploadLog;
import com.eucalyptus.wormhole.service.UploadLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 */
@Controller
@RequestMapping("/log")
public class LogController {

  final Logger logger = LoggerFactory.getLogger(LogController.class);

  @Autowired
  private UploadLogService uploadLogService;

  @RequestMapping(value = "/upload", method = RequestMethod.GET)
  public String updateLogForm(Model model) {
    model.addAttribute("uploadLog", new UploadLog());
    return "uploadLogForm";
  }

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public String addUpdateLog(@ModelAttribute @Valid UploadLog uploadLog, BindingResult result, ModelMap modelMap) {
    String message;
    if(result.hasErrors()) {
      return "uploadLogForm";
    } else {
      uploadLogService.create(uploadLog);
      message = "success";
    }
    modelMap.addAttribute("message", message);
    return message;
  }
}
