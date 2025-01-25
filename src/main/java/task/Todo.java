package task;

/**
 * Represents a Todo task with a description and a completion status.
 * This class extends the abstract Task class.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo with the specified description.
     * The task is initialized as not done.
     *
     * @param description Description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructs a Todo with the specified description and completion status.
     *
     * @param description Description of the todo task.
     * @param isDone Completion status of the todo task.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the formatted string representation of the todo task.
     *
     * @return Formatted string representing the todo task.
     */
    @Override
    public String getFormattedTask() {
        return "T|" + this.isDone + "|" + this.description;
    }

    /**
     * Returns the string representation of the todo task, including its type,
     * status, and description.
     *
     * @return String representation of the todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}