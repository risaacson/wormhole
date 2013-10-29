/*
 * Copyright 2009-2013 Eucalyptus Systems, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Please contact Eucalyptus Systems, Inc., 6755 Hollister Ave., Goleta
 * CA 93117, USA or visit http://www.eucalyptus.com/licenses/ if you need
 * additional information or have any questions.
 *
 * This file may incorporate work covered under the following copyright
 * and permission notice:
 *
 *   Software License Agreement (BSD License)
 *
 *   Copyright (c) 2008, Regents of the University of California
 *   All rights reserved.
 *
 *   Redistribution and use of this software in source and binary forms,
 *   with or without modification, are permitted provided that the
 *   following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer
 *     in the documentation and/or other materials provided with the
 *     distribution.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *   FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *   COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *   INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *   BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *   CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *   LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *   ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE. USERS OF THIS SOFTWARE ACKNOWLEDGE
 *   THE POSSIBLE PRESENCE OF OTHER OPEN SOURCE LICENSED MATERIAL,
 *   COPYRIGHTED MATERIAL OR PATENTED MATERIAL IN THIS SOFTWARE,
 *   AND IF ANY SUCH MATERIAL IS DISCOVERED THE PARTY DISCOVERING
 *   IT MAY INFORM DR. RICH WOLSKI AT THE UNIVERSITY OF CALIFORNIA,
 *   SANTA BARBARA WHO WILL THEN ASCERTAIN THE MOST APPROPRIATE REMEDY,
 *   WHICH IN THE REGENTS' DISCRETION MAY INCLUDE, WITHOUT LIMITATION,
 *   REPLACEMENT OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO
 *   IDENTIFIED, OR WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT
 *   NEEDED TO COMPLY WITH ANY SUCH LICENSES OR RIGHTS.
 */

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
 *
 *
 * @author richard@eucalyptus.com (Richard Isaacson)
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