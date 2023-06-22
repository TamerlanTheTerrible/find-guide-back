package me.timur.findguideback.bot.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.admin.service.AdminService;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.util.UpdateUtil;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.service.GuideService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Created by Temurbek Ismoilov on 21/06/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final GuideService guideService;

    @Override
    public List<BotApiMethod<? extends Serializable>> confirmGuide(RequestDto requestDto) {
        final Long guideTelegramId = Long.valueOf(UpdateUtil.getData(requestDto.getData()));
        log.info("Admin {} confirming guide with telegramId {}", requestDto.getChatId(), guideTelegramId);
        GuideDto guideDto = guideService.getByTelegramId(guideTelegramId);
        String guideInfo = String.format("name: %s, phone: %s, telegramId: %s", guideDto.getUser().getFullNameOrUsername(), guideDto.getUser().getPhoneNumbers(), guideDto.getUser().getTelegramId());

        if (guideDto.getIsVerified()) {
            return sendMessage(requestDto.getChatId(), String.format("Guide %s is already verified", guideInfo));
        }

        guideDto = guideService.confirmGuide(guideTelegramId);
        if (guideDto.getIsVerified()) {
            return sendMessage(requestDto.getChatId(), String.format("Guide %s verified", guideInfo));
        } else {
            return sendMessage(requestDto.getChatId(), String.format("Guide %s verification failed", guideInfo));
        }
    }
}
