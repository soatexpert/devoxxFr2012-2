package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QuestionsPack implements Serializable {

    private static final long serialVersionUID = -4114147958697061703L;

    @Id
	Integer questionPackId;
	
	String questionPackName;

	boolean isActivePack;

	Long activeQuestion;

	LinkedList<Question> questions;
}
