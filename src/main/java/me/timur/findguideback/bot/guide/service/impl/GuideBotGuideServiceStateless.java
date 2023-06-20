package me.timur.findguideback.bot.guide.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.util.KeyboardUtil;
import me.timur.findguideback.bot.guide.model.dto.NewGuideProgress;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import me.timur.findguideback.bot.guide.service.GuideBotUpdateHandlerService;
import me.timur.findguideback.entity.Language;
import me.timur.findguideback.entity.Region;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.repository.LanguageRepository;
import me.timur.findguideback.repository.RegionRepository;
import me.timur.findguideback.service.GuideService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Handle request.
 * With every request GuideProgress will be updated.
 * The flow should be strictly followed the order of steps: language -> region -> car -> done
 * If the previous step is not completed (e.g. the previous step's field is empty), the previous step will be repeated.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class GuideBotGuideServiceStateless implements GuideBotUpdateHandlerService {

    private final LanguageRepository languageRepository;
    private final RegionRepository regionRepository;
    private final GuideService guideService;
    private final ConcurrentHashMap<Long, NewGuideProgress> newGuideProgressMap;

    @Override
    public GuideCommand getType() {
        return GuideCommand.NEW_GUIDE;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        log.info("Handling request: {}", requestDto);
        // get request data
        final long chatId = requestDto.getChatId();
        final String data = requestDto.getData();
        final int prevMessageId = requestDto.getPrevMessageId();

        List<BotApiMethod<? extends Serializable>> methodList = new ArrayList<>();

        // get progress or create new one
        NewGuideProgress progress = newGuideProgressMap.get(chatId);
        if (progress == null) {
            log.info("Creating new progress for chatId: {}", chatId);
            progress = new NewGuideProgress();
            newGuideProgressMap.put(chatId, progress);
        }

        if (progress.isLanguageProgressing()) {
            if (Objects.equals(data, "Skip")) {
                progress.regionProcessing();
                methodList = sendMessage(chatId, "Select region", createRegionsKeyboard(), prevMessageId);
            } else {
                progress.addLanguage(data);
                methodList = sendMessage(chatId, "You can select more language or skip", createLanguagesKeyboard(), prevMessageId);
            }
        } else if (progress.isRegionProgressing()) {
            // if no language is selected, repeat language step
            if (progress.getLanguages().isEmpty()) {
                progress.languageIsProcessing();
                methodList = sendMessage(chatId, "Select language", createLanguagesKeyboard(), prevMessageId);
            } else {
                // else continue with region step
                if (Objects.equals(data, "Skip")) {
                    progress.carProcessing();
                    methodList = sendMessage(chatId, "Do you have a car for an excursion?", createHasCarKeyboard(), prevMessageId);
                } else {
                    progress.addRegion(data);
                    methodList = sendMessage(chatId, "You can select more region or skip", createRegionsKeyboard(), prevMessageId);
                }
            }
        } else if (progress.isCarProgressing()) {
            if (progress.getRegions().isEmpty()) {
                progress.regionProcessing();
                methodList = sendMessage(chatId, "Select region", createRegionsKeyboard(), prevMessageId);
            } else {
                progress.commentProcessing();
                progress.setHasCar(Objects.equals(data, "yes"));
                methodList = sendMessage(chatId, "A new guide successfully saved", prevMessageId);
                guideService.save(new GuideCreateOrUpdateDto(chatId, progress));
                newGuideProgressMap.remove(chatId);
            }
        }

        return methodList;
    }

    private ReplyKeyboard createLanguagesKeyboard() {
        List<String> languages = new ArrayList<>(languageRepository.findAll().stream().map(Language::getEngName).toList());
        languages.add("Skip");
        return KeyboardUtil.inlineKeyboard(languages, getType().command,2);
    }

    private ReplyKeyboard createRegionsKeyboard() {
        List<String> regions = new ArrayList<>(regionRepository.findAll().stream().map(Region::getEngName).toList());
        regions.add("Skip");
        return KeyboardUtil.inlineKeyboard(regions, getType().command,2);
    }

    private ReplyKeyboard createHasCarKeyboard() {
        return KeyboardUtil.inlineKeyboard(List.of("yes", "no"), getType().command,2);
    }

}
