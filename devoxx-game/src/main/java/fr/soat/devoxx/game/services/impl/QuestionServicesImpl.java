package fr.soat.devoxx.game.services.impl;

import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;


@Controller
public class QuestionServicesImpl implements QuestionServices {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public void updateUserQuestion(UserQuestion nextQuestion) {
        //TODO
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


}
