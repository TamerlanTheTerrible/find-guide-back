package me.timur.findguideback.bot.common.model.enums;

import me.timur.findguideback.bot.client.model.enums.ClientCommand;

/**
 * Created by Temurbek Ismoilov on 22/06/23.
 */

public enum CommonCommand {
    CONFIRM_GUIDE("CONFIRM_GUIDE"),
    CONFIRM("CONFIRM"),
    REJECT("REJECT"),
    ;

    public final String command;

    CommonCommand(String s) {
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
