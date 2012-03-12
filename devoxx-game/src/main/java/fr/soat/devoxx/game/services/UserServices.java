package fr.soat.devoxx.game.services;

import java.util.List;

<<<<<<< HEAD
=======
import fr.soat.devoxx.game.model.BundleUserQuestions;
import org.springframework.security.core.userdetails.UserDetails;

>>>>>>> integration des ecrans pour le jeu sur mobile
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.User;

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
