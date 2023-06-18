package me.timur.findguideback.bot.guide;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.client.model.dto.RequestDto;
import me.timur.findguideback.bot.common.factory.CallbackHandlerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 22/03/23.
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class GuideBot extends TelegramLongPollingBot {

    private final CallbackHandlerFactory callbackHandlerFactory;

    @Value("${bot.guide.username}")
    private String BOT_NAME;
    @Value("${bot.guide.token}")
    private String BOT_TOKEN;

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return this.BOT_NAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleIncomingMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        this.onUpdateReceived(updates.get(0));
    }

    private void handleIncomingMessage(Message message) {
        var request = new RequestDto(message);
        log.info("GUIDE BOT Message : {}", request);

//        var callbackHandler = callbackHandlerFactory.get(message.getText());
//        var methods = callbackHandler.handle(request);
        execute(List.of(
                new SendMessage(message.getChatId().toString(), "Hello, "
        )));
    }

    private void handleCallbackQuery(CallbackQuery query) {
        var request = new RequestDto(query);
        log.info("GUIDE BOT CallbackQuery : {}",request);

//        var callbackHandler = callbackHandlerFactory.get(ClientCommand.get(UpdateUtil.getPrefix(query.getData())));
//        var methods = callbackHandler.handle(request);
        execute(
                List.of(
                        new SendMessage(query.getMessage().getChatId().toString(), "Hello, "
                        )
                ));
    }

    private void execute(List<BotApiMethod<? extends Serializable>> methods) {
        try {
            for (BotApiMethod<? extends Serializable> method : methods) {
                execute(method);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
