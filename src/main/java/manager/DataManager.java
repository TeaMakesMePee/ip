package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.stream.Collectors;
import task.Task;
import contacts.Contact;

/**
 * Manages the saving and loading of task data to and from a JSON file.
 * Implements a singleton pattern to ensure a single instance of the class.
 */
public class DataManager {

    /** File path to the JSON file where task data is stored. */
    private static final String DATA_FILE_PATH = "data/data_tiffy.json";
    private static final String CONTACTS_FILE_PATH = "data/contacts_tiffy.json";

    /** Gson instance for serializing and deserializing JSON data. */
    private final Gson gson;

    /**
     * Private constructor to initialize the Gson instance.
     */
    private DataManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Inner static class for holding the singleton instance of DataManager.
     */
    private static final class InstanceHolder {
        /** Singleton instance of DataManager. */
        private static final DataManager instance = new DataManager();
    }

    /**
     * Returns the singleton instance of DataManager.
     *
     * @return Singleton DataManager instance.
     */
    public static DataManager getInstance() {
        return DataManager.InstanceHolder.instance;
    }

    /**
     * Saves the list of tasks to a JSON file.
     *
     * @param taskList List of tasks to be saved.
     */
    public void saveTasksToFile(List<Task> taskList) {
        assert taskList != null : "Task list cannot be null";

        File file = new File(DATA_FILE_PATH);
        List<String> stringList = taskList.stream()
                .map(Task::getFormattedTask)
                .collect(Collectors.toList());
        try {
            file.getParentFile().mkdirs();

            try (Writer writer = new FileWriter(file)) {
                gson.toJson(stringList, writer);
            }
        } catch (IOException e) {
            System.err.println("Error saving strings: " + e.getMessage());
        }
    }

    public void saveContactsToFile(List<Contact> contactList) {
        assert contactList != null : "Contact list cannot be null";

        File file = new File(CONTACTS_FILE_PATH);
        List<String> stringList = contactList.stream()
                .map(Contact::getFormattedContact)
                .collect(Collectors.toList());
        try {
            file.getParentFile().mkdirs();

            try (Writer writer = new FileWriter(file)) {
                gson.toJson(stringList, writer);
            }
        } catch (IOException e) {
            System.err.println("Error saving strings: " + e.getMessage());
        }
    }

    /**
     * Loads the list of tasks from a JSON file.
     *
     * @return List of serialized task strings loaded from the file.
     */
    public List<String> loadTasksFromFile() {
        File file = new File(DATA_FILE_PATH);

        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<String>>() {}.getType();
                return gson.fromJson(reader, listType);
            } catch (IOException e) {
                System.err.println("Error loading strings: " + e.getMessage());
            }
        }

        return new ArrayList<>();
    }

    public List<String> loadContactsFromFile() {
        File file = new File(CONTACTS_FILE_PATH);

        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<String>>() {}.getType();
                return gson.fromJson(reader, listType);
            } catch (IOException e) {
                System.err.println("Error loading strings: " + e.getMessage());
            }
        }

        return new ArrayList<>();
    }
}