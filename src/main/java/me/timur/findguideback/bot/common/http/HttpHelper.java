package me.timur.findguideback.bot.common.http;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 * Created by Temurbek Ismoilov on 21/06/23.
 */

public interface HttpHelper {
    ResponseEntity<String> get(String url);
    ResponseEntity<String> sendResource(String url, String chatId, Resource resource);
}
