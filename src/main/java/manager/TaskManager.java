package manager;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import task.Task;

public class TaskManager {
    private final List<task.Task> tasks;

    public TaskManager(List<String> taskData) {
        this.tasks = new ArrayList<>();
        for (String s : taskData) {
            String[] parts = s.split("\\|");
            switch (parts[0]) {
                case "E" -> {
                    task.Event e = new task.Event(parts[2], parts[1].equals("true"), java.time.LocalDate.parse(parts[3]), java.time.LocalDate.parse(parts[4]));
                    this.tasks.add(e);
                }
                case "D" -> {
                    try {
                        task.Deadline d = new task.Deadline(parts[2], parts[1].equals("true"), java.time.LocalDate.parse(parts[3]));
                        this.tasks.add(d);
                    } catch (Exception e) {
                        UiManager.getInstance().printException(e);
                    }
                }
                case "T" -> {
                    task.Todo t = new task.Todo(parts[2], parts[1].equals("true"));
                    this.tasks.add(t);
                }
            }
        }
    }

    public void deleteTask(int index) throws exception.TiffyException {
        try {
            UiManager.getInstance().printEventFeedback(this.tasks.get(index), UiManager.eventType.TASK_DELETED);
            this.tasks.remove(index);
            UiManager.getInstance().printTaskCount(this.tasks.size());
        } catch (IndexOutOfBoundsException e) {
            throw new exception.TiffyException("Invalid index!",
                    exception.TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    public void addTask(task.Task task) {
        this.tasks.add(task);
        UiManager.getInstance().printEventFeedback(task, UiManager.eventType.TASK_ADDED);
        UiManager.getInstance().printTaskCount(this.tasks.size());
    }

    public List<task.Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }

    public List<Task> findTasks(String keyword) throws exception.TiffyException {
        List<Task> temp = this.tasks.stream()
                .filter(x -> x.getDescription()
                        .contains(keyword))
                .toList();
        if (temp.isEmpty()) {
            throw new exception.TiffyException("Task you're looking for is not found.",
                    exception.TiffyException.ExceptionType.TASK_NOT_FOUND);
        }
        UiManager.getInstance().notifyTaskFound();
        return temp;
    }
}
