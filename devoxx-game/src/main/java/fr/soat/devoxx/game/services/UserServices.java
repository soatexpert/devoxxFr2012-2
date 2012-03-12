package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.model.BundleUserQuestions;
import fr.soat.devoxx.game.model.User;

import java.util.List;

public interface UserServices {

	public List<User> getUsers();

	public void createUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(User user);

    public BundleUserQuestions getQuestionBundle();

	public int getPosition();
    
    public int nbOfUsers();

	public User getUser(long userId);

	public User getUserByName(String username);
}
