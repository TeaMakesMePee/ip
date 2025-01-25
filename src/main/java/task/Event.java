package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a description and a time period (from and to).
 * This class extends the abstract Task class.
 */
public class Event extends Task {

    /** The start date of the event. */
    protected LocalDate from;

    /** The end date of the event. */
    protected LocalDate to;

    /**
     * Constructs an Event with the specified description, start date, and end date.
     * The event is initialized as not done.
     *
     * @param description Description of the event.
     * @param from Start date of the event.
     * @param to End date of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an Event with the specified description, completion status, start date, and end date.
     *
     * @param description Description of the event.
     * @param isDone Completion status of the event.
     * @param from Start date of the event.
     * @param to End date of the event.
     */
    public Event(String description, boolean isDone, LocalDate from, LocalDate to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the formatted string representation of the event task.
     *
     * @return Formatted string representing the event.
     */
    @Override
    public String getFormattedTask() {
        return "E|" + this.isDone + "|" + this.description + "|" + this.from + "|" + this.to;
    }

    /**
     * Returns the string representation of the event, including its type, status,
     * description, and date range.
     *
     * @return String representation of the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.from.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}