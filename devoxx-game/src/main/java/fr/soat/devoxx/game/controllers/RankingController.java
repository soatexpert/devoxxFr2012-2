package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.tools.TilesUtil;
import fr.soat.devoxx.game.viewbeans.RankedUserViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Controller
@RequestMapping(value = "/ranking")
@SessionAttributes("userGameInfos")
public class RankingController {

    @Autowired
    private UserServices userServices;

    @RequestMapping("")
    public String ranking(Model model) {

        model.addAttribute("players", adaptTop10(userServices.getPlayersTop10()));

        return TilesUtil.DFR_GAME_RANKING_PAGE;
    }

    @RequestMapping(value="/update", headers="Accept=*/*", method= RequestMethod.GET)
    public @ResponseBody
    List<RankedUserViewBean> updateRanking(Model model) {
        return adaptTop10(userServices.getPlayersTop10());
    }

    private List<RankedUserViewBean> adaptTop10(List<DevoxxUser> playersTop10) {
        List<RankedUserViewBean> players = new ArrayList<RankedUserViewBean>();

        for(DevoxxUser currentUser : playersTop10) {
            players.add(new RankedUserViewBean(currentUser));
        }

        populateTop10ToHaveAlways10Players(players);

        return players;
    }

    private void populateTop10ToHaveAlways10Players(List<RankedUserViewBean> players) {
        while(players.size() < 10) {
            players.add(new RankedUserViewBean());
        }
    }

}
