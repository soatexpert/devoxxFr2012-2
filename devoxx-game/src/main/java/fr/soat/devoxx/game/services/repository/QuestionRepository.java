package fr.soat.devoxx.game.services.repository;

import fr.soat.devoxx.game.model.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question,Long>{
}
