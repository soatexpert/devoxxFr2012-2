package fr.soat.devoxx.game.services.impl;

import java.util.ArrayList;
import java.util.List;

import fr.soat.devoxx.game.model.BundleUserQuestions;
import fr.soat.devoxx.game.model.UserQuestion;
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

	public User getUser(long userId) {
		return userDao.getEntityManager().find(User.class, userId);

	}

	@Transactional
	public void createUser(User user) {
		userDao.getEntityManager().persist(user);
	}
	
	@Override
	@Transactional
    public void updateUser(User user) {
	    userDao.getEntityManager().merge(user);        
    }

	@Transactional
	public void deleteUser(User user) {
		userDao.getEntityManager().remove(user);
	}

    public BundleUserQuestions getQuestionBundle() {
        List<UserQuestion> questions = new ArrayList<UserQuestion>();

        questions.add(new UserQuestion(new Question()));

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

    @SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return userDao.getEntityManager().createQuery("from User").getResultList();
	}

	@Transactional
	public User getUserByName(String username) {
		User u = (User) userDao.getEntityManager().createQuery("from User u where u.userName = ?1").setParameter(1, username).getSingleResult();
		/*if (resultList.size() != 0)
			return (UserDetails) resultList.get(0);
		User u = new User();
		u.setUserName(username);
		u.setAdmin(false);
		createUser(u);*/
		return u;
	}
}
