package me.timur.findguideback.exception;

import lombok.Getter;
import lombok.Setter;
import me.timur.findguideback.model.enums.ResponseCode;

/**
 * Created by Temurbek Ismoilov on 10/04/23.
 */

@Getter
@Setter
public class FindGuideException extends RuntimeException {
    private ResponseCode code;
    private String message;

    public FindGuideException(ResponseCode code, String message) {
        this.message = message;
        this.code = code;
    }

    public FindGuideException(ResponseCode code, String message, Object... vars) {
        this.message = String.format(message, vars);
        this.code = code;
    }
}
