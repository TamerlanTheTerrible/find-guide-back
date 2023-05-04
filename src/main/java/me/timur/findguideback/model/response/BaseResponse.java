package me.timur.findguideback.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.timur.findguideback.model.enums.ResponseCode;

/**
 * Created by Temurbek Ismoilov on 28/02/23.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private T payload = null;
    private Integer code;
    private String message;

    public static <T> BaseResponse<T> of(T payload){
        return BaseResponse.<T>builder()
                .payload(payload)
                .code(ResponseCode.OK.getCode())
                .message(ResponseCode.OK.name())
                .build();
    }

    public static BaseResponse<NoopDTO> empty(){
        return BaseResponse.<NoopDTO>builder()
                .payload(NoopDTO.INSTANCE)
                .code(ResponseCode.OK.getCode())
                .message(ResponseCode.OK.name())
                .build();
    }

    public static <T>BaseResponse<T> of(ResponseCode responseCode, String details) {
        return BaseResponse.<T>builder()
                .code(responseCode.getCode())
                .message(details)
                .build();
    }
}
