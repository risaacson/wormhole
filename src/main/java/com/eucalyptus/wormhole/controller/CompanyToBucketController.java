package com.eucalyptus.wormhole.controller;

import com.eucalyptus.wormhole.exception.CompanyToBucketNotFound;
import com.eucalyptus.wormhole.model.CompanyToBucket;
import com.eucalyptus.wormhole.service.CompanyToBucketService;
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
@RequestMapping("/admin/companyToBucket")
public class CompanyToBucketController {

  final Logger logger = LoggerFactory.getLogger(CompanyToBucketController.class);

  @Autowired
  private CompanyToBucketService companyToBucketService;

  @RequestMapping(value="/create", method= RequestMethod.GET)
  public String newCompanyToBucketPage(ModelMap model) {
    model.addAttribute("company-to-bucket", new CompanyToBucket());
    model.addAttribute("action", "create");
    return "company-to-bucket-edit";
  }

  @RequestMapping(value="/create", method= RequestMethod.POST)
  public String createNewCompanyToBucket(@ModelAttribute("company-to-bucket") @Valid CompanyToBucket companyToBucket, BindingResult result, ModelMap model) {

      if(result.hasErrors()) {
          model.addAttribute("action", "create");
          return "company-to-bucket-edit";
      }

    String message = "New e-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully created.";

    companyToBucketService.create(companyToBucket);

    List<CompanyToBucket> companyToBucketList = companyToBucketService.findAll();
    model.addAttribute("companyToBucketList", companyToBucketList);
    model.addAttribute("message", message);

    return "company-to-bucket-list";
  }

  @RequestMapping(value="", method= RequestMethod.GET)
  public String companyToBucketListPage(ModelMap model) {

    List<CompanyToBucket> companyToBucketList = companyToBucketService.findAll();
    model.addAttribute("companyToBucketList", companyToBucketList);

    return "company-to-bucket-list";
  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.GET)
  public String editCompanyToBucketPage(@PathVariable Integer id, ModelMap model) {

    CompanyToBucket companyToBucket = companyToBucketService.findById(id);
    model.addAttribute("company-to-bucket", companyToBucket);
    model.addAttribute("action", "edit/" + companyToBucket.getId());

    return "company-to-bucket-edit";
  }

  @RequestMapping(value="/edit/{id}", method= RequestMethod.POST)
  public String editCompanyToBucket(@ModelAttribute("company-to-bucket") @Valid CompanyToBucket companyToBucket, @PathVariable Integer id, BindingResult result, ModelMap model) throws CompanyToBucketNotFound {

    if(result.hasErrors()) {
      model.addAttribute("action", "edit/" + companyToBucket.getId());
      return "company-to-bucket-edit";
    }

    String message = "E-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully updated.";

    companyToBucketService.update(companyToBucket);

    List<CompanyToBucket> companyToBucketList = companyToBucketService.findAll();
    model.addAttribute("companyToBucketList", companyToBucketList);
    model.addAttribute("message", message);

    return "company-to-bucket-list";
  }

  @RequestMapping(value="/delete/{id}", method= RequestMethod.GET)
  public String deleteCompanyToBucket(@PathVariable Integer id, ModelMap model) throws CompanyToBucketNotFound {

    CompanyToBucket companyToBucket = companyToBucketService.delete(id);

    String message = "The e-mail to bucket relation " + companyToBucket.getCompany() + " <=> " + companyToBucket.getBucket() + " was successfully deleted.";

    List<CompanyToBucket> companyToBucketList = companyToBucketService.findAll();
    model.addAttribute("companyToBucketList", companyToBucketList);
    model.addAttribute("message", message);

    return "company-to-bucket-list";
  }
}