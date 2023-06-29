package me.timur.findguideback.bot.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.admin.service.AdminService;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.model.enums.CommonCommand;
import me.timur.findguideback.bot.common.util.UpdateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Created by Temurbek Ismoilov on 22/03/23.
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class AdminBot extends TelegramLongPollingBot {

    private final AdminService adminService;

    @Value("${bot.admin.username}")
    private String BOT_NAME;
    @Value("${bot.admin.token}")
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
        log.info("ADMIN BOT Message : {}", request);
        if (Objects.equals(request.getData(), "/start")) {
            execute(sendMessage(request.getChatId(), "Welcome to Admin Bot"));
        } else {
            execute(sendMessage(request.getChatId(), "I don't understand you"));
        }
    }

    private void handleCallbackQuery(CallbackQuery query) {
        RequestDto request = new RequestDto(query);
        log.info("ADMIN BOT CallbackQuery : {}",request);
        final String prefix = UpdateUtil.getPrefix(query.getData());
        if (prefix.equals(CommonCommand.CONFIRM.command)) {
            execute(adminService.confirmGuide(new RequestDto(query), true));
        } else if (prefix.equals(CommonCommand.REJECT.command)) {
            execute(adminService.confirmGuide(new RequestDto(query), false));
        } else {
            execute(sendMessage(request.getChatId(), "I don't understand you"));
        }
    }

    private void execute(List<BotApiMethod<? extends Serializable>> methods) {
        try {
            for (BotApiMethod<? extends Serializable> method : methods) {
                execute(method);
            }
        } catch (Exception e) {
            log.error("Error while executing methods", e);
        }
    }
}
