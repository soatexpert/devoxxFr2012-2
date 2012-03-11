package fr.soat.devoxx.game.exceptions;

public class DevoxxException extends Exception {

	private static final long serialVersionUID = 1L;

	public DevoxxException(DevoxxExceptionType devoxxExceptionType ) {
		super(devoxxExceptionType.getMessageName());
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
}
