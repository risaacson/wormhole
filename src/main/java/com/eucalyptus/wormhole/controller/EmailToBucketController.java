package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.exception.EmailToBucketNotFound;
import com.eucalyptus.wormhole.model.EmailToBucket;
import com.eucalyptus.wormhole.service.EmailToBucketService;
import com.eucalyptus.wormhole.validation.EmailToBucketValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 */
@Controller
@RequestMapping("/admin/emailToBucket")
public class EmailToBucketController {

  @Autowired
  private EmailToBucketService emailToBucketService;

  @Autowired
  private EmailToBucketValidator emailTobucketValidator;

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    binder.setValidator(emailTobucketValidator);
  }

  @RequestMapping(value="/create", method= RequestMethod.GET)
  public ModelAndView newEmailToBucketPage() {
    return new ModelAndView("email-to-bucket-new", "email-to-bucket", new EmailToBucket());
  }

  @RequestMapping(value="/create", method= RequestMethod.POST)
  public ModelAndView createNewEmailToBucket(@ModelAttribute @Valid EmailToBucket emailToBucket, BindingResult result, final RedirectAttributes redirectAttributes) {

    if(result.hasErrors()) return new ModelAndView("email-to-bucket-new");

    ModelAndView modelAndView = new ModelAndView();
    String message = "New e-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully created.";

    emailToBucketService.create(emailToBucket);
    modelAndView.setViewName("index");

    redirectAttributes.addFlashAttribute("message", message);
    return modelAndView;

  }

  @RequestMapping(value="/list", method= RequestMethod.GET)
  public ModelAndView emailToBucketListPage() {

    ModelAndView modelAndView = new ModelAndView("email-to-bucket-list");

    List<EmailToBucket> emailToBucketList = emailToBucketService.findAll();
    modelAndView.addObject("emailToBucketList", emailToBucketList);

    return modelAndView;

  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.GET)
  public ModelAndView editEmailToBucketPage(@PathVariable Integer id) {

    ModelAndView modelAndView = new ModelAndView("email-to-bucket-edit");

    EmailToBucket emailToBucket = emailToBucketService.findById(id);
    modelAndView.addObject("email-to-bucket", emailToBucket);

    return modelAndView;

  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.POST)
  public ModelAndView editEmailToBucket(@ModelAttribute @Valid EmailToBucket emailToBucket, BindingResult result, @PathVariable Integer id, final RedirectAttributes redirectAttributes) throws EmailToBucketNotFound {

    if(result.hasErrors()) return new ModelAndView("email-to-bucket-edit");

    ModelAndView modelAndView = new ModelAndView("index");
    String message = "E-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully updated.";

    emailToBucketService.update(emailToBucket);

    redirectAttributes.addFlashAttribute("message", message);
    return modelAndView;

  }

  @RequestMapping(value="/delete/{id}", method= RequestMethod.GET)
  public ModelAndView deleteEmailToBucket(@PathVariable Integer id, final RedirectAttributes redirectAttributes) throws EmailToBucketNotFound {

    ModelAndView modelAndView = new ModelAndView("index");

    EmailToBucket emailToBucket = emailToBucketService.delete(id);
    String message = "The e-mail to bucket relation " + emailToBucket.getEmail() + " <=> " + emailToBucket.getBucket() + " was successfully deleted.";

    redirectAttributes.addFlashAttribute("message", message);
    return modelAndView;

  }

}