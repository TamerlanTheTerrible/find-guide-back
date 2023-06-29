package me.timur.findguideback.bot.guide.model.enums;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

public enum GuideCommand {
    USER("/start"),
    UNIVERSAL("UNIVERSAL"),
    NEW_GUIDE("NEW_GUIDE"),
    CALENDAR("CALENDAR"),
    JOBS("JOBS"),
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
