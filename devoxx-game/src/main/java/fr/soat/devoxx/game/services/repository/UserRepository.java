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
package fr.soat.devoxx.game.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import fr.soat.devoxx.game.model.DevoxxUser;

@Transactional
public interface UserRepository extends CrudRepository<DevoxxUser, Long> {
    
    @Query("FROM DevoxxUser u where u.username=?1")
    public DevoxxUser findUserByName(String username);

    @Query("FROM DevoxxUser u")
    Page<DevoxxUser> findUsersWithPager(Pageable pageable); // Pageable -> order by + limit
    
    //@Query("SELECT COUNT(*) FROM DevoxxUser du WHERE du.score > (SELECT u.score FROM DevoxxUser u WHERE u.userId=?1 ORDER BY u.score DESC, u.totalTime ASC, u.userForname DESC)")
    @Query("SELECT COUNT(*) FROM DevoxxUser du WHERE du.score > (SELECT u.score FROM DevoxxUser u WHERE u.userId=?1)")
    Long findUserRankingPosition(Long userId);
}
