package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.exceptions.QuestionNotFoundException;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionsProgressTracker;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 */
public class QuestionProgressTrackerTest {

    @Test
    public void when_NoQuestion_GetNbOfQuestionsToAnswer_ReturnZero() {
        QuestionsProgressTracker questionsProgressTracker = buildUserGameInformation(Collections.<UserQuestion>emptyList());
        Assert.assertEquals(0, questionsProgressTracker.getNbOfQuestionsToAnswer());
    }

    @Test
    public void when_AllQuestionsNotAnswered_GetNbOfQuestionsToAnswer_ReturnListSize() {
        List<UserQuestion> userQuestions = createUserQuestions(2,false,false);
        QuestionsProgressTracker questionsProgressTracker = buildUserGameInformation(userQuestions);

        Assert.assertEquals(userQuestions.size(), questionsProgressTracker.getNbOfQuestionsToAnswer());
    }

    @Test
    public void when_SomeQuestionsNotAnswered_GetNbOfQuestionsToAnswer_ReturnRightSize() {
        List<UserQuestion> userQuestions = createUserQuestions(2,true,false);
        QuestionsProgressTracker questionsProgressTracker = buildUserGameInformation(userQuestions);

        Assert.assertEquals(userQuestions.size()-1, questionsProgressTracker.getNbOfQuestionsToAnswer());
    }

    @Test(expected = NoMoreQuestionException.class)
    public void when_want_NextQuestion_with_EmptyList_then_throwException() {
        QuestionsProgressTracker questionsProgressTracker = buildUserGameInformation(Collections.<UserQuestion>emptyList());

        questionsProgressTracker.nextQuestion();
    }
    
    @Test
    public void when_want_NextQuestion_with_FirstUnanswered_then_returnFirstQuestion() {
        List<UserQuestion> userQuestions = createUserQuestions(2, false, false);
        QuestionsProgressTracker questionsProgressTracker = buildUserGameInformation(userQuestions);

        UserQuestion userQuestion = questionsProgressTracker.nextQuestion();
        
        Assert.assertEquals(userQuestions.get(0),userQuestion);
    }

    @Test
    public void when_want_NextQuestion_with_FirstAnswered_then_returnSecondQuestion() {
        List<UserQuestion> userQuestions = createUserQuestions(2, true, false);
        final QuestionsProgressTracker questionsProgressTracker = buildUserGameInformation(userQuestions);

        final UserQuestion userQuestion = questionsProgressTracker.nextQuestion();

        Assert.assertEquals(userQuestions.get(1),userQuestion);
    }

    @Test(expected= QuestionNotFoundException.class)
    public void when_search_a_question_unknown_throw_exception() throws QuestionNotFoundException {
        List<UserQuestion> userQuestions = createUserQuestions(2, true, false);
        final QuestionsProgressTracker questionsProgressTracker = buildUserGameInformation(userQuestions);

        questionsProgressTracker.findQuestionById(100l);
    }

    @Test
    public void when_search_an_existing_question() throws QuestionNotFoundException {
        List<UserQuestion> userQuestions = createUserQuestions(2, true, false);
        final QuestionsProgressTracker questionsProgressTracker = buildUserGameInformation(userQuestions);

        final UserQuestion question = questionsProgressTracker.findQuestionById(1l);
        Assert.assertEquals(1l,question.getId().longValue());
    }

    private QuestionsProgressTracker buildUserGameInformation(List<UserQuestion> userQuestions) {
        return new QuestionsProgressTracker(userQuestions);
    }

    private List<UserQuestion> createUserQuestions(final int nbOfQuestions, boolean... answered) {
        final List<UserQuestion> currentUserPendingQuestions = new ArrayList<UserQuestion>();

        for(int currentQuestionIdx = 0; currentQuestionIdx < nbOfQuestions; currentQuestionIdx++) {
            UserQuestion pendingQuestion = new UserQuestion();
            pendingQuestion.setId(currentQuestionIdx+1l);
            pendingQuestion.setQuestion(createQuestion("Question " + currentQuestionIdx, "rep1", "rep2", "rep3", "rep4"));
            if(answered[currentQuestionIdx]) {
                pendingQuestion.setAnswer(new QuestionChoice());
            }
            currentUserPendingQuestions.add(pendingQuestion);
        }

        return currentUserPendingQuestions;
    }

    private AtomicLong increment = new AtomicLong();
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

        question.setCorrectAnswer(questionAnswers.get(0));

        question.setChoices(questionAnswers);

        return question;
    }
}
