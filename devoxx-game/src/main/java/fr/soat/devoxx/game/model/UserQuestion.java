package fr.soat.devoxx.game.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity

public class UserQuestion implements Serializable {

    private static final long serialVersionUID = 4058247641469267996L;

    @Id
	Long id;

	@OneToOne
	Question question;

	long startQuestion;

	long endQuestion;

	@OneToOne
	QuestionChoice reponse;

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

    public QuestionChoice getReponse() {
        return reponse;
    }

    public void setReponse(QuestionChoice reponse) {
        this.reponse = reponse;
    }

    public int getAnsweringTimeInSeconds() {
        return Math.round(getAnsweringTimeInMs() / 1000);
    }

    private long getAnsweringTimeInMs() {
        return endQuestion - startQuestion;
    }

    public boolean isAnswerCorrect() {
        return getQuestion().getGoodChoice().equals(getReponse());
    }

    public QuestionChoice getCorrectAnswer() {
        return getQuestion().getGoodChoice();
    }
}
