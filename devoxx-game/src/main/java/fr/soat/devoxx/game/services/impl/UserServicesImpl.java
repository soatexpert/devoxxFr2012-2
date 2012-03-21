package fr.soat.devoxx.game.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.soat.devoxx.game.model.BundleUserQuestions;
import fr.soat.devoxx.game.model.User;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.services.repository.UserRepository;

@Repository
public class UserServicesImpl implements UserServices  {

	@Autowired
	UserRepository userRepo;

	@Override
	public User getUser(Long userId) {
		return userRepo.findOne(userId);
	}

	@Override
	public void createUser(User user) {
	    userRepo.save(user);
	}
	
	@Override
    public void updateUser(User user) {
	    userRepo.save(user);
    }

    @Override
	public void deleteUser(User user) {
	    userRepo.delete(user);
	}

	@Override
    public BundleUserQuestions getQuestionBundle() {
        List<UserQuestion> questions = new ArrayList<UserQuestion>();

        questions.add(new UserQuestion());

        BundleUserQuestions bundle = new BundleUserQuestions(
                questions,
                null
        );
        return bundle;
    }

    public int getPosition() {
		return 10;
	}

    public int nbOfUsers() {
        return 100;
    }
    
    @Override
    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }    

    @Override
	public User getUserByName(String username) {
		return userRepo.findUserByName(username);
	}    
}
