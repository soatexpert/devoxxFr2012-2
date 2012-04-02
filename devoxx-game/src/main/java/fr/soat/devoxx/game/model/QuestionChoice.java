package fr.soat.devoxx.game.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class QuestionChoice implements Serializable {

    private static final long serialVersionUID = 2477899775268524275L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	Long questionChoiceId;

	String choiceLabel;

    public Long getQuestionChoiceId() {
        return questionChoiceId;
    }

    public void setQuestionChoiceId(Long questionChoiceId) {
        this.questionChoiceId = questionChoiceId;
    }

    public String getChoiceLabel() {
        return choiceLabel;
    }

    public void setChoiceLabel(String choiceLabel) {
        this.choiceLabel = choiceLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionChoice that = (QuestionChoice) o;

        if (!questionChoiceId.equals(that.questionChoiceId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return questionChoiceId.hashCode();
    }
}
