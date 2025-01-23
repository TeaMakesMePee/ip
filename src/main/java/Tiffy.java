import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import main.java.*;

public class Tiffy {
    public static void main(String[] args) {
        Scanner s = new Scanner(in);
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

        List<Task> tasks = new ArrayList<>();
        String input = s.nextLine();
        while (!input.equals("bye")) {
            handleRequests(input, tasks);
            input = s.nextLine();
        }
        System.out.println(bye);
    }

    public static void markDoneUndone(List<Task> tasks, boolean mark, int index) {
        Task temp = tasks.get(index - 1);
        if (temp.getStatusIcon().equals(mark ? "X" : " ")) {
            System.out.println(mark ? "Task already marked!" : "Task has not been marked!");
        } else {
            if (mark) temp.markDone();
            else temp.unmarkDone();
            System.out.println("Task has been marked as " + (mark ? "done." : "not done: "));
            System.out.println(temp.toString());
        }
    }

    public static void notifyTaskAdded(Task t, int size) {
        System.out.println("Task added:\n" +
                t.toString() + "\n"
                + "You have " + size + " tasks.");
    }

    public static void handleRequests(String input, List<Task> tasks) {
        String[] partition = input.split(" ");
        switch (partition[0]) {
            case "list" -> {
                int count = 1;
                for (Task t : tasks) {
                    System.out.println(count + "." + t.toString());
                    count++;
                }
            }
            case "todo" -> {
                String task = input.replaceFirst("todo ", "");
                Todo td = new Todo(task);
                tasks.add(td);
                notifyTaskAdded(td, tasks.size());
            }
            case "deadline" -> {
                String[] parts = input.replaceFirst("deadline ", "").split(" /by ");
                Deadline d = new Deadline(parts[0], parts[1]);
                tasks.add(d);
                notifyTaskAdded(d, tasks.size());
            }
            case "event" -> {
                String[] parts = input.replaceFirst("event ", "").split(" /from | /to ");
                Event e = new Event(parts[0], parts[1], parts[2]);
                tasks.add(e);
                notifyTaskAdded(e, tasks.size());
            }
            case "mark" -> {
                int index = Integer.parseInt(partition[1]);
                markDoneUndone(tasks, true, index);
            }
            case "unmark" -> {
                int index = Integer.parseInt(partition[1]);
                markDoneUndone(tasks, false, index);
            }
            default -> {
                System.out.println("Invalid command!");
            }
        }
    }
}