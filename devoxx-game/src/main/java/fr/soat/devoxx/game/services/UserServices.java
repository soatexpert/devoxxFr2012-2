package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.model.BundleUserQuestions;
import fr.soat.devoxx.game.model.User;

public interface UserServices {

	public Iterable<User> getAllUsers();

	public void createUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(User user);

    public BundleUserQuestions getQuestionBundle();

	public int getPosition();
    
    public int nbOfUsers();

	public User getUser(Long userId);

	public User getUserByName(String username);
}
