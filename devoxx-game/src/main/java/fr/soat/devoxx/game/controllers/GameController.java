package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.exceptions.AlreadyAnsweredException;
import fr.soat.devoxx.game.exceptions.InvalidQuestionException;
import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.forms.AnswerForm;
import fr.soat.devoxx.game.forms.UserGameInformation;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.security.OpenIdUserDetails;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.tools.TilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
@SessionAttributes("userGameInfos")
public class GameController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private QuestionServices questionServices;

    @RequestMapping(value = {"/", "/index", ""})
    public String index(Model model) {
        final OpenIdUserDetails currentUser = (OpenIdUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!currentUser.getUser().isReglementAccepted()) {
            return TilesUtil.DFR_GAME_RULES_APPROVAL;
        }

        UserGameInformation userGameInformation  = new UserGameInformation(userServices.getPosition(),userServices.nbOfUsers(),userServices.getPendingQuestionsForUser(currentUser.getUser()));
        model.addAttribute("userGameInfos",userGameInformation);


        model.addAttribute("userName",currentUser.getUser().getUserForname());
        model.addAttribute("rank",userGameInformation.getCurrentRanking());
        model.addAttribute("nbUsers",userGameInformation.getNbOfPlayers());
        model.addAttribute("waitingQuestions",userGameInformation.getNbOfQuestionsToAnswer());


        return TilesUtil.DFR_GAME_INDEX_PAGE;
    }

    @RequestMapping("/approveRules")
    public String approveRules(Model model) {
        final OpenIdUserDetails currentUser = (OpenIdUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userServices.approveRules(currentUser.getUser());
        
        return index(model);
    }
    
    @RequestMapping("/play")
    public String play(@ModelAttribute("userGameInfos")UserGameInformation userGameInformation, Model model) {

        try {
            UserQuestion nextQuestion = userGameInformation.nextQuestion();

            nextQuestion.setStartQuestion(System.currentTimeMillis());

            questionServices.updateUserQuestion(nextQuestion);

            model.addAttribute("questionStartDate",nextQuestion.getStartQuestion());
            model.addAttribute("answerForm",new AnswerForm(nextQuestion.getQuestion().getIdQuestion()));
            model.addAttribute("question", nextQuestion.getQuestion());
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
                               Model model) {
        try {
            addUserInformationToModel(userGameInformation, model);

            UserQuestion question = answerQuestion(questionId, answer, userGameInformation);

            model.addAttribute("answerDelayInSeconds",question.getAnsweringTimeInSeconds());
            model.addAttribute("isAnswerCorrect",question.isAnswerCorrect());
            model.addAttribute("rightAnswer", question.getCorrectAnswer());

            return TilesUtil.DFR_GAME_ANSWER_PAGE;
        } catch(AlreadyAnsweredException e) {
            return index(model);
        } catch (InvalidQuestionException e) {
            return index(model);
        }
    }

    @RequestMapping(value = "/pause")
    public String pause(@ModelAttribute("userGameInfos") UserGameInformation userGameInformation,
                        Model model) {
        return index(model);
    }

    private void addUserInformationToModel(UserGameInformation userGameInformation, Model model) {
        model.addAttribute("nbOfQuestionsAnswered",userGameInformation.getNbOfQuestionAnswered());
        model.addAttribute("nbOfQuestionsTotal",userGameInformation.getNbOfQuestionsInProgress());
        model.addAttribute("nbOfQuestionLeft",userGameInformation.getNbOfQuestionsToAnswer());
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
                userQuestion.setReponse(choice);
                userQuestion.setEndQuestion(System.currentTimeMillis());
                questionServices.updateUserQuestion(userQuestion);
            }
        }
    }

    private void checkQuestionNotAlreadyAnswered(UserQuestion userQuestion) {
        if(userQuestion.getReponse() != null) {
            throw new AlreadyAnsweredException();
        }
    }
}
