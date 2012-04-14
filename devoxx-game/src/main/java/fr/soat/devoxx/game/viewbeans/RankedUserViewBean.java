package fr.soat.devoxx.game.viewbeans;

import fr.soat.devoxx.game.model.DevoxxUser;
import org.apache.commons.lang.StringUtils;

/**
 */
public class RankedUserViewBean {

    private static int GRAVATA_PIC_SIZE = 64;


    private String name;
    private String email;
    private long score;
    private long totalTime;
    private String avatarUrl;

    public RankedUserViewBean(final DevoxxUser player) {
        this.name = player.getUserForname();
        this.email = player.getUserEmail();
        this.score = player.getScore();
        this.totalTime = player.getTotalTime();
        this.avatarUrl = "http://www.gravatar.com/avatar/" + player.getMailHash() + "?d=mm&s=" + GRAVATA_PIC_SIZE;
    }

    public RankedUserViewBean() {
    }

    public String getName() {
        if(StringUtils.isEmpty(name)) {
            return email;
        }
        return name;
    }

    public long getScore() {
        return score;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
