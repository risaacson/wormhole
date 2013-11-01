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

import com.eucalyptus.wormhole.exception.SecurityAuthoritiesNotFound;
import com.eucalyptus.wormhole.exception.SecurityUsersNotFound;
import com.eucalyptus.wormhole.model.SecurityAuthorities;
import com.eucalyptus.wormhole.model.SecurityUsers;
import com.eucalyptus.wormhole.service.SecurityAuthoritiesService;
import com.eucalyptus.wormhole.service.SecurityUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 *
 *
 * @author richard@eucalyptus.com (Richard Isaacson)
 */
@Controller
@RequestMapping("/admin/security")
public class SecurityController {

  @Autowired
  private SecurityAuthoritiesService securityAuthoritiesService;

  @Autowired
  private SecurityUsersService securityUsersService;

  @RequestMapping(value="/authorities/create", method= RequestMethod.GET)
  public String newSecurityAuthorities(ModelMap model) {
    model.addAttribute("security-authorities", new SecurityAuthorities());
    model.addAttribute("action", "create");
    return "security-authorities-edit";
  }

  @RequestMapping(value="/authorities/create", method= RequestMethod.POST)
  public String createNewSecurityAuthorities(@ModelAttribute("security-authorities") @Valid SecurityAuthorities securityAuthorities, BindingResult result, ModelMap model) {
    if(result.hasErrors()) {
      model.addAttribute("action", "create/" + securityAuthorities.getUsername());
      return "security-authorities-edit";
    }

    securityAuthoritiesService.create(securityAuthorities);

    String message = "Authority " + securityAuthorities.getUsername() + " was successfully created.";
    model.addAttribute("message", message);

    return listSecurityAuthorities(model);
  }

  @RequestMapping(value="/authorities/edit/{id}", method= RequestMethod.GET)
  public String editPageSecurityAuthorities(@PathVariable int id, ModelMap model) {
    SecurityAuthorities securityAuthorities = securityAuthoritiesService.findById(id);
    model.addAttribute("security-authorities", securityAuthorities);
    model.addAttribute("action", "edit/" + id);
    return "security-authorities-edit";
  }

  @RequestMapping(value="/authorities/edit", method= RequestMethod.POST)
  public String editSecurityAuthorities(@ModelAttribute("security-authorities") @Valid SecurityAuthorities securityAuthorities, BindingResult result, ModelMap model) throws SecurityAuthoritiesNotFound {
    if(result.hasErrors()) {
      model.addAttribute("action", "edit/" + securityAuthorities.getId());
      return "security-authorities-edit";
    }

    securityAuthoritiesService.update(securityAuthorities);

    String message = "Authority " + securityAuthorities.getId() + " was successfully updated.";
    model.addAttribute("message");

    return listSecurityAuthorities(model);
  }

  @RequestMapping(value="/authorities/delete/{id}", method= RequestMethod.GET)
  public String deleteSecurityAuthorities(@PathVariable int id, ModelMap model) throws SecurityAuthoritiesNotFound {
    SecurityAuthorities securityAuthorities = securityAuthoritiesService.delete(id);

    String message = "Authority " + id + " was successfully deleted.";
    model.addAttribute("message", message);

    return listSecurityAuthorities(model);
  }

  @RequestMapping(value = "/authorities", method = RequestMethod.GET)
  public String listSecurityAuthorities(ModelMap model) {
    model.addAttribute("authoritiesList", securityAuthoritiesService.findAll());
    return "security-authorities-list";
  }

  @RequestMapping(value="/users/create", method= RequestMethod.GET)
  public String newSecurityUsers(ModelMap model) {
    model.addAttribute("security-users", new SecurityUsers());
    model.addAttribute("action", "create");
    return "security-users-edit";
  }

  @RequestMapping(value="/users/create", method= RequestMethod.POST)
  public String createNewSecurityUsers(@ModelAttribute("security-users") @Valid SecurityUsers securityUsers, BindingResult result, ModelMap model) {
    if(result.hasErrors()) {
      model.addAttribute("action", "create/" + securityUsers.getId());
      return "security-users-edit";
    }

    securityUsersService.create(securityUsers);

    String message = "User " + securityUsers.getId() + " was successfully created.";
    model.addAttribute("message", message);

    return listSecurityUsers(model);
  }

  @RequestMapping(value="/users/edit/{id}", method= RequestMethod.GET)
  public String editPageSecurityUsers(@PathVariable int id, ModelMap model) {
    SecurityUsers securityUsers = securityUsersService.findById(id);
    model.addAttribute("security-users", securityUsers);
    model.addAttribute("action", "edit/" + id);
    return "security-users-edit";
  }

  @RequestMapping(value="/users/edit", method= RequestMethod.POST)
  public String editSecurityUsers(@ModelAttribute("security-users") @Valid SecurityUsers securityUsers, BindingResult result, ModelMap model) throws SecurityUsersNotFound {
    if(result.hasErrors()) {
      model.addAttribute("action", "edit/" + securityUsers.getId());
      return "security-users-edit";
    }

    securityUsersService.update(securityUsers);

    String message = "User " + securityUsers.getId() + " was successfully updated.";
    model.addAttribute("message");

    return listSecurityUsers(model);
  }

  @RequestMapping(value="/users/delete/{id}", method= RequestMethod.GET)
  public String deleteSecurityUsers(@PathVariable int id, ModelMap model) throws SecurityUsersNotFound {
    SecurityUsers securityUsers = securityUsersService.delete(id);

    String message = "User " + id + " was successfully deleted.";
    model.addAttribute("message", message);

    return listSecurityUsers(model);
  }

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public String listSecurityUsers(ModelMap model) {
    model.addAttribute("usersList", securityUsersService.findAll());
    return "security-users-list";
  }
}
