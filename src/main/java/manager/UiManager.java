package manager;

import static java.lang.System.in;
import java.util.Scanner;
import java.util.List;
import task.Task;
import gui.MainWindow;
import contacts.Contact;

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

    private MainWindow mainWindow;

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
    public void greetUser() {
        mainWindow.setOutputMessage("""
                Hi! I'm Tiffy.
                What can I do for you?
                """);
        mainWindow.flushBuffer();
    }

    /**
     * Prints the exception message to the error stream.
     *
     * @param e The exception to be printed.
     */
    public void printException(Exception e) {
        mainWindow.toggleError(true);
        mainWindow.setOutputMessage(e.getMessage());
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
    public void generateEventFeedback(Task task, eventType type) {
        String output = "";
        switch (type) {
            case TASK_MARKED -> {
                output += "Task marked as done:";
            }
            case TASK_UNMARKED -> {
                output += "Task marked as undone:";
            }
            case TASK_ADDED -> {
                output += "Task added:";
            }
            case TASK_DELETED -> {
                output += "Task deleted:";
            }
        }
        mainWindow.setOutputMessage(output + "\n" + task.toString() + "\n");
    }

    public void notifyTaskFound() {
        mainWindow.setOutputMessage("Task(s) we found with your query:\n");
    }

    public void notifyContactAdded(Contact contact) {
        mainWindow.setOutputMessage("Contact has been added!\n");
        mainWindow.setOutputMessage(contact.toString());
    }

    /**
     * Prints the total number of tasks.
     *
     * @param size The number of tasks.
     */
    public void printTaskCount(int size) {
        mainWindow.setOutputMessage("You have " + size + " tasks.\n");
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    /**
     * Prints a list of tasks with their indexes.
     *
     * @param tasks The list of tasks to be printed.
     */
    public void printTasks(List<Task> tasks) {
        int count = 1;
        StringBuilder output = new StringBuilder();
        for (Task task : tasks) {
            output.append(count).append(". ").append(task.toString()).append("\n");
            count++;
        }
        mainWindow.setOutputMessage(output.toString());
    }

    public void printContacts(List<Contact> contacts) {
        int count = 1;
        StringBuilder output = new StringBuilder();
        for (Contact contact : contacts) {
            output.append(count).append(".\n").append(contact.toString()).append("\n");
            count++;
        }
        mainWindow.setOutputMessage(output.toString());
    }

    /**
     * Prints a goodbye message.
     */
    public void generateExitMessage() {
        mainWindow.setOutputMessage("Goodbye!");
    }
}