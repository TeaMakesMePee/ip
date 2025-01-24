import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.in;

public class Tiffy {
    public static void main(String[] args) throws TiffyException {
        Scanner scanner = new Scanner(in);

        DataManager dataManager = DataManager.getInstance();
        List<String> taskStr = dataManager.loadTasksFromFile();
        List<Task> tasks = createTasks(taskStr);

        String asciiArt = """
                 ___________  __     _______   _______  ___  ___\s
                ("     _   ")|" \\   /"     "| /"     "||"  \\/"  |
                 )__/  \\\\__/ ||  | (: ______)(: ______) \\   \\  /\s
                    \\\\_ /    |:  |  \\/    |   \\/    |    \\\\  \\/ \s
                    |.  |    |.  |  // ___)   // ___)    /   /  \s
                    \\:  |    /\\  |\\(:  (     (:  (      /   /   \s
                     \\__|   (__\\_|_)\\__/      \\__/     |___/    \s
        """;
        System.out.println(asciiArt);
        System.out.println("""
                Hi! I'm Tiffy.
                What can I do for you?
                """);
        String bye = "Bye. Hope to see you again soon!";

        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            try {
                handleRequests(input, tasks);
            } catch (TiffyException te) {
                System.out.println(te.toString());
            }
            dataManager.saveTasksToFile(tasks);
            input = scanner.nextLine();
        }
        System.out.println(bye);
    }

    public static void markDoneUndone(List<Task> tasks, boolean mark, int index) throws TiffyException {
        try {
            Task temp = tasks.get(index - 1);
            if (temp.getStatusIcon().equals(mark ? "X" : " ")) {
                System.out.println(mark ? "Task already marked!" : "Task has not been marked!");
            } else {
                if (mark) {
                    temp.markDone();
                } else {
                    temp.unmarkDone();
                }
                System.out.println("Task has been marked as " + (mark ? "done:" : "not done:"));
                System.out.println(temp.toString());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new TiffyException("Invalid index!",
                    TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    public static void notifyTaskAdded(Task t, int size) {
        System.out.println("Task added:\n" +
                t.toString() + "\n"
                + "You have " + size + " tasks.");
    }

    public static void deleteTask(List<Task> t, int index) throws TiffyException {
        try {
            String taskString = t.get(index - 1).toString();
            t.remove(index - 1);
            System.out.println("Noted. I've removed this task:\n"
                    + taskString + "\nYou have " + t.size() + " tasks left.");
        } catch (IndexOutOfBoundsException e) {
            throw new TiffyException("Invalid index!",
                    TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    public static void handleRequests(String input, List<Task> tasks) throws TiffyException {
        String[] partition = input.split(" ");
        switch (partition[0]) {
            case "list" -> {
                if (tasks.isEmpty()) {
                    throw new TiffyException("You have no tasks! Add some.",
                            TiffyException.ExceptionType.ZERO_TASK);
                }
                int count = 1;
                for (Task t : tasks) {
                    System.out.println(count + "." + t.toString());
                    count++;
                }
            }
            case "todo" -> {
                String task = input.replaceFirst("todo ", "");
                if (task.isBlank()) {
                    throw new TiffyException("Adding empty tasks to feel productive?",
                            TiffyException.ExceptionType.EMPTY_TASK);
                }
                Todo td = new Todo(task);
                tasks.add(td);
                notifyTaskAdded(td, tasks.size());
            }
            case "deadline" -> {
                String[] parts = input.replaceFirst("deadline ", "").split(" /by ");
                if (parts.length < 2 || parts[0].isBlank()) {
                    throw new TiffyException("I'm afraid that's an invalid request.",
                            TiffyException.ExceptionType.INVALID_INPUT);
                }
                Deadline d = new Deadline(parts[0], parts[1]);
                tasks.add(d);
                notifyTaskAdded(d, tasks.size());
            }
            case "event" -> {
                String[] parts = input.replaceFirst("event ", "").split(" /from | /to ");
                if (parts.length < 3 || parts[0].isBlank()) {
                    throw new TiffyException("I'm afraid that's an invalid request.",
                            TiffyException.ExceptionType.INVALID_INPUT);
                }
                Event e = new Event(parts[0], parts[1], parts[2]);
                tasks.add(e);
                notifyTaskAdded(e, tasks.size());
            }
            case "mark" -> {
                int index = Integer.parseInt(partition[1]);
                try {
                    markDoneUndone(tasks, true, index);
                } catch (TiffyException te) {
                    System.out.println(te.toString());
                }
            }
            case "unmark" -> {
                int index = Integer.parseInt(partition[1]);
                try {
                    markDoneUndone(tasks, false, index);
                } catch (TiffyException te) {
                    System.out.println(te.toString());
                }
            }
            case "delete" -> {
                int index = Integer.parseInt(partition[1]);
                try {
                    deleteTask(tasks, index);
                } catch (TiffyException te) {
                    System.out.println(te.toString());
                }
            }
            default -> {
                throw new TiffyException("I'm afraid that's an invalid request.",
                        TiffyException.ExceptionType.INVALID_INPUT);
            }
        }
    }

    public static List<Task> createTasks(List<String> loadedData) {
        List<Task> tasks = new ArrayList<>();
        for (String s : loadedData) {
            String[] parts = s.split("\\|");
            switch (parts[0]) {
                case "E" -> {
                    Event e = new Event(parts[2], parts[1].equals("true"), parts[3], parts[4]);
                    tasks.add(e);
                }
                case "D" -> {
                    Deadline d = new Deadline(parts[2], parts[1].equals("true"), parts[3]);
                    tasks.add(d);
                }
                case "T" -> {
                    Todo t = new Todo(parts[2], parts[1].equals("true"));
                    tasks.add(t);
                }
            }
        }
        return tasks;
    }
}