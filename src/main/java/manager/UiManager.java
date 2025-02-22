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
    public enum EventType {
        TASK_MARKED,
        TASK_UNMARKED,
        TASK_ADDED,
        TASK_DELETED,
        CONTACT_DELETED,
    }

    /** Scanner object for reading user input. */
    private final Scanner scanner;

    /**
     * Reference to the main application window.
     * Used for controlling UI interactions.
     */
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
    public void generateEventFeedback(Object obj, EventType type) {
        String output = switch (type) {
            case TASK_MARKED -> "Task marked as done:";
            case TASK_UNMARKED -> "Task marked as undone:";
            case TASK_ADDED -> "Task added:";
            case TASK_DELETED -> "Task deleted:";
            case CONTACT_DELETED -> "Contact deleted:";
        };
        mainWindow.setOutputMessage(output + "\n" + obj.toString() + "\n");
    }

    /**
     * Notifies the user that a task matching the query has been found.
     * Displays a message in the main window.
     */
    public void notifyTaskFound() {
        mainWindow.setOutputMessage("Task(s) we found with your query:\n");
    }

    /**
     * Notifies the user that a new contact has been added.
     * Displays a confirmation message followed by the contact details.
     *
     * @param contact The contact that was added.
     */
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

    /**
     * Sets the reference to the main application window.
     * This allows interaction with the UI from this class.
     *
     * @param mainWindow The main application window instance.
     */
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

    /**
     * Displays a formatted list of contacts in the main application window.
     * Each contact is numbered sequentially.
     *
     * @param contacts The list of contacts to be displayed.
     */
    public void printContacts(List<Contact> contacts) {
        int count = 1;
        StringBuilder output = new StringBuilder();

        // Iterate through each contact and format the output
        for (Contact contact : contacts) {
            output.append(count).append(".\n").append(contact.toString()).append("\n");
            count++;
        }

        // Display the formatted contact list in the main window
        mainWindow.setOutputMessage(output.toString());
    }

    /**
     * Prints a goodbye message.
     */
    public void generateExitMessage() {
        mainWindow.setOutputMessage("Goodbye!");
    }
}