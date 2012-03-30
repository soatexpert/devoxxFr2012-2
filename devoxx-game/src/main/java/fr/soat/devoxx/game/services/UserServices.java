package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.model.BundleUserQuestions;
import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.RankedUser;
import fr.soat.devoxx.game.model.UserQuestion;

import java.util.List;

public interface UserServices {

	public Iterable<DevoxxUser> getAllUsers();

	public void createUser(DevoxxUser user);
	
	public void updateUser(DevoxxUser user);
	
	public void deleteUser(DevoxxUser user);

    public BundleUserQuestions getQuestionBundle();

	public int getPosition();
    
    public int nbOfUsers();

	public DevoxxUser getUser(Long userId);

	public DevoxxUser getUserByName(String username);

    void approveRules(DevoxxUser user);

    /**
     * Retourne la liste des questions non répondues dans le BundleUserQuestions de l'utilisateur passé en paramètre.
     * Ne pas oublié que le bundle est dépendant du questionPack, celui va etre passé en param au demarrage ? il est commun à toute l'application
     * @param user
     * @return
     */
    List<UserQuestion> getPendingQuestionsForUser(DevoxxUser user);

    /**
     * Retourne la liste du top 10 des utilisateurs avec leurs scores et leur temps total de reponse en secondes
     * @return
     */
    List<RankedUser> getPlayersTop10();
}
