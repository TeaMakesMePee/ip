package task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class.
 */
class EventTest {

    /**
     * Tests the getFormattedTask() method to ensure correct storage formatting.
     */
    @Test
    void testGetFormattedTask() {
        LocalDate fromDate = LocalDate.of(2025, 2, 22);
        LocalDate toDate = LocalDate.of(2025, 2, 23);
        Event event = new Event("Project meeting", fromDate, toDate);

        String expectedFormat = "E|false|Project meeting|2025-02-22|2025-02-23";
        assertEquals(expectedFormat, event.getFormattedTask());
    }

    /**
     * Tests the toString() method to ensure correct display formatting.
     */
    @Test
    void testToString() {
        LocalDate fromDate = LocalDate.of(2025, 2, 22);
        LocalDate toDate = LocalDate.of(2025, 2, 23);
        Event event = new Event("Team retreat", true, fromDate, toDate);

        String expectedString = "[E][X] Team retreat (from: Feb 22 2025 to: Feb 23 2025)";
        assertEquals(expectedString, event.toString());
    }
}