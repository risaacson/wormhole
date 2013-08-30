package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.exception.CompanyToBucketNotFound;
import com.eucalyptus.wormhole.model.CompanyToBucket;
import com.eucalyptus.wormhole.service.CompanyToBucketService;
import com.eucalyptus.wormhole.validation.CompanyToBucketValidator;

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
@RequestMapping("/admin/companyToBucket")
public class CompanyToBucketController {

  @Autowired
  private CompanyToBucketService companyToBucketService;

  @Autowired
  private CompanyToBucketValidator companyTobucketValidator;

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    binder.setValidator(companyTobucketValidator);
  }

  @RequestMapping(value="/create", method= RequestMethod.GET)
  public ModelAndView newCompanyToBucketPage() {
    return new ModelAndView("company-to-bucket-new", "company-to-bucket", new CompanyToBucket());
  }

  @RequestMapping(value="/create", method= RequestMethod.POST)
  public ModelAndView createNewCompanyToBucket(@ModelAttribute @Valid CompanyToBucket companyToBucket, BindingResult result, final RedirectAttributes redirectAttributes) {

    if(result.hasErrors()) return new ModelAndView("company-to-bucket-new");

    ModelAndView modelAndView = new ModelAndView();
    String message = "New e-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully created.";

    companyToBucketService.create(companyToBucket);
    modelAndView.setViewName("index");

    redirectAttributes.addFlashAttribute("message", message);
    return modelAndView;

  }

  @RequestMapping(value="/list", method= RequestMethod.GET)
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

    return modelAndView;

  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.POST)
  public ModelAndView editCompanyToBucket(@ModelAttribute @Valid CompanyToBucket companyToBucket, BindingResult result, @PathVariable Integer id, final RedirectAttributes redirectAttributes) throws CompanyToBucketNotFound {

    if(result.hasErrors()) return new ModelAndView("company-to-bucket-edit");

    ModelAndView modelAndView = new ModelAndView("index");
    String message = "E-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully updated.";

    companyToBucketService.update(companyToBucket);

    redirectAttributes.addFlashAttribute("message", message);
    return modelAndView;

  }

  @RequestMapping(value="/delete/{id}", method= RequestMethod.GET)
  public ModelAndView deleteCompanyToBucket(@PathVariable Integer id, final RedirectAttributes redirectAttributes) throws CompanyToBucketNotFound {

    ModelAndView modelAndView = new ModelAndView("index");

    CompanyToBucket companyToBucket = companyToBucketService.delete(id);
    String message = "The e-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully deleted.";

    redirectAttributes.addFlashAttribute("message", message);
    return modelAndView;

  }

}
