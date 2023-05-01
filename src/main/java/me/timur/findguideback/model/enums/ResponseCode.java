package me.timur.findguideback.model.enums;

/**
 * Created by Temurbek Ismoilov on 03/09/22.
 *
 * 1-99: Success
 * 100-199: Internal errors
 * 200-299: Client errors
 * 300-399: External billing errors
 *
 */

public enum ResponseCode {
    OK(1),

    INTERNAL_ERROR(100),
    NOT_FOUND(201),
    INVALID_PARAMETERS(202),

    FEIGN_EXCEPTION(300)
    ;

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ResponseCode findByCode(int code) {
        final ResponseCode[] values = ResponseCode.values();
        for (ResponseCode responseCode : values) {
            if (responseCode.getCode() == code) {
                return responseCode;
            }
        }
        return null;
    }
}
