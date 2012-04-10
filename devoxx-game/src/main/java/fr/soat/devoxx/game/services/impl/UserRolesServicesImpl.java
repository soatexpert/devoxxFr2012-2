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
package fr.soat.devoxx.game.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.soat.devoxx.game.model.UserRole;
import fr.soat.devoxx.game.services.UserRoleServices;
import fr.soat.devoxx.game.services.repository.UserRoleRepository;

@Component
public class UserRolesServicesImpl implements UserRoleServices {

    @Autowired
    UserRoleRepository userRoleRepo;

    @Override
    public Iterable<UserRole> getAllUserRoles() {
        return userRoleRepo.findAll();
    }

    @Override
    public UserRole getUserRole(Long id_role) {
        return userRoleRepo.findOne(id_role);
    }

    @Override
    public UserRole getUserRoleByName(String roleName) {
        return userRoleRepo.findUserRoleByName(roleName);
    }

    @Override
    public List<UserRole> getUserRolesByNames(List<String> roleNames) {
        return userRoleRepo.findUserRolesByNames(roleNames);
    }

}
