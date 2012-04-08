package fr.soat.devoxx.game.services.impl;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.QuestionPackType;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.model.UserScore;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.UserQuestionsGenerator;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.services.repository.UserRepository;
import fr.soat.devoxx.game.services.repository.UserScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserServicesImpl implements UserServices  {

	@Autowired
	private UserRepository userRepo;
    
    @Autowired
    private UserScoreRepository userScoreRepo;
    
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
        final UserScore score = userScoreRepo.save(user.getCurrentScore());
        user.setCurrentScore(score);
	    userRepo.save(user);
    }

    @Override
	public void deleteUser(DevoxxUser user) {
	    userRepo.delete(user);
	}

    public long getPosition(DevoxxUser user) {
		return userRepo.getUsersWithScoreLessThan(user.getScore(), user.getTotalTime(), QuestionPackType.packForToday()).size()+1;
	}

    @Override
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
        user.setRulesApproved(true);
        updateUser(user);
    }

    @Override
    public List<DevoxxUser> getPlayersTop10() {
        Pageable topTen = new PageRequest(0, 10);
        return userRepo.findTopTen(QuestionPackType.packForToday(),topTen).getContent();
    }

    @Override
    public List<DevoxxUser> findUsersByFornameOrEmail(String term) {
        return userRepo.findUsersByForNameOrEmail(term);
    }
}
