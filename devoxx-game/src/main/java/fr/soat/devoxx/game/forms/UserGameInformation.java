package fr.soat.devoxx.game.forms;

import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.UserQuestion;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mparisot
 * Date: 12/03/12
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public class UserGameInformation implements Serializable {

    private static final long serialVersionUID = -1918069635247552059L;

    private int currentRanking;
    private int nbOfPlayers;
    private List<UserQuestion> questionsInProgress;

    public UserGameInformation(int currentRanking, int nbOfPlayers, List<UserQuestion> questionsInProgress) {
        this.currentRanking = currentRanking;
        this.nbOfPlayers = nbOfPlayers;
        this.questionsInProgress = questionsInProgress;
    }

    public int getCurrentRanking() {
        return currentRanking;
    }

    public int getNbOfPlayers() {
        return nbOfPlayers;
    }

    public List<UserQuestion> getQuestionsInProgress() {
        return questionsInProgress;
    }
    
    public int getNbOfQuestionsToAnswer() {
        int nbOfQuestionsToAnswer = 0;
        for(UserQuestion question : questionsInProgress) {
            if(question.getReponse() == null)  {
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

    public Question nextQuestion() {
        for(UserQuestion userQuestion : questionsInProgress) {
            if(userQuestion.getReponse() == null) {
                return userQuestion.getQuestion();
            }
        }
        throw new NoMoreQuestionException();
    }
}
