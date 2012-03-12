package fr.soat.devoxx.game.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserQuestion {

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
}
