package main.java;
public class TiffyException extends Exception{
    public enum ExceptionType {
        INVALID_INPUT,
        INVALID_INDEX,
        EMPTY_TASK,
        ZERO_TASK
    }

    public TiffyException(String message, ExceptionType type) {
        super(message);
    }

    public TiffyException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
    }
}