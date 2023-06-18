package me.timur.findguideback.bot.client.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.client.model.enums.ClientCommand;
import me.timur.findguideback.bot.client.model.dto.GuideDto;
import me.timur.findguideback.bot.client.model.dto.RequestDto;
import me.timur.findguideback.bot.client.model.dto.UserProgress;
import me.timur.findguideback.bot.common.constant.Language;
import me.timur.findguideback.bot.common.service.BotUpdateHandlerService;
import me.timur.findguideback.bot.common.util.CalendarUtil;
import me.timur.findguideback.bot.common.util.KeyboardUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class BotGuideSearchServiceImplBot implements BotUpdateHandlerService {

    private final ConcurrentHashMap<Long, UserProgress> userProgressMap;
    private final static String callbackPrefix = ClientCommand.GUIDE_PARAMS.command;

    @Override
    public ClientCommand getType() {
        return ClientCommand.GUIDE_PARAMS;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        log.info("Handling request: {}", requestDto);
        // get request data
        final long chatId = requestDto.getChatId();
        final String data = requestDto.getData();
        final int prevMessageId = requestDto.getPrevMessageId();

        UserProgress progress = userProgressMap.get(chatId);
        List<BotApiMethod<? extends Serializable>> methodList = new ArrayList<>();

        if (progress == null) {
            log.info("Creating new progress for chatId: {}", chatId);
            userProgressMap.put(chatId, new UserProgress());
            methodList = sendMessage(chatId,"Please select a language:", createLanguageOptionsKeyboard());
        } else if (progress.isSelectingLanguage()) {
            log.info("Selecting language for chatId: {}", chatId);
            // Store the selected language and ask for the region
            progress.setLanguage(Language.get(data));
            progress.setSelectingLanguage(false);
            progress.setSelectingRegion(true);
            methodList = sendMessage(chatId, prevMessageId,"Please select a region:", createRegionOptionsKeyboard());
        } else if (progress.isSelectingRegion()) {
            log.info("Selecting region for chatId: {}", chatId);
            // Store the selected region and ask for the start year
            progress.setRegion(data);
            progress.setSelectingRegion(false);
            progress.setSelectingStartYear(true);
            methodList = sendYear(chatId, "Please select a start year:", prevMessageId);
        }  else if (progress.isSelectingStartYear()) {
            log.info("Selecting start year for chatId: {}", chatId);
            // Store the selected year and ask for the start month
            progress.setStartYear(Integer.valueOf(data));
            progress.setSelectingStartYear(false);
            progress.setSelectingStartMonth(true);
            methodList = sendMonth(chatId, "Please select a start month:", prevMessageId);
        }  else if (progress.isSelectingStartMonth()) {
            log.info("Selecting start month for chatId: {}", chatId);
            // Store the selected moth and ask for the start date
            progress.setStartMonth(CalendarUtil.monthNumber(data));
            progress.setSelectingStartMonth(false);
            progress.setSelectingStartDate(true);
            methodList = sendDay(chatId, "Please select a start date:", prevMessageId);
        }  else if (progress.isSelectingStartDate()) {
            log.info("Selecting start date for chatId: {}", chatId);
            // Store the selected date and ask for the end year
            progress.setStartDate(Integer.valueOf(data));
            progress.setSelectingStartDate(false);
            progress.setSelectingEndYear(true);
            methodList = sendYear(chatId, "Please select a end year:", prevMessageId);
        }  else if (progress.isSelectingEndYear()) {
            log.info("Selecting end year for chatId: {}", chatId);
            // Store the selected end year and ask for the end month
            progress.setEndYear(Integer.valueOf(data));
            progress.setSelectingEndYear(false);
            progress.setSelectingEndMonth(true);
            methodList = sendMonth(chatId, "Please select a end month:", prevMessageId);
        } else if (progress.isSelectingEndMonth()) {
            log.info("Selecting end month for chatId: {}", chatId);
            // Store the selected end month and ask for the end date
            progress.setEndMonth(CalendarUtil.monthNumber(data));
            progress.setSelectingEndMonth(false);
            progress.setSelectingEndDate(true);
            methodList = sendDay(chatId, "Please select an end date:", prevMessageId);
        } else if (progress.isSelectingEndDate()) {
            log.info("Selecting end date for chatId: {}", chatId);
            // Store the selected end date and search
            progress.setEndDate(Integer.valueOf(data));
            // Search for a guide based on the user's input
            methodList = searchGuide(chatId, progress);
        }

        return methodList;
    }

    private List<BotApiMethod<? extends Serializable>> searchGuide(long chatId, UserProgress progress) {
        List<BotApiMethod<? extends Serializable>> methodList;
        String language = progress.getLanguage().name();
        String region = progress.getRegion();
        String startDateFormatted = CalendarUtil.formatDate(LocalDate.of(progress.getStartYear(), progress.getStartMonth(), progress.getStartDate()));
        String endDateFormatted = CalendarUtil.formatDate(LocalDate.of(progress.getEndYear(), progress.getEndMonth(), progress.getEndDate()));

        List<GuideDto> guideDtos = searchGuides(language, region, startDateFormatted, endDateFormatted);
        if (guideDtos.isEmpty()) {
            methodList = sendMessage(
                    chatId,
                    "No guides available for the selected criteria. "
                            + "Please try again with different criteria.");
        } else {
            // Display the search results to the user
            StringBuilder messageText = new StringBuilder();
            messageText.append("Here are the available guides:\n");
            for (GuideDto guideDto : guideDtos) {
                messageText
                        .append("\n- ")
                        .append(guideDto.getName())
                        .append(" (")
                        .append(guideDto.getLanguage())
                        .append(")");
            }
            methodList = sendMessage(chatId, messageText.toString());
        }
        userProgressMap.remove(chatId);
        return methodList;
    }

    private InlineKeyboardMarkup createLanguageOptionsKeyboard() {
        List<String> languages =
                Arrays.stream(Language.values())
                        .map(l -> l.text)
                        .toList();
        return KeyboardUtil.createInlineKeyboard(languages, callbackPrefix,4);
    }

    private InlineKeyboardMarkup createRegionOptionsKeyboard() {
        List<String> regions = List.of("Uzbekistan", "Tashkent", "Samarkand", "Bukhara", "Khiva", "Fergana Valley");
        return KeyboardUtil.createInlineKeyboard(regions, callbackPrefix, 3);
    }

    private List<BotApiMethod<? extends Serializable>> sendYear(long chatId, String messageText, int prevMessageId) {
        // Create a reply keyboard markup with a calendar
        InlineKeyboardMarkup markup = KeyboardUtil.createInlineKeyboard(List.of("2023","2024","2025"), callbackPrefix, 3);
        return sendMessage(chatId, prevMessageId, messageText, markup);
    }

    private List<BotApiMethod<? extends Serializable>> sendMonth(long chatId, String messageText, int prevMessageId) {
        // Create a reply keyboard markup with a calendar
        InlineKeyboardMarkup markup = KeyboardUtil.createInlineKeyboard(CalendarUtil.monthNames(), callbackPrefix, 3);
        return sendMessage(chatId, prevMessageId, messageText, markup);
    }

    private List<BotApiMethod<? extends Serializable>> sendDay(long chatId, String messageText, int prevMessageId) {
        // Create a reply keyboard markup with a calendar
        List<String> days = new ArrayList<>();
        for (int i=1; i <=31; i++){
            days.add(String.valueOf(i));
        }
        InlineKeyboardMarkup markup = KeyboardUtil.createInlineKeyboard(days, callbackPrefix, 7);
        return sendMessage(chatId, prevMessageId, messageText, markup);
    }

    private List<GuideDto> searchGuides(String language, String region, String startDate, String endDate) {
        log.info("Searching for a guide => lang: {}, region: {}, startDate: {}, endDate {}", language, region, startDate, endDate);
        return null;
    }

}
