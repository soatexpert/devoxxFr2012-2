package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.dto.DevoxxUserDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
@SessionAttributes("userGameInfos")
public class GameController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private QuestionServices questionServices;

    @RequestMapping(value = {"/", "/home","/index", ""})
    public String index(Model model) {
        final DevoxxUser currentUser = (DevoxxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!currentUser.isReglementAccepted()) {
            return TilesUtil.DFR_GAME_RULES_APPROVAL;
        }


        UserGameInformation userGameInformation  = new UserGameInformation(
                userServices.getPosition(currentUser),
                userServices.nbOfUsers(),
                questionServices.getPendingQuestionsForUser(currentUser));
        model.addAttribute("userGameInfos",userGameInformation);

        model.addAttribute("approuved",currentUser.isEnabled());
        model.addAttribute("username",currentUser.getUserForname());

        model.addAttribute("rank",userGameInformation.getCurrentRanking());
        model.addAttribute("nbUsers",userGameInformation.getNbOfPlayers());
        model.addAttribute("waitingQuestions",userGameInformation.getNbOfQuestionsToAnswer());


        return TilesUtil.DFR_GAME_INDEX_PAGE;
    }

    @RequestMapping("/approveRules")
    public String approveRules(Model model) {
        final DevoxxUser currentUser = (DevoxxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userServices.approveRules(currentUser);
        
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
            final DevoxxUser currentUser = (DevoxxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            addUserInformationToModel(userGameInformation, model);

            UserQuestion question = answerQuestion(questionId, answer, userGameInformation, currentUser);

            updatePlayerScore(answer, currentUser, question);

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

    private void updatePlayerScore(Long answer, DevoxxUser currentUser, UserQuestion question) {
        if(answer.equals(question.getCorrectAnswer().getQuestionChoiceId())) {
            currentUser.addToScore(1);
        }
        currentUser.addToTime(question.getAnsweringTimeInSeconds());
        userServices.updateUser(currentUser);
    }

    @RequestMapping(value = "/pause")
    public String pause(@ModelAttribute("userGameInfos") UserGameInformation userGameInformation,
                        Model model) {
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

    @RequestMapping("/ranking")
    public String ranking(Model model) {
        
        model.addAttribute("players", userServices.getPlayersTop10());
        
        return TilesUtil.DFR_GAME_RANKING_PAGE;
    }

    @RequestMapping(value="/updateRanking", headers="Accept=*/*", method=RequestMethod.GET)
    public @ResponseBody List<DevoxxUserDto> updateRanking(Model model) {
        List<DevoxxUserDto> usersDtoList = new ArrayList<DevoxxUserDto>();
        DevoxxUserDto userDto;
        for (DevoxxUser devoxxUser : userServices.getPlayersTop10()) {
            userDto = new DevoxxUserDto();            
            userDto.setUserId(devoxxUser.getUserId());
            userDto.setUserEmail(devoxxUser.getUserEmail());
            userDto.setMailHash(devoxxUser.getMailHash());
            userDto.setUserForname(devoxxUser.getUserForname());
            userDto.setEnabled(devoxxUser.isEnabled());
            userDto.setScore(devoxxUser.getScore());
            userDto.setTotalTime(devoxxUser.getTotalTime());
            usersDtoList.add(userDto);
        }        
        return usersDtoList;
    }

    private void addUserInformationToModel(UserGameInformation userGameInformation, Model model) {
        model.addAttribute("nbOfQuestionsAnswered",userGameInformation.getNbOfQuestionAnswered());
        model.addAttribute("nbOfQuestionsTotal",userGameInformation.getNbOfQuestionsInProgress());
        model.addAttribute("nbOfQuestionLeft",userGameInformation.getNbOfQuestionsToAnswer());
    }

    private UserQuestion answerQuestion(Long questionId, Long answer, UserGameInformation userGameInformation, DevoxxUser currentUser) throws InvalidQuestionException {
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
