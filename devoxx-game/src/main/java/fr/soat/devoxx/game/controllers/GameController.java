package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.exceptions.AlreadyAnsweredException;
import fr.soat.devoxx.game.exceptions.InvalidQuestionException;
import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.forms.AnswerForm;
import fr.soat.devoxx.game.forms.UserGameInformation;
import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.tools.TilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;

@Controller
@RequestMapping(value = "/")
@SessionAttributes("userGameInfos")
public class GameController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private QuestionServices questionServices;

    @RequestMapping(value = {"/", "/home","/index", ""})
    public String index(Map model) {
        final DevoxxUser currentUser = (DevoxxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!currentUser.isReglementAccepted()) {
            return TilesUtil.DFR_GAME_RULES_APPROVAL;
        }


        UserGameInformation userGameInformation  = new UserGameInformation(
                userServices.getPosition(currentUser),
                userServices.nbOfUsers(),
                questionServices.getPendingQuestionsForUser(currentUser));
        model.put("userGameInfos",userGameInformation);

        model.put("approuved",currentUser.isEnabled());
        model.put("username",currentUser.getUserForname());

        model.put("rank",userGameInformation.getCurrentRanking());
        model.put("nbUsers",userGameInformation.getNbOfPlayers());
        model.put("waitingQuestions",userGameInformation.getNbOfQuestionsToAnswer());


        return TilesUtil.DFR_GAME_INDEX_PAGE;
    }

    @RequestMapping("/approveRules")
    public String approveRules(Map model) {
        final DevoxxUser currentUser = (DevoxxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userServices.approveRules(currentUser);
        
        return index(model);
    }
    
    @RequestMapping("/play")
    public String play(@ModelAttribute("userGameInfos")UserGameInformation userGameInformation, Map model) {

        try {
            UserQuestion nextQuestion = userGameInformation.nextQuestion();

            nextQuestion.setStartQuestion(System.currentTimeMillis());

            questionServices.updateUserQuestion(nextQuestion);

            model.put("questionStartDate",nextQuestion.getStartQuestion());
            model.put("answerForm",new AnswerForm(nextQuestion.getQuestion().getIdQuestion()));
            model.put("question", nextQuestion.getQuestion());
            addUserInformationToModel(userGameInformation, model);
            return TilesUtil.DFR_GAME_PLAY_PAGE;
        } catch(NoMoreQuestionException e) {
            return index(model);
        }
    }

    @RequestMapping(value = "/next")
    public String nextQuestion(@ModelAttribute("userGameInfos") UserGameInformation userGameInformation,
                               @RequestParam("questionId") Long questionId,
                               @RequestParam("answer") Long answer,
                               Map model) {
        try {
            final DevoxxUser currentUser = (DevoxxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            addUserInformationToModel(userGameInformation, model);

            UserQuestion question = answerQuestion(questionId, answer, userGameInformation);

            updatePlayerScore(answer, currentUser, question);

            model.put("answerDelayInSeconds",question.getAnsweringTimeInSeconds());
            model.put("isAnswerCorrect",question.isAnswerCorrect());
            model.put("rightAnswer", question.getCorrectAnswer());

            return TilesUtil.DFR_GAME_ANSWER_PAGE;
        } catch(AlreadyAnsweredException e) {
            return index(model);
        } catch (InvalidQuestionException e) {
            return index(model);
        }
    }

    @RequestMapping(value = "/pause")
    public String pause(Map model) {
        return index(model);
    }

    @RequestMapping("/rules")
    public String rules() {
        return TilesUtil.DFR_GAME_RULES_PAGE;
    }

    @RequestMapping("/about")
    public String about() {
        return TilesUtil.DFR_GAME_ABOUT_PAGE;
    }

    private void updatePlayerScore(Long answer, DevoxxUser currentUser, UserQuestion question) {
        if(answer.equals(question.getCorrectAnswer().getQuestionChoiceId())) {
            currentUser.addToScore(1);
        }
        currentUser.addToTime(question.getAnsweringTimeInSeconds());
        userServices.updateUser(currentUser);
    }

    private void addUserInformationToModel(UserGameInformation userGameInformation, Map model) {
        model.put("nbOfQuestionsAnswered",userGameInformation.getNbOfQuestionAnswered());
        model.put("nbOfQuestionsTotal",userGameInformation.getNbOfQuestionsInProgress());
        model.put("nbOfQuestionLeft",userGameInformation.getNbOfQuestionsToAnswer());
    }

    private UserQuestion answerQuestion(Long questionId, Long answer, UserGameInformation userGameInformation) throws InvalidQuestionException {
       for (UserQuestion userQuestion : userGameInformation.getQuestionsInProgress()) {
            if(userQuestion.getQuestion().getIdQuestion().equals(questionId))  {
                checkQuestionNotAlreadyAnswered(userQuestion);

                updateQuestionWithAnswer(answer, userQuestion);

                return userQuestion;
            }
        }
        throw new InvalidQuestionException();
    }

    private void updateQuestionWithAnswer(Long answer, UserQuestion userQuestion) {
        for(QuestionChoice choice : userQuestion.getQuestion().getChoices()) {
            if(choice.getQuestionChoiceId().equals(answer)) {
                userQuestion.setResponse(choice);
                userQuestion.setEndQuestion(System.currentTimeMillis());
                questionServices.updateUserQuestion(userQuestion);
            }
        }
    }

    private void checkQuestionNotAlreadyAnswered(UserQuestion userQuestion) {
        if(userQuestion.getResponse() != null) {
            throw new AlreadyAnsweredException();
        }
    }
}
