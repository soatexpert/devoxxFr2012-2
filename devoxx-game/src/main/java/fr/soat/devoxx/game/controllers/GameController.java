package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.security.OpenIdUserDetails;
import fr.soat.devoxx.game.tools.TilesUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/game")
public class GameController {

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {
        final OpenIdUserDetails currentUser = (OpenIdUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("userName","toto");
        model.addAttribute("rank",10);
        model.addAttribute("nbUsers",100);
        model.addAttribute("waitingQuestions",3);


        return TilesUtil.DFR_GAME_INDEX_PAGE;
    }
    
    @RequestMapping("/play")
    public String play(Model model) {

        Question question = new Question();
        question.setIdQuestion(1234);
        question.setQuestionLabel("Quel est le nom de l'évènement auquel vous participez ?");
        List<QuestionChoice> answers = new ArrayList<QuestionChoice>();
        QuestionChoice choice1 = new QuestionChoice();
        choice1.setQuestionChoiceId(1231l);
        choice1.setChoiceLabel("Devoxx");
        answers.add(choice1);

        QuestionChoice choice2 = new QuestionChoice();
        choice2.setQuestionChoiceId(1232l);
        choice2.setChoiceLabel("JavaOne");
        answers.add(choice2);

        QuestionChoice choice3 = new QuestionChoice();
        choice3.setQuestionChoiceId(1233l);
        choice3.setChoiceLabel("Techdays");
        answers.add(choice3);

        QuestionChoice choice4 = new QuestionChoice();
        choice4.setQuestionChoiceId(1234l);
        choice4.setChoiceLabel("Solidays");
        answers.add(choice4);

        question.setChoices(answers);

        model.addAttribute("question",question);

        return TilesUtil.DFR_GAME_PLAY_PAGE;
    }

    @RequestMapping(value = "/next", method = RequestMethod.POST)
    public String nextQuestion() {
        return TilesUtil.DFR_GAME_PLAY_PAGE;
    }
}
