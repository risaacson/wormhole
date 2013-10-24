package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.model.UploadLog;
import com.eucalyptus.wormhole.service.UploadLogService;
import com.eucalyptus.wormhole.validation.UploadLogValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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

  @RequestMapping(value = "/upload", method = RequestMethod.PUT)
  public ModelAndView addUpdateLog(@ModelAttribute @Valid UploadLog uploadLog, BindingResult result) {
    ModelAndView modelAndView = new ModelAndView();

    if(result.hasErrors()) return new ModelAndView("blank");

    uploadLogService.create(uploadLog);

     modelAndView.setViewName("blank");

    return modelAndView;
  }
}
