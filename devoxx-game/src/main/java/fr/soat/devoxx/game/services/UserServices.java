package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.model.BundleUserQuestions;
import fr.soat.devoxx.game.model.DevoxxUser;

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
}
