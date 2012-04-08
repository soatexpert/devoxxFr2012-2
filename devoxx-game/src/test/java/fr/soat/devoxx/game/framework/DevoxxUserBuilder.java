package fr.soat.devoxx.game.framework;

import fr.soat.devoxx.game.model.DevoxxUser;

/**
 */
public class DevoxxUserBuilder {
    
    private DevoxxUser user;

    public DevoxxUserBuilder() {
        this.user = new DevoxxUser();
    }

    public static DevoxxUserBuilder newBuilder() {
        return new DevoxxUserBuilder();
    }
    
    public DevoxxUser build() {
        return user;
    }

    public DevoxxUserBuilder rulesApproved() {
        user.setRulesApproved(true);
        return  this;
    }

    public DevoxxUserBuilder withName(String test) {
        user.setUserForname(test);
        return this;
    }

    public DevoxxUserBuilder approved() {
        user.setEnabled(true);
        return this;
    }
}
