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

public class DataManager {
    private static final String FILE_PATH = "data/Tiffy.json";
    private final Gson gson;

    private DataManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private static final class InstanceHolder {
        private static final DataManager instance = new DataManager();
    }

    public static DataManager getInstance() {
        return DataManager.InstanceHolder.instance;
    }

    public void saveTasksToFile(List<Task> taskList) {
        File file = new File(FILE_PATH);
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

    public List<String> loadTasksFromFile() {
        File file = new File(FILE_PATH);

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
