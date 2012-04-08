package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.forms.QuestionsProgressTracker;
import fr.soat.devoxx.game.framework.DevoxxUserBuilder;
import fr.soat.devoxx.game.model.DevoxxUser;
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

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        when(principal.getPrincipal()).thenReturn(DevoxxUserBuilder.newBuilder().build());
        
        final String result = controller.index(null,principal);

        assertEquals(TilesUtil.DFR_GAME_RULES_APPROVAL,result);
    }

    @Test
    public void whenRulesApprovedBuildQuestionTrackerAndRedirectToIndex() {
        final GameController controller = buildGameController();
        
        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                                .rulesApproved()
                                .approved()
                                .withName("test")
                                .build();
        when(principal.getPrincipal()).thenReturn(user);

        final Map model = new HashMap();
        final String result = controller.index(model, principal);

        assertIndexPageRedirectionOk(model, result);
    }

    @Test
    public void whenApproveRulesUpdateUserAndRedirectToIndex() {
        final GameController controller = buildGameController();

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .approved()
                .withName("test")
                .build();
        when(principal.getPrincipal()).thenReturn(user);

        final Map model = new HashMap();
        final String result = controller.approveRules(model, principal);

        assertIndexPageRedirectionOk(model, result);
    }
    
    @Test
    public void whenPlayAndNoMoreQuestionsRedirectToIndex() {
        final GameController controller = buildGameController();

        final OpenIDAuthenticationToken principal = mock(OpenIDAuthenticationToken.class);
        final DevoxxUser user = DevoxxUserBuilder.newBuilder()
                .rulesApproved()
                .approved()
                .withName("test")
                .build();
        when(principal.getPrincipal()).thenReturn(user);

        final QuestionsProgressTracker questionTracker = mock(QuestionsProgressTracker.class);
        when(questionTracker.nextQuestion()).thenThrow(new NoMoreQuestionException());
        
        final Map model = new HashMap();
        final String result = controller.play(questionTracker, model, principal);

        assertIndexPageRedirectionOk(model, result);
    }

    private GameController buildGameController() {
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
