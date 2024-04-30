package info.smartfactory.global.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@Builder
public class ResultResponse<T> {
    private HttpStatus statusCode;
    private String resultMsg;
    private T resultData;
    private int error; // 에러 정보

    public ResultResponse(final HttpStatus statusCode, final String resultMsg) {
        this.statusCode = statusCode;
        this.resultMsg = resultMsg;
        this.resultData = null;
    }

    public static <T> ResultResponse<T> res(final HttpStatus statusCode, final String resultMsg) {
        return res(statusCode, resultMsg, null);
    }

    public static <T> ResultResponse<T> res(final HttpStatus statusCode, final String resultMsg, final T t) {
        return ResultResponse.<T>builder()
                .resultData(t)
                .statusCode(statusCode)
                .resultMsg(resultMsg)
                .build();
    }

    // 400번대 에러 코드 포함 생성자
    public ResultResponse(final HttpStatus statusCode, final String resultMsg, final int error) {
        this.statusCode = statusCode;
        this.resultMsg = resultMsg;
        this.resultData = null;
        this.error = error;
    }

    public static <T> ResponseEntity<ResultResponse<T>> errRes(HttpStatus status, String message, T data, int errorCode) {
        if (status == HttpStatus.BAD_REQUEST) {
            return ResponseEntity.badRequest().body(new ResultResponse<>(status, message, data, errorCode));
        } else {
            return ResponseEntity.status(status).body(new ResultResponse<>(status, message, data, errorCode));
        }
    }

}
