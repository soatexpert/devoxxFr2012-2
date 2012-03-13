package fr.soat.devoxx.game.forms;

/**
 * Created by IntelliJ IDEA.
 * User: mparisot
 * Date: 13/03/12
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public class AnswerForm {

    private long questionId;
    private long answer;

    public AnswerForm(long questionId) {
        this.questionId = questionId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswer() {
        return answer;
    }

    public void setAnswer(long answer) {
        this.answer = answer;
    }
}
