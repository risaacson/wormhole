package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.exception.CompanyToBucketNotFound;
import com.eucalyptus.wormhole.model.CompanyToBucket;
import com.eucalyptus.wormhole.service.CompanyToBucketService;
import com.eucalyptus.wormhole.validation.CompanyToBucketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 */
@Controller
@RequestMapping("/admin/companyToBucket")
public class CompanyToBucketController {

  final Logger logger = LoggerFactory.getLogger(CompanyToBucketController.class);

  @Autowired
  private CompanyToBucketService companyToBucketService;

  @Autowired
  private CompanyToBucketValidator companyToBucketValidator;

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    binder.setValidator(companyToBucketValidator);
  }

  @RequestMapping(value="/create", method= RequestMethod.GET)
  public ModelAndView newCompanyToBucketPage() {
    ModelAndView modelAndView = new ModelAndView("company-to-bucket-edit");
    modelAndView.addObject("company-to-bucket", new CompanyToBucket());
    modelAndView.addObject("action", "create");
    return modelAndView;
  }

  @RequestMapping(value="/create", method= RequestMethod.POST)
  public ModelAndView createNewCompanyToBucket(@ModelAttribute @Valid CompanyToBucket companyToBucket, BindingResult result) {

    if(result.hasErrors()) return newCompanyToBucketPage();

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
  public ModelAndView editCompanyToBucket(@ModelAttribute @Valid CompanyToBucket companyToBucket, BindingResult result, @PathVariable Integer id) throws CompanyToBucketNotFound {

    if(result.hasErrors()) return new ModelAndView("company-to-bucket-edit");

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