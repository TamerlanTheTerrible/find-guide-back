package me.timur.findguideback.bot.common.util;

import me.timur.findguideback.bot.client.model.enums.ClientCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static me.timur.findguideback.bot.common.util.UpdateUtil.callbackDataPrefixed;

/**
 * Created by Temurbek Ismoilov on 25/04/23.
 */

public class KeyboardUtil {

    public static InlineKeyboardMarkup inlineKeyboard(String value, ClientCommand prefix) {
        List<String> values = new ArrayList<>(1);
        values.add(value);
        return inlineKeyboard(values, prefix.command, 1);
    }

    public static InlineKeyboardMarkup inlineKeyboard(List<String> values, ClientCommand prefix) {
        return inlineKeyboard(values, prefix.command, 2);
    }

    public static InlineKeyboardMarkup inlineKeyboard(List<String> values, String prefix, int rowLength) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton(values.get(i));
            button.setCallbackData(callbackDataPrefixed(prefix, values.get(i)));
            buttonRow.add(button);
            if (i % rowLength == 0) {
                keyboard.add(buttonRow);
                buttonRow = new ArrayList<>();
            }
        }
        if (!buttonRow.isEmpty()) {
            keyboard.add(buttonRow);
        }

        return new InlineKeyboardMarkup(keyboard);
    }

    public static ReplyKeyboardMarkup requestPhone() {
        KeyboardButton button = new KeyboardButton("Please share your phone number");
        button.setRequestContact(true);

        KeyboardRow row = new KeyboardRow();
        row.add(button);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(row);

        ReplyKeyboardMarkup keyboard = replyKeyboardMarkup();

        keyboard.setKeyboard(keyboardRows);

        return keyboard;
    }

    private static ReplyKeyboardMarkup replyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setSelective(false);
        return keyboard;
    }
}
