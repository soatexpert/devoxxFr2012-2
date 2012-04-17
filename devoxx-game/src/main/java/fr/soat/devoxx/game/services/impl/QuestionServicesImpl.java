package fr.soat.devoxx.game.services.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.QuestionPackType;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.repository.QuestionRepository;
import fr.soat.devoxx.game.services.repository.UserQuestionRepository;


@Component
public class QuestionServicesImpl implements QuestionServices {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserQuestionRepository userQuestionRepository;

    @Override
    public void updateUserQuestion(UserQuestion nextQuestion) {
        userQuestionRepository.save(nextQuestion);
    }

    @Override
    public Question getQuestionById(Long questionId) {
        return questionRepository.findOne(questionId);
    }

    @Override
    @Transactional
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public List<Question> getQuestionsByPack(QuestionPackType pack) {
        return questionRepository.findQuestionsByPack(pack);
    }

    @Override
    public List<UserQuestion> getPendingQuestionsForUser(DevoxxUser currentUser) {
         return userQuestionRepository.findRemainingQuestionsByPackAndUserId(
                QuestionPackType.packForToday(),
                currentUser.getUserId());
    }

    @Override
    public void saveBundleOfUserQuestions(List<UserQuestion> userQuestions) {
        for(UserQuestion question : userQuestions) {
            userQuestionRepository.save(question);
        }
    }

    public void updateQuestionWithAnswer(UserQuestion userQuestion, Long answer) {
        for(QuestionChoice choice : userQuestion.getQuestion().getChoices()) {
            if(choice.getQuestionChoiceId().equals(answer)) {
                userQuestion.setAnswer(choice);
                userQuestion.setEndQuestion(System.currentTimeMillis());
                updateUserQuestion(userQuestion);
            }
        }
    }

    public void setUserQuestionRepository(UserQuestionRepository userQuestionRepository) {
        this.userQuestionRepository = userQuestionRepository;
    }
}
