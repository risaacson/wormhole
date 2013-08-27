package com.eucalyptus.wormhole.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {

  @RequestMapping(method = RequestMethod.GET)
  public String printWelcome(ModelMap model) {
    model.addAttribute("message", "Spring 3 MVC Hello World");
    return "test";
  }

}