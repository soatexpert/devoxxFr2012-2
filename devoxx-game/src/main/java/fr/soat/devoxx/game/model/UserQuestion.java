package fr.soat.devoxx.game.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserQuestion implements Serializable {

    private static final long serialVersionUID = 4912747959890426136L;

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
