package fr.soat.devoxx.game.forms;

import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.UserQuestion;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IntelliJ IDEA.
 * User: mparisot
 * Date: 13/03/12
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
public class UserGameInformationTest {

    private AtomicLong increment = new AtomicLong();

    @Test
    public void whenNoQuestionGetNbOfQuestionsToAnswerReturnZero() {
         UserGameInformation userGameInformation = new UserGameInformation(0,0, Collections.<UserQuestion>emptyList());

        Assert.assertEquals(0, userGameInformation.getNbOfQuestionsToAnswer());
    }

    @Test
    public void whenAllQuestionsNotAnsweredGetNbOfQuestionsToAnswerReturnListSize() {


        List<UserQuestion> userQuestions = createUserQuestions();
        UserGameInformation userGameInformation = new UserGameInformation(0,0, userQuestions);

        Assert.assertEquals(userQuestions.size(), userGameInformation.getNbOfQuestionsToAnswer());
    }

    @Test
    public void whenSomeQuestionsNotAnsweredGetNbOfQuestionsToAnswerReturnRightSize() {


        List<UserQuestion> userQuestions = createUserQuestions();
        userQuestions.get(0).setReponse(new QuestionChoice());
        UserGameInformation userGameInformation = new UserGameInformation(0,0, userQuestions);

        Assert.assertEquals(userQuestions.size()-1, userGameInformation.getNbOfQuestionsToAnswer());
    }

    private List<UserQuestion> createUserQuestions() {
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
