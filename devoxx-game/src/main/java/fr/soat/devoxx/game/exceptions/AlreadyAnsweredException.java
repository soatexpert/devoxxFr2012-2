package fr.soat.devoxx.game.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: mparisot
 * Date: 13/03/12
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class AlreadyAnsweredException extends RuntimeException {

    public AlreadyAnsweredException() {
    }

    public AlreadyAnsweredException(String s) {
        super(s);
    }

    public AlreadyAnsweredException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AlreadyAnsweredException(Throwable throwable) {
        super(throwable);
    }
}
