package me.timur.findguideback.bot.common.util;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 04/05/23.
 */

public class BotApiMethodUtil {

    private BotApiMethodUtil() {
    }

    public static List<BotApiMethod<? extends Serializable>> sendMessage(@NonNull Long chatId, String message) {
        return toList(new SendMessage(String.valueOf(chatId), message));
    }

    public static List<BotApiMethod<? extends Serializable>> sendMessage(@NonNull Long chatId, String message, int prevMessageId) {
        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), prevMessageId);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        return toList(deleteMessage, sendMessage);
    }

    public static List<BotApiMethod<? extends Serializable>> sendMessage(@NonNull Long chatId, String message, ReplyKeyboard markup) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        sendMessage.setReplyMarkup(markup);
        return toList(sendMessage);
    }

    public static List<BotApiMethod<? extends Serializable>> sendMessage(@NonNull Long chatId, String message, ReplyKeyboard markup, int prevMessageId) {
        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), prevMessageId);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        sendMessage.setReplyMarkup(markup);
        return toList(deleteMessage, sendMessage);
    }

    public static List<BotApiMethod<? extends Serializable>> deleteMessage(@NonNull Long chatId, int prevMessageId) {
        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), prevMessageId);
        return toList(deleteMessage);
    }

    private static List<BotApiMethod<? extends Serializable>> toList(BotApiMethod<? extends Serializable>... methods) {
        List<BotApiMethod<? extends Serializable>> list = new ArrayList();
        Collections.addAll(list, methods);
        return list;
    }

}
