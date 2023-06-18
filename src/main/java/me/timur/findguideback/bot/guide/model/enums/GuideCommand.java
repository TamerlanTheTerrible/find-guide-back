package me.timur.findguideback.bot.guide.model.enums;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

public enum GuideCommand {
    USER("/start"),
    GUIDE_PARAMS("/findguide"),
    UNIVERSAL("universal"),
    ;

    public final String command;

    GuideCommand(String s) {
        this.command = s;
    }

    public static GuideCommand get(String command) {
        for (GuideCommand c : GuideCommand.values()) {
            if (c.command.equals(command)) {
                return c;
            }
        }
        return null;
    }
}
