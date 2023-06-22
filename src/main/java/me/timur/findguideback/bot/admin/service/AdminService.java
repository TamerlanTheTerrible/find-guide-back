package me.timur.findguideback.bot.admin.service;

import me.timur.findguideback.bot.common.model.dto.RequestDto;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 21/06/23.
 */

public interface AdminService {
    List<BotApiMethod<? extends Serializable>> confirmGuide(RequestDto requestDto);
}
