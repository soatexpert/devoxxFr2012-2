package fr.soat.devoxx.game.services.impl;

import fr.soat.devoxx.game.model.*;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.services.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserServicesImpl implements UserServices  {

	@Autowired
	private UserRepository userRepo;
	
	//@Autowired
	//private SessionRegistry sessionRegistry;
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserServicesImpl.class);

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
	    // invalidate user session	    
	    /*List<Object> loggedUsers = sessionRegistry.getAllPrincipals();
	    for (Object principal : loggedUsers) {
            if(principal instanceof DevoxxUser) {
                final DevoxxUser loggedUser = (DevoxxUser) principal;
                if(user.getUsername().equals(loggedUser.getUsername()) && !user.getUserRoles().equals(loggedUser.getUserRoles())) {
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
                    if(null != sessionsInfo && sessionsInfo.size() > 0) {
                        for (SessionInformation sessionInformation : sessionsInfo) {
                            //TODO ne déloggue pas le User, voir pourquoi...
                            LOGGER.info("Exprire now :" + sessionInformation.getSessionId());
                            sessionInformation.expireNow(); // force re-logging
                            sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
                        }
                    }
                }
            }
        }    */
    }

    @Override
	public void deleteUser(DevoxxUser user) {
	    userRepo.delete(user);
	}

	/*@Override
    public BundleUserQuestions getQuestionBundle() {
        List<UserQuestion> questions = new ArrayList<UserQuestion>();

        questions.add(new UserQuestion());

        BundleUserQuestions bundle = new BundleUserQuestions(
                questions,
                null
        );
        return bundle;
    }*/

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

    @Override
    public void approveRules(DevoxxUser user) {
        user.setReglementAccepted(true);
        updateUser(user);
    }

    @Override
    public List<UserQuestion> getPendingQuestionsForUser(DevoxxUser user) {

        List<UserQuestion> currentUserPendingQuestions = new ArrayList<UserQuestion>();
        
        user = getUser(user.getUserId());
        BundleUserQuestions bundle = user.getBundleUserQuestions();
        if(null != bundle) { //TODO always null
            List<Question> remainingQuestions = bundle.getRemainingQuestions();            
    
            UserQuestion userQuestion;
            for (Question question : remainingQuestions) {
                userQuestion = new UserQuestion(question);
                currentUserPendingQuestions.add(userQuestion);
            }
        } else {
            LOGGER.debug("no BundleUserQuestions for user : " + user.getUserId());
        }

        /*
         * UserQuestion pendingQuestion1 = new UserQuestion();
         * pendingQuestion1.setQuestion
         * (createQuestion("Quel est le nom de l'évènement auquel vous participez ?"
         * ,"Devoxx","JavaOne","TechDays","Solidays"));
         * currentUserPendingQuestions.add(pendingQuestion1);
         * 
         * 
         * UserQuestion pendingQuestion2 = new UserQuestion();
         * pendingQuestion2.setQuestion
         * (createQuestion("Quelle est la reponse à l'univers, la vie et tout ça ?"
         * , "42", "Dieu", "joker", "ObiWanKenobi"));
         * currentUserPendingQuestions.add(pendingQuestion2);
         */
        return currentUserPendingQuestions;
    }

    @Override
    public List<RankedUser> getPlayersTop10() {
        TreeSet<RankedUser> rankedUsers = new TreeSet<RankedUser>(new Comparator<RankedUser>() {
            @Override
            public int compare(RankedUser rankedUser, RankedUser rankedUser1) {
                return rankedUser1.getScore() - rankedUser.getScore();
            }
        });

        Iterable<DevoxxUser> allUsers = getAllUsers();

        Random randomizer = new Random();

        int maxUsers = 10;
        int cmp = 1;
        for (DevoxxUser user : allUsers) {
            rankedUsers.add(new RankedUser(user,randomizer.nextInt(200),randomizer.nextInt(2000)));

            cmp++;
            if(cmp > 10) {
                break;
            }
        }

        return new ArrayList<RankedUser>(rankedUsers);
    }

    // TODO a supprimer quand impl en base faite!
    private AtomicLong increment = new AtomicLong();
    private Question createQuestion(String questionLabel,String... answers) {
        Question question = new Question();
        question.setIdQuestion(increment.incrementAndGet());
        question.setQuestionLabel(questionLabel);
        List<QuestionChoice> questionAnswers = new ArrayList<QuestionChoice>();

        for(String answer : answers) {
            QuestionChoice choice1 = new QuestionChoice();
            choice1.setQuestionChoiceId(increment.incrementAndGet());
            choice1.setChoiceLabel(answer);
            questionAnswers.add(choice1);
        }

        question.setGoodChoice(questionAnswers.get(0));

        question.setChoices(questionAnswers);

        return question;
    }
}
