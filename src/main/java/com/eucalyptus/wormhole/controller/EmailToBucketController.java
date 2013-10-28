package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.exception.EmailToBucketNotFound;
import com.eucalyptus.wormhole.model.EmailToBucket;
import com.eucalyptus.wormhole.service.EmailToBucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

//import com.eucalyptus.wormhole.validation.EmailToBucketValidator;
//import org.springframework.web.bind.WebDataBinder;

/**
 */
@Controller
@RequestMapping("/admin/emailToBucket")
public class EmailToBucketController {

  final Logger logger = LoggerFactory.getLogger(EmailToBucketController.class);

  @Autowired
  private EmailToBucketService emailToBucketService;

//  @Autowired
//  private EmailToBucketValidator emailToBucketValidator;
//
//  @InitBinder
//  private void initBinder(WebDataBinder binder) {
//    binder.setValidator(emailToBucketValidator);
//  }

  @RequestMapping(value="/create", method= RequestMethod.GET)
  public ModelAndView newEmailToBucketPage() {
    ModelAndView modelAndView = new ModelAndView("email-to-bucket-edit");
    modelAndView.addObject("email-to-bucket", new EmailToBucket());
    modelAndView.addObject("action", "create");
    return modelAndView;
  }

  @RequestMapping(value="/create", method= RequestMethod.POST)
  public ModelAndView createNewEmailToBucket(@ModelAttribute @Valid EmailToBucket emailToBucket, BindingResult result, Model model) {

      if(result.hasErrors()) {
          ModelAndView modelAndView = new ModelAndView("email-to-bucket-edit");
          modelAndView.addObject("action", "create");
          return modelAndView;
      }

    String message = "New e-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully created.";

    emailToBucketService.create(emailToBucket);

    ModelAndView modelAndView = new ModelAndView("admin");
    List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
    modelAndView.addObject("emailToBucketList", emailToBucketList);
    modelAndView.addObject("message", message);

    return modelAndView;
  }

  @RequestMapping(value="", method= RequestMethod.GET)
  public ModelAndView emailToBucketListPage() {

    ModelAndView modelAndView = new ModelAndView("admin");

    List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
    modelAndView.addObject("emailToBucketList", emailToBucketList);

    return modelAndView;
  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.GET)
  public ModelAndView editEmailToBucketPage(@PathVariable Integer id) {

    ModelAndView modelAndView = new ModelAndView("email-to-bucket-edit");
    EmailToBucket emailToBucket = emailToBucketService.findById(id);
    modelAndView.addObject("email-to-bucket", emailToBucket);
    modelAndView.addObject("action", "edit/" + emailToBucket.getId());

    return modelAndView;
  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.POST)
  public ModelAndView editEmailToBucket(@ModelAttribute @Valid EmailToBucket emailToBucket, BindingResult result, Model model, @PathVariable Integer id) throws EmailToBucketNotFound {

    if(result.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("email-to-bucket-edit");
      modelAndView.addObject("action", "edit/" + emailToBucket.getId());
      return modelAndView;
    }

    String message = "E-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully updated.";

    emailToBucketService.update(emailToBucket);

    ModelAndView modelAndView = new ModelAndView("admin");
    List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
    modelAndView.addObject("emailToBucketList", emailToBucketList);
    modelAndView.addObject("message", message);

    return modelAndView;
  }

  @RequestMapping(value="/delete/{id}", method= RequestMethod.GET)
  public ModelAndView deleteEmailToBucket(@PathVariable Integer id) throws EmailToBucketNotFound {

    EmailToBucket emailToBucket = emailToBucketService.delete(id);

    String message = "The e-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully deleted.";

      ModelAndView modelAndView = new ModelAndView("admin");
      List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
      modelAndView.addObject("emailToBucketList", emailToBucketList);
      modelAndView.addObject("message", message);

    return modelAndView;
  }
}