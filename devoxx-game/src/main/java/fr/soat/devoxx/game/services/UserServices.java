package fr.soat.devoxx.game.services;

import java.util.List;

import fr.soat.devoxx.game.model.DevoxxUser;

public interface UserServices {

    public Iterable<DevoxxUser> getAllUsers();

    public void createUser(DevoxxUser user);

    public void updateUser(DevoxxUser user);

    public void deleteUser(DevoxxUser user);

    public int getPosition(DevoxxUser user);

    public long nbOfUsers();

    public DevoxxUser getUser(Long userId);

    public DevoxxUser getUserByName(String username);

    public void approveRules(DevoxxUser user);

    /**
     * Retourne la liste du top 10 des utilisateurs avec leurs scores et leur
     * temps total de reponse en secondes.
     * 
     * @return
     */
    public List<DevoxxUser> getPlayersTop10();
}
