package fr.soat.devoxx.game.model;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION")
public class Question implements Serializable {

    private static final long serialVersionUID = 4593890730355225780L;

    @Id
	@Column(name = "ID")
	Long idQuestion;
	
	@Column(name = "LABEL")
	String questionLabel;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<QuestionChoice> choices;
	
	@OneToOne(cascade = CascadeType.ALL)
	QuestionChoice correctAnswer;

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

    @Transient
    public List<QuestionChoice> getShuffledChoices() {
        final List<QuestionChoice> shuffledChoices = Lists.newArrayList(choices);
        Collections.shuffle(shuffledChoices);
        return shuffledChoices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }

    public QuestionChoice getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(QuestionChoice correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionPackType getPack() {
        return pack;
    }

    public void setPack(QuestionPackType pack) {
        this.pack = pack;
    }
}
