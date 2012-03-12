package fr.soat.devoxx.game.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QuestionChoice {

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
