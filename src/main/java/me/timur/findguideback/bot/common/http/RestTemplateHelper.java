package me.timur.findguideback.bot.common.http;

import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Temurbek Ismoilov on 21/06/23.
 */

@Slf4j
@Service
public class RestTemplateHelper<T> implements HttpHelper {

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<String> get(String url) {
        log.info("GET request to {}", url);
        try {
            final ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            } else {
                log.error("Error while sending request to {}", url);
                throw new FindGuideException(ResponseCode.HTTP_ERROR, "Error while sending request to {}", url);
            }
        } catch (Exception e) {
            log.error("Error while sending request to {}", url);
            throw new FindGuideException(ResponseCode.HTTP_ERROR, "Error while sending request to {}. ERROR: ", url, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> sendResource(String url, String chatId, Resource resource) {
        log.info("POST request to {}", url);
        try {

//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("photo", resource);
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//            return restTemplate.postForEntity(url, requestEntity, String.class);

            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("chat_id", chatId);
            requestBody.add("photo", new FileSystemResource(new java.io.File(resource.getFile().getPath())));

            // Create headers with Content-Type as multipart/form-data
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create the HTTP entity with request body and headers
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            return restTemplate.postForEntity(url, requestEntity, String.class);
        } catch (Exception e) {
            log.error("Error while sending request to {}", url);
            throw new FindGuideException(ResponseCode.HTTP_ERROR, "Error while sending request to {}. ERROR: ", url, e.getMessage());
        }

    }
}
