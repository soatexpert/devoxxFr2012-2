package fr.soat.devoxx.game.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QuestionChoice {

	@Id
	Long questionChoiceId;

	String choiceLabel;

}
