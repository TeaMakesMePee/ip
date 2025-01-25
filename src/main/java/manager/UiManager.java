package manager;

import static java.lang.System.in;
import java.util.Scanner;
import java.util.List;
import task.Task;

/**
 * Manages the user interface for interacting with tasks.
 * Provides methods for printing messages, reading commands, and displaying tasks.
 */
public class UiManager {

    /** Enumeration of event types for task-related actions. */
    public enum eventType {
        TASK_MARKED,
        TASK_UNMARKED,
        TASK_ADDED,
        TASK_DELETED,
    }

    /** Scanner object for reading user input. */
    private final Scanner scanner;

    /**
     * Constructs a UiManager instance and initializes the scanner for input.
     */
    private UiManager() {
        this.scanner = new java.util.Scanner(in);
    }

    /**
     * Inner static class for holding the singleton instance of UiManager.
     */
    private static final class InstanceHolder {
        /** Singleton instance of UiManager. */
        private static final UiManager instance = new UiManager();
    }

    /**
     * Returns the singleton instance of UiManager.
     *
     * @return Singleton UiManager instance.
     */
    public static UiManager getInstance() {
        return UiManager.InstanceHolder.instance;
    }

    /**
     * Prints the startup message with ASCII art and a greeting.
     */
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

    /**
     * Prints a goodbye message.
     */
    public void printGoodbyeMessage() {
        System.out.println("Goodbye!");
    }

    /**
     * Prints the exception message to the error stream.
     *
     * @param e The exception to be printed.
     */
    public void printException(Exception e) {
        System.err.println(e.getMessage());
    }

    /**
     * Reads a command from the user.
     *
     * @return The user-entered command as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints feedback to the user about a task-related event.
     *
     * @param task The task involved in the event.
     * @param type The type of event that occurred.
     */
    public void printEventFeedback(Task task, eventType type) {
        switch (type) {
            case TASK_MARKED -> {
                System.out.println("Task marked as done:");
            }
            case TASK_UNMARKED -> {
                System.out.println("Task marked as undone:");
            }
            case TASK_ADDED -> {
                System.out.println("Task added:");
            }
            case TASK_DELETED -> {
                System.out.println("Task deleted:");
            }
        }
        System.out.println(task.toString());
    }

    public void notifyTaskFound() {
        System.out.println("Task(s) we found with your query:");
    }

    /**
     * Prints the total number of tasks.
     *
     * @param size The number of tasks.
     */
    public void printTaskCount(int size) {
        System.out.println("You have " + size + " tasks.");
    }

    /**
     * Prints a list of tasks with their indexes.
     *
     * @param tasks The list of tasks to be printed.
     */
    public void printTasks(List<Task> tasks) {
        int count = 1;
        for (Task task : tasks) {
            System.out.println(count + "." + task.toString());
            count++;
        }
    }
}