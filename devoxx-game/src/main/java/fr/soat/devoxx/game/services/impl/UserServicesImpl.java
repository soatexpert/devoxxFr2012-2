package fr.soat.devoxx.game.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

import fr.soat.devoxx.game.model.*;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.UserQuestionsGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.services.repository.UserRepository;

@Repository
public class UserServicesImpl implements UserServices  {

	@Autowired
	private UserRepository userRepo;
    
    @Autowired
    private UserQuestionsGenerator userQuestionsGenerator;

    @Autowired
    private QuestionServices questionServices;
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserServicesImpl.class);

	@Override
	public DevoxxUser getUser(Long userId) {
		return userRepo.findOne(userId);
	}

	@Override
	public void createUser(DevoxxUser user) {
	    userRepo.save(user);

        List<UserQuestion> userQuestions = userQuestionsGenerator.generateQuestionsListForUser(user);

        questionServices.saveBundleOfUserQuestions(userQuestions);
    }
	
	@Override
    public void updateUser(DevoxxUser user) {
	    userRepo.save(user);	    
    }

    @Override
	public void deleteUser(DevoxxUser user) {
	    userRepo.delete(user);
	}

    public int getPosition() {
		return 10;
	}

    public long nbOfUsers() {
        return userRepo.count();
    }
    
    @Override
    public Iterable<DevoxxUser> getAllUsers() {
        return userRepo.findAll();
    }    

    @Override
	public DevoxxUser getUserByName(String username) {
		return userRepo.findUserByName(username);
	}

    @Override
    public void approveRules(DevoxxUser user) {
        user.setReglementAccepted(true);
        updateUser(user);
    }

    @Override
    public List<RankedUser> getPlayersTop10() {
        List<RankedUser> rankedUsers =userRepo.ranking();

        CollectionUtils.

        return filteredRankedUsers;
    }
}
