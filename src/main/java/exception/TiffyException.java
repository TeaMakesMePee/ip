package exception;

public class TiffyException extends Exception {
    public enum ExceptionType {
        INVALID_INPUT,
        INVALID_INDEX,
        ZERO_TASK,
        ALREADY_MARKED,
        ALREADY_UNMARKED,
        TASK_NOT_FOUND,
        ZERO_CONTACTS,
        INVALID_TIME_FORMAT,
        INVALID_ARGUMENT,
        INVALID_LIST_TYPE
    }

    private final ExceptionType exceptionType;

    public TiffyException(String message, ExceptionType type) {
        super(message);
        this.exceptionType = type;
    }

    public TiffyException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.exceptionType = type;
    }

    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}