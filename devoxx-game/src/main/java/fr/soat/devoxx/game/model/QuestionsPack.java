package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTIONS_PACK")
public class QuestionsPack implements Serializable {

    private static final long serialVersionUID = -4114147958697061703L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
	private Integer questionPackId;
	
    @Column(name = "PACK_NAME")
    private String questionPackName;

    @Column(name = "ACTIVE_PACK")
    private boolean activePack;

    /*@Column(name = "ACTIVE_QUESTION_ID")
	Long activeQuestion;*/

	@OneToMany
	private List<Question> questions = new LinkedList<Question>();

    public Integer getQuestionPackId() {
        return questionPackId;
    }

    public void setQuestionPackId(Integer questionPackId) {
        this.questionPackId = questionPackId;
    }

    public String getQuestionPackName() {
        return questionPackName;
    }

    public void setQuestionPackName(String questionPackName) {
        this.questionPackName = questionPackName;
    }

    public boolean isActivePack() {
        return activePack;
    }

    public void setActivePack(boolean isActivePack) {
        this.activePack = isActivePack;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }	
}
