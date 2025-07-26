package exception;

public class BizException extends RuntimeException {
    private final int code;

    public BizException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}
