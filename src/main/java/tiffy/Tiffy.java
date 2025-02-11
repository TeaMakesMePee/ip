package tiffy;

import java.util.List;
import java.time.LocalDate;
import task.Task;
import task.Todo;
import task.Event;
import task.Deadline;
import exception.TiffyException;
import manager.TaskManager;
import manager.UiManager;
import manager.DataManager;
import manager.ContactManager;
import contacts.Contact;
import utility.Parser;

public class Tiffy {
    private static final TaskManager taskManager = new TaskManager(DataManager.getInstance().loadTasksFromFile());
    private static final ContactManager contactManager = new ContactManager(DataManager.getInstance().loadContactsFromFile());
    private static final Parser parser = new Parser();

    /*public static void main(String[] args) {
        UiManager.getInstance().printStartupMessage();
        String input = UiManager.getInstance().readCommand();
        while (!input.equals("bye")) {
            try {
                handleRequests(input);
            } catch (TiffyException te) {
                UiManager.getInstance().printException(te);
            }
            DataManager.getInstance().saveTasksToFile(taskManager.getTasks());
            input = UiManager.getInstance().readCommand();
        }
        UiManager.getInstance().printGoodbyeMessage();
    }*/

    private void markDoneUndone(List<Task> tasks, boolean mark, int index) throws TiffyException {
        assert index > 0 && index <= tasks.size() : "Invalid task index: " + index;
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

    public void handleRequests(String input) throws TiffyException {
        assert input != null && !input.isBlank() : "Input command cannot be null or empty";

        String[] partition = parser.handleRequests(input);
        switch (partition[0]) {
            case "list" -> {
                if (taskManager.getTasks().isEmpty()) {
                    throw new TiffyException("You have no tasks! Add some.",
                            TiffyException.ExceptionType.ZERO_TASK);
                }
                UiManager.getInstance().printTasks(taskManager.getTasks());
            }
            case "todo" -> {
                taskManager.addTask(new Todo(partition[1]));
            }
            case "deadline" -> {
                taskManager.addTask(new Deadline(partition[1], LocalDate.parse(partition[2])));
            }
            case "event" -> {
                taskManager.addTask(new Event(partition[1], LocalDate.parse(partition[2]), LocalDate.parse(partition[3])));
            }
            case "mark" -> {
                try {
                    markDoneUndone(taskManager.getTasks(), true, Integer.parseInt(partition[1]));
                } catch (TiffyException te) {
                    UiManager.getInstance().printException(te);
                }
            }
            case "unmark" -> {
                try {
                    markDoneUndone(taskManager.getTasks(), false, Integer.parseInt(partition[1]));
                } catch (TiffyException te) {
                    UiManager.getInstance().printException(te);
                }
            }
            case "delete" -> {
                try {
                    taskManager.deleteTask(Integer.parseInt(partition[1]) - 1);
                } catch (TiffyException te) {
                    UiManager.getInstance().printException(te);
                }
            }
            case "find" -> {
                try {
                    List<Task> temp = taskManager.findTasks(partition[1]);
                    UiManager.getInstance().printTasks(temp);
                } catch (TiffyException te) {
                    UiManager.getInstance().printException(te);
                }
            }
            case "contact" -> {
                if (partition.length == 4 ) {
                    contactManager.addContact(new Contact(partition[1], partition[2], partition[3]));
                } else {
                    contactManager.addContact(new Contact(partition[1], partition[2]));
                }
            }
            case "bye" -> {
                DataManager.getInstance().saveTasksToFile(taskManager.getTasks());
                DataManager.getInstance().saveContactsToFile(contactManager.getContacts());
            }
        }
    }
}