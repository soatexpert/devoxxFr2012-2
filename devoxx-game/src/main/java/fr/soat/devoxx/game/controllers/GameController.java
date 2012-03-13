package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.forms.AnswerForm;
import fr.soat.devoxx.game.forms.UserGameInformation;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.security.OpenIdUserDetails;
import fr.soat.devoxx.game.tools.TilesUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping(value = "/game")
@SessionAttributes("userGameInfos")
public class GameController {

    private AtomicLong increment = new AtomicLong();

    @RequestMapping(value = {"/", "/index", ""})
    public String index(Model model) {
        final OpenIdUserDetails currentUser = (OpenIdUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserGameInformation userGameInformation  = new UserGameInformation(10,100,getCurrentUserPendingQuestions());
        model.addAttribute("userGameInfos",userGameInformation);

        model.addAttribute("userName",currentUser.getUser().getUserForname());
        model.addAttribute("rank",userGameInformation.getCurrentRanking());
        model.addAttribute("nbUsers",userGameInformation.getNbOfPlayers());
        model.addAttribute("waitingQuestions",userGameInformation.getNbOfQuestionsToAnswer());


        return TilesUtil.DFR_GAME_INDEX_PAGE;
    }
    
    @RequestMapping("/play")
    public String play(@ModelAttribute("userGameInfos")UserGameInformation userGameInformation, Model model) {

        try {
            UserQuestion nextQuestion = userGameInformation.nextQuestion();

            nextQuestion.setStartQuestion(System.currentTimeMillis());

            model.addAttribute("answerForm",new AnswerForm(nextQuestion.getQuestion().getIdQuestion()));
            model.addAttribute("question", nextQuestion.getQuestion());
            model.addAttribute("nbOfQuestionsAnswered",userGameInformation.getNbOfQuestionAnswered());
            model.addAttribute("nbOfQuestionsTotal",userGameInformation.getNbOfQuestionsInProgress());
            model.addAttribute("nbOfQuestionLeft",userGameInformation.getNbOfQuestionsToAnswer());
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
       answerQuestion(questionId, answer, userGameInformation);

       return play(userGameInformation,model);
    }

    private void answerQuestion(Long questionId, Long answer, UserGameInformation userGameInformation) {
       for (UserQuestion userQuestion : userGameInformation.getQuestionsInProgress()) {
            if(userQuestion.getQuestion().getIdQuestion().equals(questionId))  {
                for(QuestionChoice choice : userQuestion.getQuestion().getChoices()) {
                    if(choice.getQuestionChoiceId().equals(answer)) {
                        userQuestion.setReponse(choice);
                        userQuestion.setEndQuestion(System.currentTimeMillis());
                        //TODO save(userQuestion);
                    }
                }
            }
        }
    }

    private List<UserQuestion> getCurrentUserPendingQuestions() {
        List<UserQuestion> currentUserPendingQuestions = new ArrayList<UserQuestion>();

        UserQuestion pendingQuestion1 = new UserQuestion();
        pendingQuestion1.setQuestion(createQuestion("Quel est le nom de l'évènement auquel vous participez ?","Devoxx","JavaOne","TechDays","Solidays"));
        currentUserPendingQuestions.add(pendingQuestion1);


        UserQuestion pendingQuestion2 = new UserQuestion();
        pendingQuestion2.setQuestion(createQuestion("Quelle est la reponse à l'univers, la vie et tout ça ?", "42", "Dieu", "joker", "ObiWanKenobi"));
        currentUserPendingQuestions.add(pendingQuestion2);

        return currentUserPendingQuestions;
    }

    private Question createQuestion(String questionLabel,String... answers) {
        Question question = new Question();
        question.setIdQuestion(increment.incrementAndGet());
        question.setQuestionLabel(questionLabel);
        List<QuestionChoice> questionAnswers = new ArrayList<QuestionChoice>();

        for(String answer : answers) {
            QuestionChoice choice1 = new QuestionChoice();
            choice1.setQuestionChoiceId(increment.incrementAndGet());
            choice1.setChoiceLabel(answer);
            questionAnswers.add(choice1);
        }

        question.setGoodChoice(questionAnswers.get(0));

        question.setChoices(questionAnswers);

        return question;
    }
}
