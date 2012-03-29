/*
 * Copyright (c) 2012 Aurélien VIALE
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.soat.devoxx.game.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
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

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import fr.soat.devoxx.game.forms.UserForm;
import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.UserRole;
import fr.soat.devoxx.game.services.UserRoleServices;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.tools.TilesUtil;

/**
 * @author aurelien
 * 
 */
@Controller
@RequestMapping(value = "/admin/user")
public class AdminUserController {

	@Autowired
	UserServices userServices;
	
	@Autowired
    UserRoleServices userRoleServices;
	
	private static Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

	@RequestMapping(value = "/admin/user")
	public String showAllUser(Model model) {
		model.addAttribute("allUserResponses", userServices.getAllUsers());
		return TilesUtil.DFR_ADMIN_SHOWALLUSERS_PAGE;
	}

    @RequestMapping(value = "/{userId}")
    public String showUser(@PathVariable Long userId, Model model) {
        String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
        try {
            DevoxxUser user = userServices.getUser(userId);
            model.addAttribute("userResponse", user);
            model.addAttribute("mailHash", getEmailHash(user.getUserEmail()));
            model.addAttribute("userRolesComma", joinUserRoles(user));
            forward = TilesUtil.DFR_ADMIN_SHOWUSER_PAGE;
        } catch (RuntimeException e) {
            //TODO RuntimeException :( gérer des exceptions métiers coté userServices
            model.addAttribute("error", "admin.error.user.get");
            model.addAttribute("errorParams", userId);
            LOGGER.info("Error while fetching user", e);
        }
        return forward;
    }
    
    @RequestMapping(value = "/{userId}/update", method = RequestMethod.GET)
    public String updateUser(@PathVariable Long userId, Model model) {
        DevoxxUser user = userServices.getUser(userId);
        model.addAttribute("userResponse", user);   
        model.addAttribute("mailHash", getEmailHash(user.getUserEmail()));
        model.addAttribute("userRolesComma", joinUserRoles(user));
        model.addAttribute("userForm", new UserForm());
        
        return TilesUtil.DFR_ADMIN_UPDATEUSER_PAGE;
    }
    
    @RequestMapping(value = "/{userId}/update", method = RequestMethod.POST)
    public String updateUserPost(@PathVariable Long userId, Model model,
            @ModelAttribute("userForm") @Valid UserForm userForm, BindingResult result) {
        String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
        
        if (result.hasErrors()) {
            return TilesUtil.DFR_ADMIN_UPDATEUSER_PAGE;
        }
        
        try {
            DevoxxUser user = userServices.getUser(userId);
            user.setUserEmail(userForm.getUserEmail());
            user.setUserForname(userForm.getUserForname());
            List<String> rolesStr = splitUserRoles(userForm.getUserRoles());
            List<UserRole> roles = null;
            try {
                roles = userRoleServices.getUserRolesByNames(rolesStr);
            } catch (RuntimeException e) {
                LOGGER.debug("No Roles found", e);
            }
            user.setUserRoles(new HashSet<UserRole>(roles));
            LOGGER.info(user.getUserRoles().toString());
            userServices.updateUser(user);
            forward = "redirect:/admin/user/";
        } catch (RuntimeException e) {
            //TODO RuntimeException :( gérer des exceptions métiers coté userServices
            model.addAttribute("error", "admin.error.user.update");
            model.addAttribute("errorParams", userId);
            LOGGER.info("Error while updating user", e);
        }
        
        return forward;
    }

    @RequestMapping(value = "/{userId}/delete")
    public String removeUser(@PathVariable Long userId, Model model) {
        String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
        try {
            DevoxxUser user = userServices.getUser(userId);
            userServices.deleteUser(user);
            forward = "redirect:/admin/user/";
        } catch (RuntimeException e) {
            //TODO RuntimeException :( gérer des exceptions métiers coté userServices
            model.addAttribute("error", "admin.error.user.delete");
            model.addAttribute("errorParams", userId);
            LOGGER.info("Error while deleting user", e);
        }
        return forward;
    }
    
    private static List<String> splitUserRoles(String userRolesComma) {
        List<String> userRoles = new ArrayList<String>();
        Iterable<String> userRolesStr = Splitter.onPattern("[\\s]*[,;]{1}[\\s]*").split(userRolesComma);
        for (String roleName : userRolesStr) {
            //userRoles.add(new UserRole(roleName.trim().toUpperCase()));
            userRoles.add(roleName.trim().toUpperCase());
        }
        return userRoles;
    }
    
    private static String joinUserRoles(DevoxxUser user) {
        List<String> userRolesStr = new ArrayList<String>();
        for (UserRole role : user.getUserRoles()) {
            userRolesStr.add(role.getRoleName());
        }
        return Joiner.on(", ").join(userRolesStr);
    }
    
    private static String getEmailHash(String email) {
        String n_email = Strings.isNullOrEmpty(email) ? "" : email.trim().toLowerCase();
        return DigestUtils.md5Hex(n_email);
    }
}
