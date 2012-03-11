package fr.soat.devoxx.game.model;

import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QuestionsPack {

	@Id
	Integer questionPackId;
	
	String questionPackName;

	boolean isActivePack;

	Long activeQuestion;

	LinkedList<Question> questions;
}
