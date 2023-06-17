package me.timur.findguideback.bot.client.exception;

/**
 * Created by Temurbek Ismoilov on 10/04/23.
 */

public class ClinetException extends RuntimeException {
    private String message;

    public ClinetException(String message) {
        this.message = message;
    }

    public ClinetException(String message, Object... vars) {
        this.message = String.format(message, vars);
    }
}
