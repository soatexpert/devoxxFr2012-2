package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.exceptions.QuestionNotFoundException;
import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.forms.AnswerForm;
import fr.soat.devoxx.game.services.QuestionsProgressTracker;
import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.tools.TilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
@SessionAttributes("questionsProgressTracker")
public class GameController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private QuestionServices questionServices;

    @RequestMapping(value = {"", "/", "/home","/index", "/pause"})
    public String index(final Map model, final Principal principal) {

        final DevoxxUser user = convertPrincipalToDevoxxUser(principal);

        return processIndexPageForUser(model, user);
    }

    private String processIndexPageForUser(final Map model, final DevoxxUser user) {
        if(!user.isRulesApproved()) {
            return TilesUtil.DFR_GAME_RULES_APPROVAL;
        }

        final QuestionsProgressTracker questionsProgressTracker = new QuestionsProgressTracker(
                questionServices.getPendingQuestionsForUser(user));

        model.put("questionsProgressTracker", questionsProgressTracker);

        model.put("approved", user.isEnabled());
        model.put("username", user.getUserForname());

        model.put("rank", userServices.getPosition(user));
        model.put("nbUsers", userServices.nbOfUsers());
        model.put("waitingQuestions", questionsProgressTracker.getNbOfQuestionsToAnswer());


        return TilesUtil.DFR_GAME_INDEX_PAGE;
    }

    @RequestMapping("/approveRules")
    public String approveRules(final Map model, final Principal principal) {
        final DevoxxUser user = convertPrincipalToDevoxxUser(principal);

        userServices.approveRules(user);
        
        return processIndexPageForUser(model, user);
    }

    @RequestMapping("/play")
    public String play(@ModelAttribute("questionsProgressTracker") final QuestionsProgressTracker questionsProgressTracker, 
                       final Map model, 
                       final Principal principal) {
        try {
            final UserQuestion nextQuestion = questionsProgressTracker.nextQuestion();

            nextQuestion.setStartQuestion(System.currentTimeMillis());

            questionServices.updateUserQuestion(nextQuestion);

            model.put("answerForm", new AnswerForm(nextQuestion.getQuestion().getIdQuestion()));
            model.put("question", nextQuestion.getQuestion());
            addQuestionsProgressInformationToModel(questionsProgressTracker, model);

            return TilesUtil.DFR_GAME_PLAY_PAGE;
        } catch(NoMoreQuestionException e) {
            return index(model, principal);
        }
    }

    @RequestMapping(value = "/next")
    public String nextQuestion(@ModelAttribute("questionsProgressTracker") QuestionsProgressTracker questionsProgressTracker,
                               @RequestParam("questionId") Long questionId,
                               @RequestParam("answer") Long answerId,
                               Map model, Principal principal) {
        try {
            final DevoxxUser currentUser = convertPrincipalToDevoxxUser(principal);

            addQuestionsProgressInformationToModel(questionsProgressTracker, model);

            final UserQuestion question = questionsProgressTracker.findQuestionById(questionId);

            if(question.isAlreadyAnswered()) {
                return index(model, principal);
            }

            questionServices.updateQuestionWithAnswer(question, answerId);

            userServices.updatePlayerScore(question, currentUser);

            model.put("answerDelayInSeconds", question.getAnsweringTimeInSeconds());
            model.put("isAnswerCorrect", question.isAnswerCorrect());
            model.put("rightAnswer", question.getCorrectAnswer());

            return TilesUtil.DFR_GAME_ANSWER_PAGE;
        } catch (QuestionNotFoundException e) {
            return index(model, principal);
        }
    }

    @RequestMapping("/rules")
    public String rules() {
        return TilesUtil.DFR_GAME_RULES_PAGE;
    }

    @RequestMapping("/about")
    public String about() {
        return TilesUtil.DFR_GAME_ABOUT_PAGE;
    }

    private DevoxxUser convertPrincipalToDevoxxUser(Principal principal) {
        final DevoxxUser sessionUser = (DevoxxUser)((OpenIDAuthenticationToken)principal).getPrincipal();
        return userServices.getUser(sessionUser.getUserId());
    }

    private void addQuestionsProgressInformationToModel(QuestionsProgressTracker questionsProgressTracker, Map model) {
        model.put("nbOfQuestionsAnswered", questionsProgressTracker.getNbOfQuestionAnswered());
        model.put("nbOfQuestionsTotal", questionsProgressTracker.getNbOfQuestionsInProgress());
        model.put("nbOfQuestionLeft", questionsProgressTracker.getNbOfQuestionsToAnswer());
    }

    public void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    public void setQuestionServices(QuestionServices questionServices) {
        this.questionServices = questionServices;
    }
}
