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

    public UserQuestion(Question question) {
        this.question = question;
    }
}
