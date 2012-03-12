package fr.soat.devoxx.game.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: mparisot
 * Date: 12/03/12
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class NoMoreQuestionException extends RuntimeException {

    public NoMoreQuestionException() {
    }

    public NoMoreQuestionException(String s) {
        super(s);
    }

    public NoMoreQuestionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NoMoreQuestionException(Throwable throwable) {
        super(throwable);
    }
}
