public class TiffyException extends Exception{
    public enum ExceptionType { //enums were added here ahead of time!
        INVALID_INPUT,
        INVALID_INDEX,
        EMPTY_TASK,
        ZERO_TASK,
        ALREADY_MARKED,
        ALREADY_UNMARKED,
    }

    public TiffyException(String message, ExceptionType type) {
        super(message);
    }

    public TiffyException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
    }
}