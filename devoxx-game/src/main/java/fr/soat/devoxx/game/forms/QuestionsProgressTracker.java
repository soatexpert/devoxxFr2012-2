package fr.soat.devoxx.game.forms;

import fr.soat.devoxx.game.exceptions.QuestionNotFoundException;
import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.model.UserQuestion;

import java.io.Serializable;
import java.util.List;

/**
 */
public class QuestionsProgressTracker implements Serializable {

    private static final long serialVersionUID = -1918069635247552059L;

    private List<UserQuestion> questionsInProgress;

    public QuestionsProgressTracker(List<UserQuestion> questionsInProgress) {
        this.questionsInProgress = questionsInProgress;
    }

    public int getNbOfQuestionsToAnswer() {
        int nbOfQuestionsToAnswer = 0;
        for(UserQuestion question : questionsInProgress) {
            if(question.getAnswer() == null)  {
                nbOfQuestionsToAnswer++;
            }
        }
        return nbOfQuestionsToAnswer;
    }
    
    public int getNbOfQuestionAnswered() {
        return getNbOfQuestionsInProgress() - getNbOfQuestionsToAnswer();
    }
    
    public int getNbOfQuestionsInProgress() {
        return questionsInProgress.size();
    }

    public UserQuestion nextQuestion() {
        for(UserQuestion userQuestion : questionsInProgress) {
            if(userQuestion.getAnswer() == null) {
                return userQuestion;
            }
        }
        throw new NoMoreQuestionException();
    }

    public UserQuestion findQuestionById(Long questionId) throws QuestionNotFoundException {
        for (UserQuestion userQuestion : questionsInProgress) {
            if (userQuestion.getQuestion().getIdQuestion().equals(questionId)) {
                return userQuestion;
            }
        }
        throw new QuestionNotFoundException();
    }
}
