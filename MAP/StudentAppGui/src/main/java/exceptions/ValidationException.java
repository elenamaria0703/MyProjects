package exceptions;

public class ValidationException extends RuntimeException {
    String msg;

    public ValidationException(String msg) {
        this.msg = msg;
    }

    public ValidationException(String message, String msg) {
        super(message);
        this.msg = msg;
    }

    public ValidationException(String message, Throwable cause, String msg) {
        super(message, cause);
        this.msg = msg;
    }

    public ValidationException(Throwable cause, String msg) {
        super(cause);
        this.msg = msg;
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
