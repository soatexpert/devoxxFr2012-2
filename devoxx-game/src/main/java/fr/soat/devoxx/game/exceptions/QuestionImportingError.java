package fr.soat.devoxx.game.exceptions;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: mparisot
 * Date: 02/04/12
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public class QuestionImportingError extends Throwable {
    public QuestionImportingError(String message, IOException e) {
        super(message,e);
    }
}
