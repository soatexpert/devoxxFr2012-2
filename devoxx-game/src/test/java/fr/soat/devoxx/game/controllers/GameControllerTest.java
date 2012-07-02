package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.exceptions.QuestionNotFoundException;
import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.forms.AnswerForm;
import fr.soat.devoxx.game.services.QuestionsProgressTracker;
import fr.soat.devoxx.game.framework.DevoxxUserBuilder;
import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.tools.TilesUtil;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 */
public class GameControllerTest {

    @Test
    public void whenRulesNotYetApprovedRedirectToApprobationScreen() {
        final GameController controller = new GameController();

        DevoxxUser devoxxUser = DevoxxUserBuilder.newBuilder().build();

        final UserServices userServices = mock(UserServices.class);
        when(userServices.getUser(any(Long.class))).thenReturn(devoxxUser);
        controller.setUserServices(userServices);

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(devoxxUser);
        
        final String result = controller.index(new HashMap(),principal);

        assertEquals(TilesUtil.DFR_GAME_RULES_APPROVAL,result);
    }

    @Test
    public void whenRulesApprovedBuildQuestionTrackerAndRedirectToIndex() {
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .rulesApproved()
                .approved()
                .withName("test")
                .build();
        final GameController controller = buildGameController(user);

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(user);

        final Map model = new HashMap();
        final String result = controller.index(model, principal);

        assertIndexPageRedirectionOk(model, result);
    }

    @Test
    public void whenApproveRulesUpdateUserAndRedirectToIndex() {
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .approved()
                .withName("test")
                .build();
        final GameController controller = buildGameController(user);

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(user);

        final Map model = new HashMap();
        final String result = controller.approveRules(model, principal);

        assertIndexPageRedirectionOk(model, result);
    }
    
    @Test
    public void whenPlayAndNoMoreQuestionsRedirectToIndex() {
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .rulesApproved()
                .approved()
                .withName("test")
                .build();
        final GameController controller = buildGameController(user);

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(user);

        final QuestionsProgressTracker questionTracker = mock(QuestionsProgressTracker.class);
        when(questionTracker.nextQuestion()).thenThrow(new NoMoreQuestionException());
        
        final Map model = new HashMap();
        final String result = controller.play(questionTracker, model, principal);

        assertIndexPageRedirectionOk(model, result);
    }
    
    @Test
    public void whenPlayWithQuestionsRemainingRedirectToPlayPageWithNextQuestion() {
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .rulesApproved()
                .approved()
                .withName("test")
                .build();
        final GameController controller = buildGameController(user);

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(user);

        final QuestionsProgressTracker questionTracker = mock(QuestionsProgressTracker.class);
        final UserQuestion userQuestion = new UserQuestion();
        final Question question = new Question();
        question.setIdQuestion(111l);
        userQuestion.setQuestion(question);
        when(questionTracker.nextQuestion()).thenReturn(userQuestion);

        final Map model = new HashMap();
        final String result = controller.play(questionTracker, model, principal);
        
        assertEquals(TilesUtil.DFR_GAME_PLAY_PAGE,result);
        
        final Question nextQuestion = (Question)model.get("question");
        assertEquals(question, nextQuestion);
        assertTrue(userQuestion.getStartQuestion() > 0);
        
        final AnswerForm form = (AnswerForm )model.get("answerForm");
        assertNotNull(form);
        assertEquals(111l,form.getQuestionId());

    }
    
    @Test
    public void whenNextAlreadyAnsweredRedirectToIndex() throws QuestionNotFoundException {
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .rulesApproved()
                .approved()
                .withName("test")
                .build();
        final GameController controller = buildGameController(user);

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(user);

        final QuestionsProgressTracker questionTracker = mock(QuestionsProgressTracker.class);
        final UserQuestion answeredQuestion = new UserQuestion();
        answeredQuestion.setAnswer(new QuestionChoice());
        when(questionTracker.findQuestionById(any(Long.class))).thenReturn(answeredQuestion);

        final Map model = new HashMap();
        final String result = controller.nextQuestion(questionTracker,0l,0l, model, principal);

        assertIndexPageRedirectionOk(model, result);
    }

    @Test
    public void whenNextInvalidQuestionRedirectToIndex() throws QuestionNotFoundException {
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .rulesApproved()
                .approved()
                .withName("test")
                .build();
        final GameController controller = buildGameController(user);

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(user);

        final QuestionsProgressTracker questionTracker = mock(QuestionsProgressTracker.class);
        when(questionTracker.findQuestionById(any(Long.class))).thenThrow(new QuestionNotFoundException());

        final Map model = new HashMap();
        final String result = controller.nextQuestion(questionTracker, 0l, 0l, model, principal);

        assertIndexPageRedirectionOk(model, result);
    }

    @Test
    public void whenNextQuestionRedirectToAnswerPage() throws QuestionNotFoundException {
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .rulesApproved()
                .approved()
                .withName("test")
                .build();
        final GameController controller = buildGameController(user);

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(user);

        final QuestionsProgressTracker questionTracker = mock(QuestionsProgressTracker.class);
        final UserQuestion expectedQuestion = new UserQuestion();
        final Question question = new Question();
        question.setIdQuestion(1l);
        final QuestionChoice answer = new QuestionChoice();
        answer.setQuestionChoiceId(1l);
        question.setCorrectAnswer(answer);
        expectedQuestion.setQuestion(question);
        when(questionTracker.findQuestionById(any(Long.class))).thenReturn(expectedQuestion);

        final Map model = new HashMap();
        final String result = controller.nextQuestion(questionTracker, 1l, 1l, model, principal);

        assertEquals(TilesUtil.DFR_GAME_ANSWER_PAGE,result);
    }

    private GameController buildGameController(DevoxxUser devoxxUser) {
        final GameController controller = new GameController();

        final UserServices userServices = mock(UserServices.class);
        when(userServices.nbOfUsers()).thenReturn(10l);
        when(userServices.getPosition(any(DevoxxUser.class))).thenReturn(1l);
        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                final DevoxxUser user = (DevoxxUser)invocationOnMock.getArguments()[0];
                user.setRulesApproved(true);
                return null;
            }
        }).when(userServices).approveRules(any(DevoxxUser.class));
        when(userServices.getUser(any(Long.class))).thenReturn(devoxxUser);
        controller.setUserServices(userServices);

        final QuestionServices questionServices = mock(QuestionServices.class);
        when(questionServices.getPendingQuestionsForUser(any(DevoxxUser.class))).thenReturn(Arrays.asList(new UserQuestion()));
        controller.setQuestionServices(questionServices);
        return controller;
    }

    private void assertIndexPageRedirectionOk(Map model, String result) {
        assertEquals(TilesUtil.DFR_GAME_INDEX_PAGE, result);
        assertNotNull(model.get("questionsProgressTracker"));
        assertTrue((Boolean)model.get("approved"));
        assertEquals("test",model.get("username"));
        assertEquals(10l, model.get("nbUsers"));
        assertEquals(1l, model.get("rank"));
        assertEquals(1, model.get("waitingQuestions"));
    }
}
