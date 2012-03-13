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
import org.springframework.stereotype.Repository;

import fr.soat.devoxx.game.dao.GenericDao;
import fr.soat.devoxx.game.model.UserRoles;
import fr.soat.devoxx.game.services.UserRolesServices;

@Repository
public class UserRolesServicesImpl implements UserRolesServices {

    @Autowired
    GenericDao<UserRoles> userRolesDao;

    @SuppressWarnings("unchecked")
    @Override
    public List<UserRoles> getAllUserRoles() {
        return userRolesDao.getEntityManager().createQuery("from UserRoles").getResultList();
    }

    @Override
    public UserRoles getUserRole(Long id_role) {
        return userRolesDao.getEntityManager().find(UserRoles.class, id_role);
    }

    @Override
    public UserRoles getUserRoleByName(String roleName) {
        return (UserRoles) userRolesDao.getEntityManager().createQuery("from UserRoles WHERE roleName=?1").setParameter(1, roleName).setMaxResults(1)
                .getSingleResult();
    }

}
