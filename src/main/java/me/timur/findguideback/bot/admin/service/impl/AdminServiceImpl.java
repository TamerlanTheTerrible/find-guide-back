package me.timur.findguideback.bot.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.admin.service.AdminService;
import me.timur.findguideback.bot.common.http.HttpHelper;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.service.GuideService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
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
    private final HttpHelper httpHelper;

    @Value("${bot.guide.token}")
    private String GUIDE_BOT_TOKEN;

    @Override
    public List<BotApiMethod<? extends Serializable>> confirmGuide(RequestDto requestDto, boolean isConfirmed) {
        final Long guideTelegramId = Long.valueOf(requestDto.getData());
        log.info("Admin {} confirming/rejecting guide with telegramId {}, isConfirmed: {}", requestDto.getChatId(), guideTelegramId, isConfirmed);
        GuideDto guideDto = guideService.getByTelegramId(guideTelegramId);
        String guideInfo = String.format("name: %s, phone: %s, telegramId: %s", guideDto.getUser().getFullNameOrUsername(), guideDto.getUser().getPhoneNumbers(), guideDto.getUser().getTelegramId());

        if (guideDto.getIsVerified() && isConfirmed) {
            return sendMessage(requestDto.getChatId(), String.format("Guide %s is already verified", guideInfo));
        } else if (!guideDto.getIsVerified() && !isConfirmed) {
            return sendMessage(requestDto.getChatId(), String.format("Guide %s is already rejected to verify", guideInfo));
        }

        guideDto = guideService.confirmGuide(guideTelegramId, isConfirmed);
        if (guideDto.getIsVerified() && isConfirmed) {
            notifyGuide(guideDto);
            return sendMessage(requestDto.getChatId(), String.format("Guide %s verified", guideInfo));
        } else if (!guideDto.getIsVerified() && !isConfirmed){
            notifyGuide(guideDto);
            return sendMessage(requestDto.getChatId(), String.format("Guide %s rejected to verify", guideInfo));
        } else {
            return sendMessage(requestDto.getChatId(), String.format("Guide %s verification/rejection failed", guideInfo));
        }
    }

    private void notifyGuide(GuideDto guideDto) {
        log.info("Notifying guide {} about verification status", guideDto.getUser().getFullNameOrUsername());
        //prepare url
        var url = "https://api.telegram.org/bot" + GUIDE_BOT_TOKEN + "/sendMessage";
        //prepare header
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //prepare body
        var requestBody = new LinkedMultiValueMap<String, Object>();
        requestBody.add("chat_id", guideDto.getUser().getTelegramId());
        requestBody.add(
                "text",
                guideDto.getIsVerified() ? "Your request has been approved." :
                        "Your request has been rejected. Please contact the administrator for more information - @timurismoilov"
        );
        //send request
        httpHelper.post(url, requestBody, headers);
    }
}
