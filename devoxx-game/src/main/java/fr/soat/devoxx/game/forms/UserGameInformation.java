package fr.soat.devoxx.game.forms;

import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.model.UserQuestion;

import java.io.Serializable;
import java.util.List;

/**
 */
public class UserGameInformation implements Serializable {

    private static final long serialVersionUID = -1918069635247552059L;

    private long currentRanking;
    private long nbOfPlayers;
    private List<UserQuestion> questionsInProgress;

    public UserGameInformation(long currentRanking, long nbOfPlayers, List<UserQuestion> questionsInProgress) {
        this.currentRanking = currentRanking;
        this.nbOfPlayers = nbOfPlayers;
        this.questionsInProgress = questionsInProgress;
    }

    public long getCurrentRanking() {
        return currentRanking;
    }

    public long getNbOfPlayers() {
        return nbOfPlayers;
    }

    public List<UserQuestion> getQuestionsInProgress() {
        return questionsInProgress;
    }
    
    public int getNbOfQuestionsToAnswer() {
        int nbOfQuestionsToAnswer = 0;
        for(UserQuestion question : questionsInProgress) {
            if(question.getResponse() == null)  {
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
            if(userQuestion.getResponse() == null) {
                return userQuestion;
            }
        }
        throw new NoMoreQuestionException();
    }
}
