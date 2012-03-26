package fr.soat.devoxx.game.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.soat.devoxx.game.model.BundleUserQuestions;
import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.services.repository.UserRepository;

@Repository
public class UserServicesImpl implements UserServices  {

	@Autowired
	UserRepository userRepo;

	@Override
	public DevoxxUser getUser(Long userId) {
		return userRepo.findOne(userId);
	}

	@Override
	public void createUser(DevoxxUser user) {
	    userRepo.save(user);
	}
	
	@Override
    public void updateUser(DevoxxUser user) {
	    userRepo.save(user);
    }

    @Override
	public void deleteUser(DevoxxUser user) {
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
    public Iterable<DevoxxUser> getAllUsers() {
        return userRepo.findAll();
    }    

    @Override
	public DevoxxUser getUserByName(String username) {
		return userRepo.findUserByName(username);
	}    
}
