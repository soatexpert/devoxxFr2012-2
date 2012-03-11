package fr.soat.devoxx.game.exceptions;

public enum DevoxxExceptionType {
	
	USER_ALREADY_EXISTS("Utilisateur existe deja...");
	
	String messageName;
	
	private DevoxxExceptionType(String messageName) {
		this.messageName = messageName;
	}
	
	public String getMessageName(){
		return messageName;
	}
	
}
