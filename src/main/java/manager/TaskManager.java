package manager;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import exception.TiffyException;
import task.Task;
import task.Todo;
import task.Event;
import task.Deadline;

/**
 * Manages a collection of tasks, including adding, deleting, and retrieving tasks.
 */
public class TaskManager {

    /** List of tasks managed by this TaskManager. */
    private final List<Task> tasks;

    /**
     * Constructs a TaskManager with tasks initialized from serialized data.
     *
     * @param taskData List of serialized task data strings.
     */
    public TaskManager(List<String> taskData) {
        this.tasks = new ArrayList<>();
        for (String s : taskData) {
            String[] parts = s.split("\\|");
            switch (parts[0]) {
                case "E" -> {
                    Event event = new Event(parts[2], parts[1].equals("true"),
                            java.time.LocalDate.parse(parts[3]), java.time.LocalDate.parse(parts[4]));
                    this.tasks.add(event);
                }
                case "D" -> {
                    try {
                        Deadline deadline = new Deadline(parts[2], parts[1].equals("true"),
                                java.time.LocalDate.parse(parts[3]));
                        this.tasks.add(deadline);
                    } catch (Exception e) {
                        UiManager.getInstance().printException(e);
                    }
                }
                case "T" -> {
                    Todo todo = new Todo(parts[2], parts[1].equals("true"));
                    this.tasks.add(todo);
                }
            }
        }
    }

    /**
     * Deletes a task from the task list at the specified index.
     *
     * @param index Index of the task to be deleted.
     * @throws TiffyException If the index is invalid.
     */
    public void deleteTask(int index) throws TiffyException {
        assert index >= 0 && index < tasks.size() : "Invalid task index: " + index;

        try {
            UiManager.getInstance().printEventFeedback(this.tasks.get(index), UiManager.eventType.TASK_DELETED);
            this.tasks.remove(index);
            UiManager.getInstance().printTaskCount(this.tasks.size());
        } catch (IndexOutOfBoundsException e) {
            throw new TiffyException("Invalid index!",
                    TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
        UiManager.getInstance().printEventFeedback(task, UiManager.eventType.TASK_ADDED);
        UiManager.getInstance().printTaskCount(this.tasks.size());
    }

    /**
     * Returns an unmodifiable view of the task list.
     *
     * @return Unmodifiable list of tasks.
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }

    public List<Task> findTasks(String keyword) throws TiffyException {
        List<Task> foundTasks = this.tasks.stream()
                .filter(x -> x.getDescription().contains(keyword))
                .toList();

        if (foundTasks.isEmpty()) {
            throw new TiffyException("Task you're looking for is not found.",
                    TiffyException.ExceptionType.TASK_NOT_FOUND);
        }

        UiManager.getInstance().notifyTaskFound();
        return foundTasks;
    }
}
