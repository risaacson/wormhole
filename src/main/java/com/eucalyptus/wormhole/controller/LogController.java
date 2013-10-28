package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.model.UploadLog;
import com.eucalyptus.wormhole.service.UploadLogService;
import com.eucalyptus.wormhole.validation.UploadLogValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 */
@Controller
@RequestMapping("/log")
public class LogController {

  final Logger logger = LoggerFactory.getLogger(LogController.class);

  @Autowired
  private UploadLogService uploadLogService;

  @Autowired
  private UploadLogValidator uploadLogValidator;

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    binder.setValidator(uploadLogValidator);
  }

  @RequestMapping(value = "/upload", method = RequestMethod.GET)
//  public String updateLogForm(Model model) {
  public ModelAndView updateLogForm(Model model) {
    UploadLog uploadLog = new UploadLog();
    model.addAttribute("uploadLog", uploadLog);
//    return "uploadLogForm";
    return new ModelAndView("uploadLogForm", "uploadLog", uploadLog);
  }

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public ModelAndView addUpdateLog(@ModelAttribute @Valid UploadLog uploadLog, BindingResult result) {
//  public ModelAndView addUpdateLog(@ModelAttribute UploadLog uploadLog) {
    String message;
    if(result.hasErrors()) {
      message = result.toString();
    } else {
      uploadLogService.create(uploadLog);
      message = "success";
    }
    return new ModelAndView("message", "message", message);
  }
}
