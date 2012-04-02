package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION")
public class Question implements Serializable {

    private static final long serialVersionUID = 4593890730355225780L;

    @Id
	@Column(name = "id")
	Long idQuestion;
	
	@Column(name = "LABEL")
	String questionLabel;

	@OneToMany(cascade = CascadeType.ALL)
	List<QuestionChoice> choices;
	
	@OneToOne(cascade = CascadeType.ALL)
	QuestionChoice goodChoice;

    @Enumerated(EnumType.STRING)
    QuestionPackType pack;

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
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

    public QuestionPackType getPack() {
        return pack;
    }

    public void setPack(QuestionPackType pack) {
        this.pack = pack;
    }
}
