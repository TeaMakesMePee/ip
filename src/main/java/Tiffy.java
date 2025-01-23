import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.java.*;
import static java.lang.System.in;

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
        String input = s.nextLine();
        List<Task> tasks = new ArrayList<>();
        while (!input.equals("bye")) {
            String[] partition = input.split(" ");
            switch (partition[0]) {
                case "list" -> {
                    int counter = 1;
                    for (Task t : tasks) {
                        System.out.println(counter + "." + t.toString());
                        counter++;
                    }
                }
                case "mark" -> {
                    try {
                        tasks.get(Integer.parseInt(partition[1]) - 1).markDone();
                        System.out.println("Marked task:");
                        System.out.println(tasks.get(Integer.parseInt(partition[1]) - 1).toString());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid index");
                    }
                }
                case "unmark" -> {
                    try {
                        tasks.get(Integer.parseInt(partition[1]) - 1).unmarkDone();
                        System.out.println("Unmarked task:");
                        System.out.println(tasks.get(Integer.parseInt(partition[1]) - 1).toString());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid index");
                    }
                }
                default -> {
                    Task t = new Task(input);
                    System.out.println("added: " + input);
                    tasks.add(t);
                }
            }
            input = s.nextLine();
        }
        System.out.println(bye);
    }
}