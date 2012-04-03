package fr.soat.devoxx.game.services.repository;

import fr.soat.devoxx.game.model.QuestionPackType;
import fr.soat.devoxx.game.model.UserQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserQuestionRepository extends CrudRepository<UserQuestion, Long> {
    @Query("FROM UserQuestion uq where uq.question.pack=?1 and uq.response is null and uq.player.userId=?2 and uq.hourOfappearence <= ?3")
    List<UserQuestion> findRemainingQuestionsByPackAndUserIdLimitedByHour(QuestionPackType questionPackType, Long userId, int hour);

}
