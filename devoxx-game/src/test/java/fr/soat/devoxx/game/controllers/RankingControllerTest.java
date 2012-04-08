package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.tools.TilesUtil;
import fr.soat.devoxx.game.viewbeans.RankedUserViewBean;
import static org.junit.Assert.*;

import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class RankingControllerTest {

    @Test
    public void whenCallRankingModelHasRankingInfosAndRedirectToRanking() {
        final RankingController controller = buildRankingController();

        final Map model = new HashMap();
        final String redirection = controller.ranking(model);

        List<RankedUserViewBean> players = (List < RankedUserViewBean >)model.get("players");
        assertNotNull(players);
        assertEquals(10,players.size());
        assertEquals(TilesUtil.DFR_GAME_RANKING_PAGE,redirection);
    }

    @Test
    public void whenCallUpdateRankingReturn10RankedUsers() {
        final RankingController controller = buildRankingController();
        
        final List<RankedUserViewBean> result = controller.updateRanking();

        assertNotNull(result);
        assertEquals(10,result.size());
    }

    private RankingController buildRankingController() {
        final RankingController controller = new RankingController();

        final UserServices userServices = mock(UserServices.class);
        controller.setUserServices(userServices);
        return controller;
    }
}
