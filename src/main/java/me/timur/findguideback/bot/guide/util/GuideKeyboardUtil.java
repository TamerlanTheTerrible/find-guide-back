package me.timur.findguideback.bot.guide.util;

import me.timur.findguideback.bot.common.util.KeyboardUtil;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;

/**
 * Created by Temurbek Ismoilov on 27/06/23.
 */

public class GuideKeyboardUtil {

    public static InlineKeyboardMarkup mainMenu() {
        // Create a reply keyboard markup with a calendar
        return KeyboardUtil.inlineKeyboard(
                Arrays.asList(GuideCommand.CALENDAR.command, GuideCommand.JOBS.command),
                "getType().command,",
                3);
    }
}
