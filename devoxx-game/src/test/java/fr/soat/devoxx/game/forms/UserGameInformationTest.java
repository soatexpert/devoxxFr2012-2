package fr.soat.devoxx.game.forms;

import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
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
    public void when_NoQuestion_GetNbOfQuestionsToAnswer_ReturnZero() {
        UserGameInformation userGameInformation = buildUserGameInformation(Collections.<UserQuestion>emptyList());
        Assert.assertEquals(0, userGameInformation.getNbOfQuestionsToAnswer());
    }

    @Test
    public void when_AllQuestionsNotAnswered_GetNbOfQuestionsToAnswer_ReturnListSize() {
        List<UserQuestion> userQuestions = createUserQuestions(2,false,false);
        UserGameInformation userGameInformation = buildUserGameInformation(userQuestions);

        Assert.assertEquals(userQuestions.size(), userGameInformation.getNbOfQuestionsToAnswer());
    }

    @Test
    public void when_SomeQuestionsNotAnswered_GetNbOfQuestionsToAnswer_ReturnRightSize() {
        List<UserQuestion> userQuestions = createUserQuestions(2,true,false);
        UserGameInformation userGameInformation = buildUserGameInformation(userQuestions);

        Assert.assertEquals(userQuestions.size()-1, userGameInformation.getNbOfQuestionsToAnswer());
    }

    @Test(expected = NoMoreQuestionException.class)
    public void when_want_NextQuestion_with_EmptyList_then_throwException() {
        UserGameInformation userGameInformation = buildUserGameInformation(Collections.<UserQuestion>emptyList());

        userGameInformation.nextQuestion();
    }
    
    @Test
    public void when_want_NextQuestion_with_FirstUnanswered_then_returnFirstQuestion() {
        List<UserQuestion> userQuestions = createUserQuestions(2, false, false);
        UserGameInformation userGameInformation = buildUserGameInformation(userQuestions);

        UserQuestion userQuestion = userGameInformation.nextQuestion();
        
        Assert.assertEquals(userQuestions.get(0),userQuestion);
    }

    @Test
    public void when_want_NextQuestion_with_FirstAnswered_then_returnSecondQuestion() {
        List<UserQuestion> userQuestions = createUserQuestions(2, true, false);
        UserGameInformation userGameInformation = buildUserGameInformation(userQuestions);

        UserQuestion userQuestion = userGameInformation.nextQuestion();

        Assert.assertEquals(userQuestions.get(1),userQuestion);
    }



    private UserGameInformation buildUserGameInformation(List<UserQuestion> userQuestions) {
        return new UserGameInformation(0,0, userQuestions);
    }

    private List<UserQuestion> createUserQuestions(final int nbOfQuestions, boolean... answered) {
        List<UserQuestion> currentUserPendingQuestions = new ArrayList<UserQuestion>();

        for(int currentQuestionIdx = 0; currentQuestionIdx < nbOfQuestions; currentQuestionIdx++) {
            UserQuestion pendingQuestion = new UserQuestion();
            pendingQuestion.setQuestion(createQuestion("Question " + currentQuestionIdx, "rep1", "rep2", "rep3", "rep4"));
            if(answered[currentQuestionIdx]) {
                pendingQuestion.setResponse(new QuestionChoice());
            }
            currentUserPendingQuestions.add(pendingQuestion);
        }

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
