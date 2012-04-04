package fr.soat.devoxx.game.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_SCORE")
public class UserScore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long scoreId;

    @Column(name="SCORE")
    long score = 0;

    @Column(name="TOTAL_TIME")
    long totalTime = 0;

    public UserScore() {
    }

    public long getScoreId() {
        return scoreId;
    }

    public void setScoreId(long scoreId) {
        this.scoreId = scoreId;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public void addToScore(long points) {
        score += points;
    }

    public void addToTotalTime(long timeToAdd) {
        totalTime += timeToAdd;
    }
}