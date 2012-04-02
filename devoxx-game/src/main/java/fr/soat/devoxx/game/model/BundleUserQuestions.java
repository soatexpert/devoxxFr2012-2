package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
public class BundleUserQuestions implements Serializable {

    private static final long serialVersionUID = -7113228874677965883L;

    @Id
	Long id;

	@OneToMany
	private List<UserQuestion> userQuestions;
	
	@Transient
    private List<Question> remainingQuestions = new LinkedList<Question>();

	@OneToOne
	private QuestionsPack questionsPack;	
	
	@Enumerated(EnumType.STRING)
    public QuestionPackType questionPackType;
	
	@PostLoad
	protected void calculateRemainingQuestions() {
	    remainingQuestions = new LinkedList<Question>(questionsPack.getQuestions());
        for (UserQuestion question : userQuestions) {
            remainingQuestions.remove(question.getQuestion());
        }
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserQuestion> getUserQuestions() {
        return userQuestions;
    }

    public void setUserQuestions(List<UserQuestion> userQuestions) {
        this.userQuestions = userQuestions;
    }    

    public List<Question> getRemainingQuestions() {
        return remainingQuestions;
    }

    public void setRemainingQuestions(List<Question> remainingQuestions) {
        this.remainingQuestions = remainingQuestions;
    }

    public QuestionsPack getQuestionsPack() {
        return questionsPack;
    }

    public void setQuestionsPack(QuestionsPack questionsPack) {
        this.questionsPack = questionsPack;
    }    

    public QuestionPackType getQuestionPackType() {
        return questionPackType;
    }

    public void setQuestionPackType(QuestionPackType questionPackType) {
        this.questionPackType = questionPackType;
    }

    public BundleUserQuestions(List<UserQuestion> userQuestions, QuestionPackType packType) {
        this.userQuestions = userQuestions;
        this.questionPackType = packType;
    }
}
