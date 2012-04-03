package fr.soat.devoxx.game.services.impl;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionPackType;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.repository.QuestionRepository;
import fr.soat.devoxx.game.services.repository.UserQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;


@Controller
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
        return userQuestionRepository.findRemainingQuestionsByPackAndUserIdLimitedByHour(
                QuestionPackType.packForToday(),
                currentUser.getUserId(),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    }

    @Override
    public void saveBundleOfUserQuestions(List<UserQuestion> userQuestions) {
        for(UserQuestion question : userQuestions) {
            userQuestionRepository.save(question);
        }
    }


}
