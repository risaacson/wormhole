package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.exception.CompanyToBucketNotFound;
import com.eucalyptus.wormhole.model.CompanyToBucket;
import com.eucalyptus.wormhole.service.CompanyToBucketService;
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

//import com.eucalyptus.wormhole.validation.CompanyToBucketValidator;
//import org.springframework.web.bind.WebDataBinder;

/**
 */
@Controller
@RequestMapping("/admin/companyToBucket")
public class CompanyToBucketController {

  final Logger logger = LoggerFactory.getLogger(CompanyToBucketController.class);

  @Autowired
  private CompanyToBucketService companyToBucketService;

//  @Autowired
//  private CompanyToBucketValidator companyToBucketValidator;
//
//  @InitBinder
//  private void initBinder(WebDataBinder binder) {
//    binder.setValidator(companyToBucketValidator);
//  }

  @RequestMapping(value="/create", method= RequestMethod.GET)
  public ModelAndView newCompanyToBucketPage() {
    ModelAndView modelAndView = new ModelAndView("company-to-bucket-edit");
    modelAndView.addObject("company-to-bucket", new CompanyToBucket());
    modelAndView.addObject("action", "create");
    return modelAndView;
  }

  @RequestMapping(value="/create", method= RequestMethod.POST)
  public ModelAndView createNewCompanyToBucket(@ModelAttribute @Valid CompanyToBucket companyToBucket, BindingResult result, Model model) {

      if(result.hasErrors()) {
          ModelAndView modelAndView = new ModelAndView("company-to-bucket-edit");
          modelAndView.addObject("action", "create");
          return modelAndView;
      }

    String message = "New e-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully created.";

    companyToBucketService.create(companyToBucket);

    ModelAndView modelAndView = new ModelAndView("company-to-bucket-list");
    List<CompanyToBucket> companyToBucketList = companyToBucketService.findAll();
    modelAndView.addObject("companyToBucketList", companyToBucketList);
    modelAndView.addObject("message", message);

    return modelAndView;
  }

  @RequestMapping(value="", method= RequestMethod.GET)
  public ModelAndView companyToBucketListPage() {

    ModelAndView modelAndView = new ModelAndView("company-to-bucket-list");

    List<CompanyToBucket> companyToBucketList = companyToBucketService.findAll();
    modelAndView.addObject("companyToBucketList", companyToBucketList);

    return modelAndView;
  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.GET)
  public ModelAndView editCompanyToBucketPage(@PathVariable Integer id) {

    ModelAndView modelAndView = new ModelAndView("company-to-bucket-edit");
    CompanyToBucket companyToBucket = companyToBucketService.findById(id);
    modelAndView.addObject("company-to-bucket", companyToBucket);
    modelAndView.addObject("action", "edit/" + companyToBucket.getId());

    return modelAndView;
  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.POST)
  public ModelAndView editCompanyToBucket(@ModelAttribute @Valid CompanyToBucket companyToBucket, BindingResult result, Model model, @PathVariable Integer id) throws CompanyToBucketNotFound {

    if(result.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("company-to-bucket-edit");
      modelAndView.addObject("action", "edit/" + companyToBucket.getId());
      return modelAndView;
    }

    String message = "E-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully updated.";

    companyToBucketService.update(companyToBucket);

    ModelAndView modelAndView = new ModelAndView("company-to-bucket-list");
    List<CompanyToBucket> companyToBucketList = companyToBucketService.findAll();
    modelAndView.addObject("companyToBucketList", companyToBucketList);
    modelAndView.addObject("message", message);

    return modelAndView;
  }

  @RequestMapping(value="/delete/{id}", method= RequestMethod.GET)
  public ModelAndView deleteCompanyToBucket(@PathVariable Integer id) throws CompanyToBucketNotFound {

    CompanyToBucket companyToBucket = companyToBucketService.delete(id);

    String message = "The e-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully deleted.";

    ModelAndView modelAndView = new ModelAndView("company-to-bucket-list");
    List<CompanyToBucket> companyToBucketList = companyToBucketService.findAll();
    modelAndView.addObject("companyToBucketList", companyToBucketList);
    modelAndView.addObject("message", message);

    return modelAndView;
  }
}