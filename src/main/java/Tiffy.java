import java.util.List;
import java.time.LocalDate;

public class Tiffy {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager(DataManager.getInstance().loadTasksFromFile());

        Parser parser = new Parser();

        UiManager.getInstance().printStartupMessage();
        String input = UiManager.getInstance().readCommand();
        while (!input.equals("bye")) {
            try {
                handleRequests(parser, input, taskManager);
            } catch (TiffyException te) {
                UiManager.getInstance().printException(te);
            }
            DataManager.getInstance().saveTasksToFile(taskManager.getTasks());
            input = UiManager.getInstance().readCommand();
        }
        UiManager.getInstance().printGoodbyeMessage();
    }

    public static void markDoneUndone(List<Task> tasks, boolean mark, int index) throws TiffyException {
        try {
            Task temp = tasks.get(index - 1);
            try {
                temp.markDone(mark);
            } catch (TiffyException te) {
                UiManager.getInstance().printException(te);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new TiffyException("Invalid index!",
                    TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    public static void handleRequests(Parser p, String input, TaskManager tm) throws TiffyException {
        String[] partition = p.handleRequests(input);
        switch (partition[0]) {
            case "list" -> {
                if (tm.getTasks().isEmpty()) {
                    throw new TiffyException("You have no tasks! Add some.",
                            TiffyException.ExceptionType.ZERO_TASK);
                }
                UiManager.getInstance().printTasks(tm.getTasks());
            }
            case "todo" -> {
                tm.addTask(new Todo(partition[1]));
            }
            case "deadline" -> {
                tm.addTask(new Deadline(partition[1], LocalDate.parse(partition[2])));
            }
            case "event" -> {
                tm.addTask(new Event(partition[1], LocalDate.parse(partition[2]), LocalDate.parse(partition[3])));
            }
            case "mark" -> {
                try {
                    markDoneUndone(tm.getTasks(), true, Integer.parseInt(partition[1]));
                } catch (TiffyException te) {
                    UiManager.getInstance().printException(te);
                }
            }
            case "unmark" -> {
                try {
                    markDoneUndone(tm.getTasks(), false, Integer.parseInt(partition[1]));
                } catch (TiffyException te) {
                    UiManager.getInstance().printException(te);
                }
            }
            case "delete" -> {
                try {
                    tm.deleteTask(Integer.parseInt(partition[1]) - 1);
                } catch (TiffyException te) {
                    UiManager.getInstance().printException(te);
                }
            }
        }
    }
}