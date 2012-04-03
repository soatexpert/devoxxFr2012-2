package fr.soat.devoxx.game.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "USER_QUESTION")
public class UserQuestion implements Serializable {

    private static final long serialVersionUID = 4058247641469267996L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
	Long id;

	@OneToOne
	Question question;

	long startQuestion;

	long endQuestion;
    
    int hourOfappearence;

	@OneToOne
	QuestionChoice response;

    @ManyToOne
    DevoxxUser player;

    public UserQuestion(Question question, DevoxxUser user, int hourOfAppearence) {
        this.question = question;
        this.player = user;
        this.hourOfappearence = hourOfAppearence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public long getStartQuestion() {
        return startQuestion;
    }

    public void setStartQuestion(long startQuestion) {
        this.startQuestion = startQuestion;
    }

    public long getEndQuestion() {
        return endQuestion;
    }

    public void setEndQuestion(long endQuestion) {
        this.endQuestion = endQuestion;
    }

    public QuestionChoice getResponse() {
        return response;
    }

    public void setResponse(QuestionChoice response) {
        this.response = response;
    }

    public int getAnsweringTimeInSeconds() {
        return Math.round(getAnsweringTimeInMs() / 1000);
    }

    private long getAnsweringTimeInMs() {
        return endQuestion - startQuestion;
    }

    public boolean isAnswerCorrect() {
        return getQuestion().getGoodChoice().equals(getResponse());
    }

    public QuestionChoice getCorrectAnswer() {
        return getQuestion().getGoodChoice();
    }

    public UserQuestion() {
        super();
    }

    public DevoxxUser getPlayer() {
        return player;
    }

    public void setPlayer(DevoxxUser player) {
        this.player = player;
    }

    public int getHourOfappearence() {
        return hourOfappearence;
    }

    public void setHourOfappearence(int hourOfappearence) {
        this.hourOfappearence = hourOfappearence;
    }
}
