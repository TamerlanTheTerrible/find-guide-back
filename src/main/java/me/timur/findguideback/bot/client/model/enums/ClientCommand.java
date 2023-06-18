package me.timur.findguideback.bot.client.model.enums;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

public enum ClientCommand {
    USER("/start"),
    GUIDE_PARAMS("/findguide"),
    ;

    public final String command;

    ClientCommand(String s) {
        this.command = s;
    }

    public static ClientCommand get(String command) {
        for (ClientCommand c : ClientCommand.values()) {
            if (c.command.equals(command)) {
                return c;
            }
        }
        return null;
    }
}
