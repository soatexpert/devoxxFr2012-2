package fr.soat.devoxx.game.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTION_CHOICE")
public class QuestionChoice implements Serializable {

    private static final long serialVersionUID = 2477899775268524275L;

    @Id
    @Column(name = "ID")
	Long questionChoiceId;

    @Column(name = "CHOICE_LABEL")
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
