package fr.soat.devoxx.game.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTION")
public class Question {

	@Id
	@Column(name = "id")
	Integer idQuestion;
	
	@Column(name = "LABEL")
	String questionLabel;

	@OneToMany
	List<QuestionChoice> choices;
	
	@OneToOne
	QuestionChoice goodChoice;

}
