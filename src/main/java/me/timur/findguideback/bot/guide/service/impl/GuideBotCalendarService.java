package me.timur.findguideback.bot.guide.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.client.model.dto.GuideDto;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.util.CalendarUtil;
import me.timur.findguideback.bot.common.util.KeyboardUtil;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import me.timur.findguideback.bot.guide.service.GuideBotUpdateHandlerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class GuideBotCalendarService implements GuideBotUpdateHandlerService {

    @Override
    public GuideCommand getType() {
        return GuideCommand.CALENDAR;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        log.info("Calendar service is called");
        // get request data
        final long chatId = requestDto.getChatId();
        final String data = requestDto.getData();
        final int prevMessageId = requestDto.getPrevMessageId();
        List<BotApiMethod<? extends Serializable>> methodList = new ArrayList<>();

        return methodList;
    }

    private List<BotApiMethod<? extends Serializable>> sendYear(long chatId, String messageText, int prevMessageId) {
        // Create a reply keyboard markup with a calendar
        List<String> list = new ArrayList<>();
        LocalDate now = LocalDate.now();
        Collections.addAll(list, String.valueOf(now.getYear()), String.valueOf(now.getYear() + 1));
        InlineKeyboardMarkup markup = KeyboardUtil.inlineKeyboard(list, getType().command, 3);
        return sendMessage(chatId, messageText, markup, prevMessageId);
    }

    private List<BotApiMethod<? extends Serializable>> sendMonth(long chatId, String messageText, int prevMessageId) {
        // Create a reply keyboard markup with a calendar
        InlineKeyboardMarkup markup = KeyboardUtil.inlineKeyboard(CalendarUtil.monthNames(), getType().command, 3);
        return sendMessage(chatId, messageText, markup, prevMessageId);
    }

    private List<BotApiMethod<? extends Serializable>> sendDay(long chatId, String messageText, int prevMessageId) {
        // Create a reply keyboard markup with a calendar
        List<String> days = new ArrayList<>();
        for (int i=1; i <=31; i++){
            days.add(String.valueOf(i));
        }
        InlineKeyboardMarkup markup = KeyboardUtil.inlineKeyboard(days, getType().command, 7);
        return sendMessage(chatId, messageText, markup, prevMessageId);
    }

    private List<GuideDto> searchGuides(String language, String region, String startDate, String endDate) {
        log.info("Searching for a guide => lang: {}, region: {}, startDate: {}, endDate {}", language, region, startDate, endDate);
        return null;
    }

}
