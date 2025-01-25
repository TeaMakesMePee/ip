package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a description and a due date.
 * This class extends the abstract Task class.
 */
public class Deadline extends Task {

    /** The due date of the deadline task. */
    protected LocalDate by;

    /**
     * Constructs a Deadline with the specified description and due date.
     * The task is initialized as not done.
     *
     * @param description Description of the deadline task.
     * @param by Due date of the deadline task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a Deadline with the specified description, completion status, and due date.
     *
     * @param description Description of the deadline task.
     * @param isDone Completion status of the deadline task.
     * @param by Due date of the deadline task.
     */
    public Deadline(String description, boolean isDone, LocalDate by) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Returns the formatted string representation of the deadline task.
     *
     * @return Formatted string representing the deadline task.
     */
    @Override
    public String getFormattedTask() {
        return "D|" + this.isDone + "|" + this.description + "|" + this.by;
    }

    /**
     * Returns the string representation of the deadline task, including its type,
     * status, description, and due date.
     *
     * @return String representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}