package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTION")
public class Question implements Serializable {

    private static final long serialVersionUID = -7601101753001835787L;

    @Id
	@Column(name = "id")
	Integer idQuestion;
	
	@Column(name = "LABEL")
	String questionLabel;

	@OneToMany
	List<QuestionChoice> choices;
	
	@OneToOne
	QuestionChoice goodChoice;

    public Integer getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestionLabel() {
        return questionLabel;
    }

    public void setQuestionLabel(String questionLabel) {
        this.questionLabel = questionLabel;
    }

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }

    public QuestionChoice getGoodChoice() {
        return goodChoice;
    }

    public void setGoodChoice(QuestionChoice goodChoice) {
        this.goodChoice = goodChoice;
    }
}
