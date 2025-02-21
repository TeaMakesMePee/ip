package exception;

/**
 * Custom exception class for handling various types of errors in the application.
 */
public class TiffyException extends Exception {

    /**
     * Enum representing different types of exceptions that can occur.
     */
    public enum ExceptionType {
        INVALID_INPUT,      // When input does not match expected format
        INVALID_INDEX,      // When an index provided is out of bounds
        ZERO_TASK,          // When there are no tasks available
        ALREADY_MARKED,     // When attempting to mark a task that is already marked
        ALREADY_UNMARKED,   // When attempting to unmark a task that is already unmarked
        TASK_NOT_FOUND,     // When a requested task cannot be found
        ZERO_CONTACTS,      // When there are no contacts available
        INVALID_TIME_FORMAT,// When time format is invalid
        INVALID_ARGUMENT,   // When an invalid argument is passed
        INVALID_LIST_TYPE   // When an unsupported list type is used
    }

    private final ExceptionType exceptionType;

    /**
     * Constructs a TiffyException with a specified message and exception type.
     *
     * @param message The detail message for the exception.
     * @param type    The type of exception.
     */
    public TiffyException(String message, ExceptionType type) {
        super(message);
        this.exceptionType = type;
    }

    /**
     * Constructs a TiffyException with a specified message, exception type, and cause.
     *
     * @param message The detail message for the exception.
     * @param type    The type of exception.
     * @param cause   The underlying cause of the exception.
     */
    public TiffyException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.exceptionType = type;
    }

    /**
     * Returns the type of exception that was thrown.
     *
     * @return The exception type.
     */
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
}