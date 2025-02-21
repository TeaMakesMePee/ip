package utility;

import java.time.LocalDate;
import java.util.List;
import java.time.format.DateTimeParseException;
import exception.TiffyException;
import manager.DataManager;
import manager.TaskManager;
import manager.UiManager;
import task.Task;
import task.Todo;
import task.Event;
import task.Deadline;
import contacts.Contact;
import manager.ContactManager;

public class RequestHandler {
    private static final ContactManager contactManager = new ContactManager(DataManager.getInstance().loadContactsFromFile());
    private static final TaskManager taskManager = new TaskManager(DataManager.getInstance().loadTasksFromFile());
    private final Parser parser;

    private static final class InstanceHolder {
        private static final RequestHandler instance = new RequestHandler();
    }

    private RequestHandler() {
        this.parser = new Parser();
    }

    public static RequestHandler getInstance() {
        return RequestHandler.InstanceHolder.instance;
    }

    public void handleRequest(String request) throws TiffyException {
        String[] parsedInput = this.parser.splitRequest(request);
        String command = parsedInput[0];

        switch (command) {
            case "event", "todo", "deadline" -> {
                addTask(command, parsedInput);
            }
            case "mark", "unmark" -> {
                markDoneUndone(parsedInput);
            }
            case "list" -> {
                handleListEvent(parsedInput[1]);
            }
            case "delete" -> {
                handleDeleteEvent(parsedInput);
            }
            case "find" -> {
                findTasks(parsedInput[1]);
            }
            case "contact" -> {
                addContact(parsedInput);
            }
            case "bye" -> {
                saveAndExit();
            }
        }
    }

    private void addTask(String type, String[] parsedInput) throws TiffyException {
        try {
            switch (type) {
                case "todo" -> {
                    taskManager.addTask(new Todo(parsedInput[1]));
                }
                case "event" -> {
                    taskManager.addTask(new Event(parsedInput[1],
                            LocalDate.parse(parsedInput[2]),
                            LocalDate.parse(parsedInput[3])));
                }
                case "deadline" -> {
                    taskManager.addTask(new Deadline(parsedInput[1],
                            LocalDate.parse(parsedInput[2])));
                }
            }
        } catch (DateTimeParseException exception) { //dont catch this here but in parser.java
            throw new TiffyException("Wrong time format! Use: YYYY-MM-DD format.",
                    TiffyException.ExceptionType.INVALID_TIME_FORMAT);
        }
    }

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

    private Contact createContact(String[] parsedInput) {
        return (parsedInput.length == 4)
                ? new Contact(parsedInput[1], parsedInput[2], parsedInput[3])
                : new Contact(parsedInput[1], parsedInput[2]);
    }

    private void addContact(String[] parsedInput) {
        Contact contact = createContact(parsedInput);
        contactManager.addContact(contact);
        UiManager.getInstance().notifyContactAdded(contact);
    }

    private void saveAndExit() {
        DataManager.getInstance().saveTasksToFile(taskManager.getTasks());
        DataManager.getInstance().saveContactsToFile(contactManager.getContacts());
        UiManager.getInstance().generateExitMessage();
    }

    private void listTasks() throws TiffyException {
        List<Task> tasks = taskManager.getTasks();
        if (tasks.isEmpty()) {
            throw new TiffyException("You have no tasks.",
                    TiffyException.ExceptionType.ZERO_TASK);
        }
        UiManager.getInstance().printTasks(tasks);
    }

    private void listContacts() throws TiffyException {
        List<Contact> contacts = contactManager.getContacts();
        if (contacts.isEmpty()) {
            throw new TiffyException("You have no contacts.",
                    TiffyException.ExceptionType.ZERO_CONTACTS);
        }
        UiManager.getInstance().printContacts(contacts);
    }

    private void handleListEvent(String listType) throws TiffyException {
        switch (listType) {
            case "tasks" -> {
                listTasks();
            }
            case "contacts" -> {
                listContacts();
            }
            default -> {
                throw new TiffyException("You can either list 'tasks' or 'contacts'.",
                        TiffyException.ExceptionType.INVALID_LIST_TYPE);
            }
        }
    }

    private void handleDeleteEvent(String[] parsedInput) throws TiffyException {
        if (parsedInput.length != 3) { //catch this in parser.java
            throw new TiffyException("Invalid input! Try again.",
                    TiffyException.ExceptionType.INVALID_INPUT);
        }

        switch (parsedInput[1]) {
            case "task" -> {
                taskManager.deleteTask(Integer.parseInt(parsedInput[2]) - 1);
            }
            case "contact" -> {
                contactManager.deleteContact(Integer.parseInt(parsedInput[2]) - 1);
            }
        }
    }

    private void findTasks(String searchKey) throws TiffyException {
        List<Task> foundTasks = taskManager.findTasks(searchKey);
        UiManager.getInstance().printTasks(foundTasks);
    }
}