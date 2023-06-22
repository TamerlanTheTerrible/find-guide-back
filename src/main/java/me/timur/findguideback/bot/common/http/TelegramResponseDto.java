package me.timur.findguideback.bot.common.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramResponseDto<T> {
    String ok;
    @JsonProperty("error_code")
    Integer errorCode;
    String description;
    T result;

    @Override
    public String toString() {
        return "TelegramResponseDto{" +
                "ok='" + ok + '\'' +
                ", errorCode=" + errorCode +
                ", description='" + description + '\'' +
                ", result=" + result +
                '}';
    }
}