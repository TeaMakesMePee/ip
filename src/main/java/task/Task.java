package task;

import exception.TiffyException;
import manager.UiManager;

/**
 * Represents an abstract Task with a description and a completion status.
 * This class serves as a base for specific types of tasks.
 */
public abstract class Task {

    /** Description of the task. */
    protected String description;

    /** Completion status of the task. */
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description.
     * The task is initialized as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a Task with the specified description and completion status.
     *
     * @param description Description of the task.
     * @param isDone Completion status of the task.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the formatted string representation of the task.
     *
     * @return Formatted task string.
     */
    public abstract String getFormattedTask();

    /**
     * Returns the status icon of the task.
     * "X" if the task is done, otherwise a blank space.
     *
     * @return Status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done or not done and provides feedback.
     * Throws an exception if the task is already in the specified state.
     *
     * @param done True to mark the task as done, false to mark it as not done.
     * @throws TiffyException If the task is already in the specified state.
     */
    public void markDone(boolean done) throws TiffyException {
        if (this.isDone == done) {
            throw new TiffyException(
                    done ? "Task already marked!" : "Task already marked not done!",
                    done ? TiffyException.ExceptionType.ALREADY_MARKED : TiffyException.ExceptionType.ALREADY_UNMARKED
            );
        }
        this.isDone = done;
        UiManager.getInstance().generateEventFeedback(this,
                done ? UiManager.EventType.TASK_MARKED : UiManager.EventType.TASK_UNMARKED);
    }

    /**
     * Returns the description of the task.
     *
     * @return Description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the string representation of the task, including its status icon
     * and description.
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}