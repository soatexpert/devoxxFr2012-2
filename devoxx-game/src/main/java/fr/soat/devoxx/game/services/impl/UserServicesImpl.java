package fr.soat.devoxx.game.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.UserQuestionsGenerator;
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

    @Override
    public int getPosition(DevoxxUser user) {
		return userRepo.findUserRankingPosition(user.getUserId()).intValue() + 1;
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
        user.setReglementAccepted(true);
        updateUser(user);
    }

    @Override
    public List<DevoxxUser> getPlayersTop10() {        
        Order scoreOrder = new Order(Direction.DESC, "score");
        Order totalTimeOrder = new Order(Direction.ASC, "totalTime");
        Order userFornameOrder = new Order(Direction.ASC, "userForname");
        Sort sort = new Sort(scoreOrder, totalTimeOrder, userFornameOrder);
        Pageable topTen = new PageRequest(0, 10, sort);
        return userRepo.findUsersWithPager(topTen).getContent();
    }
}
