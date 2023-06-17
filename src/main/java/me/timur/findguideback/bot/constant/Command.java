package me.timur.findguideback.bot.constant;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

public enum Command {
    USER("/start"),
    GUIDE_PARAMS("/findguide"),
    ;

    public final String command;

    Command(String s) {
        this.command = s;
    }

    public static Command get(String command) {
        for (Command c : Command.values()) {
            if (c.command.equals(command)) {
                return c;
            }
        }
        return null;
    }
}
