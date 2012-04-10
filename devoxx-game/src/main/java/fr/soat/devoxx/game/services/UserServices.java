package fr.soat.devoxx.game.services;

import java.util.List;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.UserQuestion;

public interface UserServices {

    public Iterable<DevoxxUser> getAllUsers();

    public void createUser(DevoxxUser user);

    public void updateUser(DevoxxUser user);
    
    public void updateIsEnabledUser(DevoxxUser user);

    public void deleteUser(DevoxxUser user);

	public long getPosition(DevoxxUser user);
    
    public long nbOfUsers();

    public DevoxxUser getUser(Long userId);

    public DevoxxUser getUserByName(String username);

    public void approveRules(DevoxxUser user);

    public List<DevoxxUser> getPlayersTop10();
    
    public List<DevoxxUser> findUsersByFornameOrEmail(String term);

    void updatePlayerScore(UserQuestion question, DevoxxUser user);
}
