package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.impl.QuestionServicesImpl;
import fr.soat.devoxx.game.services.repository.UserQuestionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

/**
 */
public class QuestionServiceTest {
    
    @Test
    public void when_updateAnswer_updateAnsweringInfo() {
        final QuestionServicesImpl questionServices = new QuestionServicesImpl();
        final UserQuestionRepository userQuestionRepo = Mockito.mock(UserQuestionRepository.class);
        questionServices.setUserQuestionRepository(userQuestionRepo);

        final UserQuestion userQuestion = new UserQuestion();
        final Question question = new Question();
        final QuestionChoice answer = new QuestionChoice();
        answer.setQuestionChoiceId(1l);
        question.setChoices(Arrays.asList(answer));
        userQuestion.setQuestion(question);
        
        questionServices.updateQuestionWithAnswer(userQuestion,1l);

        Assert.assertEquals(answer,userQuestion.getAnswer());
        Assert.assertTrue(userQuestion.getEndQuestion() > 0);
    }

    @Test
    public void when_updateAnswer_not_found_updateNothing() {
        final QuestionServicesImpl questionServices = new QuestionServicesImpl();
        final UserQuestionRepository userQuestionRepo = Mockito.mock(UserQuestionRepository.class);
        questionServices.setUserQuestionRepository(userQuestionRepo);

        final UserQuestion userQuestion = new UserQuestion();
        final Question question = new Question();
        final QuestionChoice answer = new QuestionChoice();
        answer.setQuestionChoiceId(1l);
        question.setChoices(Arrays.asList(answer));
        userQuestion.setQuestion(question);

        questionServices.updateQuestionWithAnswer(userQuestion, 2l);

        Assert.assertNull(userQuestion.getAnswer());
        Assert.assertTrue(userQuestion.getEndQuestion() == 0);
    }
}
