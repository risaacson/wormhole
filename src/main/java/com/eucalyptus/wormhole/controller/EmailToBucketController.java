package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.exception.EmailToBucketNotFound;
import com.eucalyptus.wormhole.model.EmailToBucket;
import com.eucalyptus.wormhole.service.EmailToBucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 */
@Controller
@RequestMapping("/admin/emailToBucket")
public class EmailToBucketController {

  final Logger logger = LoggerFactory.getLogger(EmailToBucketController.class);

  @Autowired
  private EmailToBucketService emailToBucketService;

  @RequestMapping(value="/create", method= RequestMethod.GET)
  public String newEmailToBucketPage(ModelMap model) {
    model.addAttribute("email-to-bucket", new EmailToBucket());
    model.addAttribute("action", "create");
    return "email-to-bucket-edit";
  }

  @RequestMapping(value="/create", method= RequestMethod.POST)
  public String createNewEmailToBucket(@ModelAttribute("email-to-bucket") @Valid EmailToBucket emailToBucket, BindingResult result, ModelMap model) {

      if(result.hasErrors()) {
          model.addAttribute("action", "create");
          return "email-to-bucket-edit";
      }

    String message = "New e-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully created.";

    emailToBucketService.create(emailToBucket);

    List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
    model.addAttribute("emailToBucketList", emailToBucketList);
    model.addAttribute("message", message);

    return "admin";
  }

  @RequestMapping(value="", method= RequestMethod.GET)
  public String emailToBucketListPage(ModelMap model) {

    List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
    model.addAttribute("emailToBucketList", emailToBucketList);

    return "admin";
  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.GET)
  public String editEmailToBucketPage(@PathVariable Integer id, ModelMap model) {

    EmailToBucket emailToBucket = emailToBucketService.findById(id);
    model.addAttribute("email-to-bucket", emailToBucket);
    model.addAttribute("action", "edit/" + emailToBucket.getId());

    return "email-to-bucket-edit";
  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.POST)
  public String editEmailToBucket(@ModelAttribute("email-to=bucket") @Valid EmailToBucket emailToBucket, BindingResult result, ModelMap model, @PathVariable Integer id) throws EmailToBucketNotFound {

    if(result.hasErrors()) {
      model.addAttribute("action", "edit/" + emailToBucket.getId());
      return "email-to-bucket-edit";
    }

    String message = "E-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully updated.";

    emailToBucketService.update(emailToBucket);

    List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
    model.addAttribute("emailToBucketList", emailToBucketList);
    model.addAttribute("message", message);

    return "admin";
  }

  @RequestMapping(value="/delete/{id}", method= RequestMethod.GET)
  public String deleteEmailToBucket(@PathVariable Integer id, ModelMap model) throws EmailToBucketNotFound {

    EmailToBucket emailToBucket = emailToBucketService.delete(id);

    String message = "The e-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully deleted.";

    List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
    model.addAttribute("emailToBucketList", emailToBucketList);
    model.addAttribute("message", message);

    return "admin";
  }
}