package me.timur.findguideback.bot.common.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * Created by Temurbek Ismoilov on 21/06/23.
 */

public interface HttpHelper {
    ResponseEntity<String> get(String url);
    ResponseEntity<String> post(String url, MultiValueMap<String, Object> requestBody);

    ResponseEntity<String> post(String url, MultiValueMap<String, Object> requestBody, HttpHeaders headers);
}
