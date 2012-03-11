package fr.soat.devoxx.game.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.soat.devoxx.game.dao.GenericDao;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.User;
import fr.soat.devoxx.game.services.UserServices;

@Repository
public class UserServicesImpl implements UserServices {

	@Autowired
	GenericDao<User> userDao;
	
	public User getUser(long userId){
		return userDao.getEntityManager().find(User.class, userId);
		
	}
	
	@Transactional
	public void createUser(User user)  {
		userDao.getEntityManager().persist(user);
	}

	@Transactional
	public void deleteUser(User user) {
		userDao.getEntityManager().remove(user);
	}

	public List<Question> getQuestionList() {
		return null;
	}

	public int getPosition() {
		return 0;
	}

	public List<Question> geQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

}
