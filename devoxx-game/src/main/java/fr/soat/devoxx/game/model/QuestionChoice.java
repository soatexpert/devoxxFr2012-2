package fr.soat.devoxx.game.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class QuestionChoice implements Serializable {

    private static final long serialVersionUID = 2477899775268524275L;

    @Id
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
}
