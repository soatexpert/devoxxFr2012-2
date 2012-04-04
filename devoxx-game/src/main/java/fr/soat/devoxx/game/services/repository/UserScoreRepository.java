package fr.soat.devoxx.game.services.repository;

import fr.soat.devoxx.game.model.UserScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Transactional
public interface UserScoreRepository extends CrudRepository<UserScore,Long> {
}
