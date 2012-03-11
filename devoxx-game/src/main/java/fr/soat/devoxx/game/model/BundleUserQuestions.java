package fr.soat.devoxx.game.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class BundleUserQuestions {

	@Id
	Long id;

	@OneToMany
	public List<UserQuestion> userQuestions;

	@OneToOne
	public QuestionsPack questionsPack;
}
