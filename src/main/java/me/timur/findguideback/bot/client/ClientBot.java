package me.timur.findguideback.bot.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.client.factory.ClientBotCallbackHandlerFactory;
import me.timur.findguideback.bot.client.model.enums.ClientCommand;
import me.timur.findguideback.bot.client.service.ClientBotUpdateHandlerService;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.util.UpdateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Temurbek Ismoilov on 22/03/23.
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class ClientBot extends TelegramLongPollingBot {

    private final ClientBotCallbackHandlerFactory clientBotCallbackHandlerFactory;

    @Value("${bot.client.username}")
    private String BOT_NAME;
    @Value("${bot.client.token}")
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
        RequestDto request = new RequestDto(message);
        log.info("CLIENT BOT Message : {}", request);

        try {
            final WebAppData webAppData = message.getWebAppData();
            if (webAppData != null) {
                final String text = "================== From Message WebApp: " + webAppData.getData();
                log.info(text);
                execute(new SendMessage(message.getChatId().toString(), text));
            } else if (Objects.equals(message.getText(), "/webapp")) {
                final String text = "================== From Message";
                log.info(text);
                execute(new SendMessage(message.getChatId().toString(), text));
            }
        } catch (Exception e) {
            log.error("Error while handling WebAppData", e);
        }


        ClientBotUpdateHandlerService callbackHandler = clientBotCallbackHandlerFactory.get(message.getText());
        List<BotApiMethod<? extends Serializable>> methods = callbackHandler.handle(request);
        execute(methods);
    }

    private void handleCallbackQuery(CallbackQuery query) {
        RequestDto request = new RequestDto(query);
        log.info("CLIENT BOT CallbackQuery : {}",request);

        try {
            query.getMessage().getWebAppData();
            if (query.getMessage().getWebAppData() != null) {
                final String text = "================== From CallbackQuery WebApp: " + query.getMessage().getWebAppData().getData();
                log.info(text);
                execute(new SendMessage(query.getMessage().getChatId().toString(), text));
            } else if (Objects.equals(query.getData(), "/webapp")) {
                final String text = "================== From CallbackQuery";
                log.info(text);
                execute(new SendMessage(query.getMessage().getChatId().toString(), text));
            }
        } catch (Exception e) {
            log.error("Error while handling WebAppData", e);
        }

        ClientBotUpdateHandlerService callbackHandler = clientBotCallbackHandlerFactory.get(ClientCommand.get(UpdateUtil.getPrefix(query.getData())));
        List<BotApiMethod<? extends Serializable>> methods = callbackHandler.handle(request);
        execute(methods);
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
