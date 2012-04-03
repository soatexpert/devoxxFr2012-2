package fr.soat.devoxx.game.services.repository;

import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionPackType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question,Long>{

    @Query("FROM Question q where q.pack=?1")
    List<Question> findQuestionsByPack(QuestionPackType pack);
}
