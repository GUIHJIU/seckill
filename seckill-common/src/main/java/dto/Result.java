package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(200)
                .msg("success")
                .data(data)
                .build();
    }

    public static Result<Void> error(int code, String msg) {
        return Result.<Void>builder()
                .code(code)
                .msg(msg)
                .build();
    }
}
