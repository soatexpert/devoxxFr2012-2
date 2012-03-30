package fr.soat.devoxx.game.model;

/**
 * Created by IntelliJ IDEA.
 * User: mparisot
 * Date: 30/03/12
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class RankedUser {
    private DevoxxUser user;
    
    private int score;
    private int timeInSeconds;

    public RankedUser(DevoxxUser user, int score, int timeInSeconds) {
        this.user = user;
        this.score = score;
        this.timeInSeconds = timeInSeconds;
    }
    
    public String getMailHash() {
        return user.getMailHash();
    }
    
    public String getName() {
        return user.getUserForname();
    }
    
    public int getScore() {
        return score;
    }
    
    public int getTime() {
        return timeInSeconds;
    }
}
