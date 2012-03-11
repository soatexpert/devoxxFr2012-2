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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping(value = "/admin/user")
	public String showAllUser(Model model) {
		model.addAttribute("allUserResponses", userServices.getUsers());
		return TilesUtil.DFR_ADMIN_SHOWALLUSERS_PAGE;
	}

	@RequestMapping(value = "/{userId}")
	public String showUser(@PathVariable Long userId, Model model) {
		// String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
		// try {
		// UserResponseDto userResponse = adminUserService.getUser(userId);
		//
		// model.addAttribute("userResponse", userResponse);
		// String email = StringUtils.isEmpty(userResponse.getMail()) ?
		// StringUtils.EMPTY : userResponse.getMail().trim().toLowerCase();
		// model.addAttribute("mailHash", DigestUtils.md5Hex(email));
		//
		// ResultResponseDto resultResponse =
		// adminResultService.getResultForUser(userId);
		// model.addAttribute("resultResponse", resultResponse);
		//
		// forward = TilesUtil.DFR_ADMIN_SHOWUSER_PAGE;
		// } catch (UserServiceException e) {
		// model.addAttribute("error", "admin.error.user.get");
		// model.addAttribute("errorParams", userId);
		// LOGGER.info("Error while fetching user", e);
		// }
		// return forward;
		return "";
	}

	@RequestMapping(value = "/{userId}/delete")
	public String removeUser(@PathVariable Long userId, Model model) {
		// String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
		// try {
		// adminUserService.deleteUser(userId);
		// forward = "redirect:/admin/user/";
		// } catch (UserServiceException e) {
		// model.addAttribute("error", "admin.error.user.delete");
		// model.addAttribute("errorParams", userId);
		// LOGGER.info("Error while deleting user", e);
		// }
		// return forward;
		return "";
	}
}
