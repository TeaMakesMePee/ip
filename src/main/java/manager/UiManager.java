package manager;

import static java.lang.System.in;
import java.util.Scanner;
import java.util.List;

public class UiManager {
    public enum eventType { //enums were added here ahead of time!
        TASK_MARKED,
        TASK_UNMARKED,
        TASK_ADDED,
        TASK_DELETED,
    }

    private final Scanner scanner;
    private UiManager() {
        this.scanner = new java.util.Scanner(in);
    }

    private static final class InstanceHolder {
        private static final UiManager instance = new UiManager();
    }

    public static UiManager getInstance() {
        return UiManager.InstanceHolder.instance;
    }

    public void printStartupMessage() {
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
    }

    public void printGoodbyeMessage() {
        System.out.println("Goodbye!");
    }

    public void printException(Exception e) {
        System.err.println(e.getMessage());
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void printEventFeedback(task.Task task, eventType type) {
        switch (type) {
            case TASK_MARKED -> {
                System.out.println("task.Task marked as done:");
            }
            case TASK_UNMARKED -> {
                System.out.println("task.Task marked as undone:");
            }
            case TASK_ADDED -> {
                System.out.println("task.Task added:");
            }
            case TASK_DELETED -> {
                System.out.println("task.Task deleted:");
            }
        }
        System.out.println(task.toString());
    }

    public void notifyTaskFound() {
        System.out.println("Task(s) we found with your query:");
    }

    public void printTaskCount(int size) {
        System.out.println("You have " + size + " tasks.");
    }

    public void printTasks(List<task.Task> tasks) {
        int count = 1;
        for (task.Task task : tasks) {
            System.out.println(count + "." + task.toString());
            count++;
        }
    }
}