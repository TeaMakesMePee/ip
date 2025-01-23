import java.util.Scanner;
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
        while (!input.equals("bye")) {
            System.out.println(input);
            input = s.nextLine();
        }
        System.out.println(bye);
    }
}