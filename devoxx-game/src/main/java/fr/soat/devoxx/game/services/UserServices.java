package fr.soat.devoxx.game.services;

import java.util.List;

import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.User;

public interface UserServices {

	public List<User> getUsers();

	public void createUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(User user);
	
	public List<Question> getQuestionList();
	
	public int getPosition();

	public User getUser(long userId);

	public List<Question> geQuestions();

	public User getUserByName(String username);

}
