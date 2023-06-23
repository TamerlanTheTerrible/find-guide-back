package me.timur.findguideback.bot.common.http;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * Created by Temurbek Ismoilov on 21/06/23.
 */

public interface HttpHelper {
    ResponseEntity<String> get(String url);
    ResponseEntity<String> sendResource(String url, MultiValueMap<String, Object> requestBody);
}
