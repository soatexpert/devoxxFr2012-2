package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class BundleUserQuestions implements Serializable {

    private static final long serialVersionUID = -7113228874677965883L;

    @Id
	Long id;

	@OneToMany
	public List<UserQuestion> userQuestions;

	@OneToOne
	public QuestionsPack questionsPack;

    public BundleUserQuestions(List<UserQuestion> userQuestions, QuestionsPack questionsPack) {
        this.userQuestions = userQuestions;
        this.questionsPack = questionsPack;
    }
}
