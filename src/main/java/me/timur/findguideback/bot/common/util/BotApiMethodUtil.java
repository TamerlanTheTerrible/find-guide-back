package me.timur.findguideback.bot.common.util;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 04/05/23.
 */

public class BotApiMethodUtil {

    private BotApiMethodUtil() {
    }

    public static List<BotApiMethod<? extends Serializable>> sendMessage(@NonNull Long chatId, String message) {
        return List.of(new SendMessage(String.valueOf(chatId), message));
    }

    public static List<BotApiMethod<? extends Serializable>> sendMessage(@NonNull Long chatId, String message, ReplyKeyboard markup) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        sendMessage.setReplyMarkup(markup);
        return List.of(sendMessage);
    }

    public static List<BotApiMethod<? extends Serializable>> sendMessage(@NonNull Long chatId, String message, ReplyKeyboard markup, int prevMessageId) {
        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), prevMessageId);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        sendMessage.setReplyMarkup(markup);
        return List.of(deleteMessage, sendMessage);
    }
}
