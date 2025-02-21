package utility;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import exception.TiffyException;
import manager.UiManager;
import manager.DataManager;
import manager.TaskManager;
import task.Task;
import task.Todo;
import task.Event;
import task.Deadline;
import contacts.Contact;
import manager.ContactManager;

/**
 * Handles processing of user requests and delegates tasks to relevant managers.
 */
public class RequestHandler {
    private static final ContactManager contactManager = new ContactManager(DataManager.getInstance().loadContactsFromFile());
    private static final TaskManager taskManager = new TaskManager(DataManager.getInstance().loadTasksFromFile());
    private final Parser parser;

    /**
     * Singleton instance holder for RequestHandler.
     */
    private static final class InstanceHolder {
        private static final RequestHandler instance = new RequestHandler();
    }

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private RequestHandler() {
        this.parser = new Parser();
    }

    /**
     * Returns the singleton instance of RequestHandler.
     *
     * @return The singleton instance of RequestHandler.
     */
    public static RequestHandler getInstance() {
        return RequestHandler.InstanceHolder.instance;
    }

    /**
     * Processes the user request and delegates the command accordingly.
     *
     * @param request The user's input command.
     * @throws TiffyException If the request is invalid.
     */
    public void handleRequest(String request) throws TiffyException {
        String[] parsedInput = this.parser.splitRequest(request);
        String command = parsedInput[0];

        switch (command) {
            case "event", "todo", "deadline" -> addTask(command, parsedInput);
            case "mark", "unmark" -> markDoneUndone(parsedInput);
            case "list" -> handleListEvent(parsedInput[1]);
            case "delete" -> handleDeleteEvent(parsedInput);
            case "find" -> findTasks(parsedInput[1]);
            case "contact" -> addContact(parsedInput);
            case "bye" -> saveAndExit();
        }
    }

    /**
     * Adds a task of the specified type (todo, event, deadline).
     *
     * @param type        The type of task to add.
     * @param parsedInput The parsed user input.
     * @throws TiffyException If the task data is invalid.
     */
    private void addTask(String type, String[] parsedInput) throws TiffyException {
        try {
            switch (type) {
                case "todo" -> taskManager.addTask(new Todo(parsedInput[1]));
                case "event" -> taskManager.addTask(new Event(parsedInput[1],
                        LocalDate.parse(parsedInput[2]),
                        LocalDate.parse(parsedInput[3])));
                case "deadline" -> taskManager.addTask(new Deadline(parsedInput[1],
                        LocalDate.parse(parsedInput[2])));
            }
        } catch (DateTimeParseException exception) {
            throw new TiffyException("Wrong time format! Use: YYYY-MM-DD format.",
                    TiffyException.ExceptionType.INVALID_TIME_FORMAT);
        }
    }

    /**
     * Marks or unmarks a task based on user input.
     *
     * @param parsedInput The parsed user input.
     * @throws TiffyException If the index is invalid.
     */
    private void markDoneUndone(String[] parsedInput) throws TiffyException {
        List<Task> tasks = taskManager.getTasks();
        int index = Integer.parseInt(parsedInput[1]);
        assert index > 0 && index <= tasks.size() : "Invalid task index: " + index;

        try {
            Task task = tasks.get(index - 1);
            task.markDone(parsedInput[0].equals("mark"));
        } catch (IndexOutOfBoundsException e) {
            throw new TiffyException("Invalid index!",
                    TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    /**
     * Creates a contact from parsed user input.
     *
     * @param parsedInput The parsed user input.
     * @return A new Contact instance.
     */
    private Contact createContact(String[] parsedInput) {
        return (parsedInput.length == 4)
                ? new Contact(parsedInput[1], parsedInput[2], parsedInput[3])
                : new Contact(parsedInput[1], parsedInput[2]);
    }

    /**
     * Adds a contact and notifies the UI.
     *
     * @param parsedInput The parsed user input.
     */
    private void addContact(String[] parsedInput) {
        Contact contact = createContact(parsedInput);
        contactManager.addContact(contact);
        UiManager.getInstance().notifyContactAdded(contact);
    }

    /**
     * Saves tasks and contacts to file, then exits the program.
     */
    private void saveAndExit() {
        DataManager.getInstance().saveTasksToFile(taskManager.getTasks());
        DataManager.getInstance().saveContactsToFile(contactManager.getContacts());
        UiManager.getInstance().generateExitMessage();
    }

    /**
     * Lists all tasks stored in the task manager.
     *
     * @throws TiffyException If no tasks exist.
     */
    private void listTasks() throws TiffyException {
        List<Task> tasks = taskManager.getTasks();
        if (tasks.isEmpty()) {
            throw new TiffyException("You have no tasks.",
                    TiffyException.ExceptionType.ZERO_TASK);
        }
        UiManager.getInstance().printTasks(tasks);
    }

    /**
     * Lists all contacts stored in the contact manager.
     *
     * @throws TiffyException If no contacts exist.
     */
    private void listContacts() throws TiffyException {
        List<Contact> contacts = contactManager.getContacts();
        if (contacts.isEmpty()) {
            throw new TiffyException("You have no contacts.",
                    TiffyException.ExceptionType.ZERO_CONTACTS);
        }
        UiManager.getInstance().printContacts(contacts);
    }

    /**
     * Handles listing of either tasks or contacts based on user input.
     *
     * @param listType The type of list requested ("tasks" or "contacts").
     * @throws TiffyException If the list type is invalid.
     */
    private void handleListEvent(String listType) throws TiffyException {
        switch (listType) {
            case "tasks" -> listTasks();
            case "contacts" -> listContacts();
            default -> throw new TiffyException("You can either list 'tasks' or 'contacts'.",
                    TiffyException.ExceptionType.INVALID_LIST_TYPE);
        }
    }

    /**
     * Handles deletion of tasks or contacts.
     *
     * @param parsedInput The parsed user input.
     * @throws TiffyException If the input format is invalid.
     */
    private void handleDeleteEvent(String[] parsedInput) throws TiffyException {
        switch (parsedInput[1]) {
            case "task" -> taskManager.deleteTask(Integer.parseInt(parsedInput[2]) - 1);
            case "contact" -> contactManager.deleteContact(Integer.parseInt(parsedInput[2]) - 1);
        }
    }

    /**
     * Searches for tasks based on the provided keyword.
     *
     * @param searchKey The keyword used for searching tasks.
     * @throws TiffyException If no tasks match the search.
     */
    private void findTasks(String searchKey) throws TiffyException {
        List<Task> foundTasks = taskManager.findTasks(searchKey);
        UiManager.getInstance().printTasks(foundTasks);
    }
}