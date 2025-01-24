import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
public class TaskManager {
    private final List<Task> tasks;

    public TaskManager(List<String> taskData) {
        this.tasks = new ArrayList<>();
        for (String s : taskData) {
            String[] parts = s.split("\\|");
            switch (parts[0]) {
                case "E" -> {
                    Event e = new Event(parts[2], parts[1].equals("true"), java.time.LocalDate.parse(parts[3]), java.time.LocalDate.parse(parts[4]));
                    this.tasks.add(e);
                }
                case "D" -> {
                    try {
                        Deadline d = new Deadline(parts[2], parts[1].equals("true"), java.time.LocalDate.parse(parts[3]));
                        this.tasks.add(d);
                    } catch (Exception e) {
                        UiManager.getInstance().printException(e);
                    }
                }
                case "T" -> {
                    Todo t = new Todo(parts[2], parts[1].equals("true"));
                    this.tasks.add(t);
                }
            }
        }
    }

    public void deleteTask(int index) throws TiffyException {
        try {
            UiManager.getInstance().printEventFeedback(this.tasks.get(index), UiManager.eventType.TASK_DELETED);
            this.tasks.remove(index);
            UiManager.getInstance().printTaskCount(this.tasks.size());
        } catch (IndexOutOfBoundsException e) {
            throw new TiffyException("Invalid index!",
                    TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        UiManager.getInstance().printEventFeedback(task, UiManager.eventType.TASK_ADDED);
        UiManager.getInstance().printTaskCount(this.tasks.size());
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }
}
