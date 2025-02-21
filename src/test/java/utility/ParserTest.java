package utility;

import exception.TiffyException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private final Parser parser = new Parser();

    /**
     * Tests the private splitRequest method using reflection.
     */
    @Test
    void testSplitRequestUsingReflection() throws Exception {
        Method method = Parser.class.getDeclaredMethod("splitRequest", String.class);
        method.setAccessible(true); // Allow access to private method

        // Test valid "todo" command
        String[] result = (String[]) method.invoke(parser, "todo Buy milk");
        assertArrayEquals(new String[]{"todo", "Buy milk"}, result);

        // Test valid "deadline" command
        String[] deadlineResult = (String[]) method.invoke(parser, "deadline Submit report /by 2025-02-22");
        assertArrayEquals(new String[]{"deadline", "Submit report", "2025-02-22"}, deadlineResult);

        // Test invalid command
        Exception exception = assertThrows(Exception.class, () -> method.invoke(parser, "invalidcommand argument1"));
        Throwable cause = exception.getCause(); // Get the actual exception thrown
        assertInstanceOf(TiffyException.class, cause);
        assertEquals("I'm afraid that's an invalid request.", cause.getMessage());
    }

    /**
     * Tests the private validateCommand method using reflection.
     */
    @Test
    void testValidateCommandUsingReflection() throws Exception {
        Method method = Parser.class.getDeclaredMethod("validateCommand", String.class, String[].class);
        method.setAccessible(true); // Allow access to private method

        // Valid case: "todo" should have 2 arguments
        assertDoesNotThrow(() -> method.invoke(parser, "todo", new String[]{"todo", "Read book"}));

        // Invalid case: "todo" with too few arguments
        Exception exception = assertThrows(Exception.class, () -> method.invoke(parser, "todo", new String[]{"todo"}));

        Throwable cause = exception.getCause(); // Get the actual exception thrown
        assertInstanceOf(TiffyException.class, cause);
        assertEquals("Invalid command syntax.", cause.getMessage());

        // Invalid case: "deadline" with missing argument
        Exception deadlineException = assertThrows(Exception.class, () ->
                method.invoke(parser, "deadline", new String[]{"Submit report"}));

        Throwable deadlineCause = deadlineException.getCause();
        assertInstanceOf(TiffyException.class, deadlineCause);
        assertEquals("Invalid command syntax.", deadlineCause.getMessage());
    }
}