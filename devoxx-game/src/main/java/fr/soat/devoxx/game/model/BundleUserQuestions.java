package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class BundleUserQuestions implements Serializable {

    private static final long serialVersionUID = -7113228874677965883L;

    @Id
	Long id;

	@OneToMany
	public List<UserQuestion> userQuestions;

    @Enumerated(EnumType.STRING)
	public QuestionPackType questionPackType;

    public BundleUserQuestions(List<UserQuestion> userQuestions, QuestionPackType packType) {
        this.userQuestions = userQuestions;
        this.questionPackType = packType;
    }
}
