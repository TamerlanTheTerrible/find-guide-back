package me.timur.findguideback.bot.common.http;

import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Temurbek Ismoilov on 21/06/23.
 */

@Slf4j
@Service
public class RestTemplateHelper implements HttpHelper {

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<String> get(String url) {
        return sendRequest(url);
    }

    @Override
    public ResponseEntity<String> post(String url, MultiValueMap<String, Object> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return post(url, requestBody, headers);
    }

    @Override
    public ResponseEntity<String> post(String url, MultiValueMap<String, Object> requestBody, HttpHeaders headers) {
        return sendRequest( url, new HttpEntity<>(requestBody, headers));
    }

    private ResponseEntity<String> sendRequest(String url) {
        return sendRequest(url, null);
    }

    private ResponseEntity<String> sendRequest(String url, HttpEntity<MultiValueMap<String, Object>> requestEntity) {
        log.info("HTTP request to {}{}", url, requestEntity != null ? " with body: " + requestEntity.getBody() : "");
        try {
            ResponseEntity<String> responseEntity = requestEntity != null
                    ? restTemplate.postForEntity(url, requestEntity, String.class)
                    : restTemplate.getForEntity(url, String.class);

            log.info("Response from admin bot: {}", responseEntity.getBody());

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            } else {
                log.error("Error while sending request to {}", url);
                throw new FindGuideException(ResponseCode.HTTP_ERROR, "Error while sending request to {}", url);
            }

        } catch (Exception e) {
            log.error("Error while sending request to {}", url);
            throw new FindGuideException(ResponseCode.HTTP_ERROR, "Error while sending request to {}. ERROR: ", url, e.getMessage());
        }
    }
}
