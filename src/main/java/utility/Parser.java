package utility;

import exception.TiffyException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Arrays;

/**
 * Parses user input into actionable commands for the task manager.
 */
public class Parser {

    private static final Map<String, Set<Integer>> argumentCount = new HashMap<>();

    // Initialize the command-to-argument mapping
    static {
        argumentCount.put("todo", Set.of(2));
        argumentCount.put("mark", Set.of(2));
        argumentCount.put("unmark", Set.of(2));
        argumentCount.put("delete", Set.of(3));
        argumentCount.put("deadline", Set.of(2));
        argumentCount.put("event", Set.of(3));
        argumentCount.put("list", Set.of(2));
        argumentCount.put("find", Set.of(2));
        argumentCount.put("contact", Set.of(3, 4));
    }

    /**
     * Processes the user's request and splits it into components for further handling.
     *
     * @param request The user's input string.
     * @return An array of strings representing the parsed command and its arguments.
     * @throws TiffyException If the input is invalid or incomplete.
     */
    public String[] splitRequest(String request) throws TiffyException {
        String[] splitRequest = request.split(" ");
        String taskInfo = request.replaceFirst(splitRequest[0] + " ", "");

        return switch (splitRequest[0]) {
            case "bye" -> splitRequest;
            case "delete", "mark", "unmark", "list" -> handleArguments(splitRequest);
            case "todo", "find" -> handleStringArguments(splitRequest);
            case "deadline" -> handleDeadline(taskInfo);
            case "event" -> handleEvent(taskInfo);
            case "contact" -> handleContact(request);
            default -> throw new TiffyException("I'm afraid that's an invalid request.",
                    TiffyException.ExceptionType.INVALID_INPUT);
        };
    }

    /**
     * Validates that the given command has the correct number of arguments.
     *
     * @param command   The command to validate.
     * @param arguments The array of arguments provided with the command.
     * @throws TiffyException If the argument count is incorrect.
     */
    private void validateCommand(String command, String[] arguments) throws TiffyException {
        Set<Integer> validCount = argumentCount.get(command);
        if (!validCount.contains(arguments.length)) {
            throw new TiffyException("Invalid command syntax.",
                    TiffyException.ExceptionType.INVALID_ARGUMENT);
        }
    }

    /**
     * Handles commands that require a single string argument, such as "todo" and "find".
     *
     * @param splitRequest The array containing the split command and its arguments.
     * @return A processed array containing the command and its merged string argument.
     * @throws TiffyException If the command is invalid.
     */
    private String[] handleStringArguments(String[] splitRequest) throws TiffyException {
        String mergedStringArgument = "";
        if (splitRequest.length > 1) {
            mergedStringArgument = String.join(" ",
                    Arrays.copyOfRange(splitRequest, 1, splitRequest.length));
        }
        validateCommand(splitRequest[0], splitRequest);
        return new String[]{splitRequest[0], mergedStringArgument};
    }

    /**
     * Parses a deadline command, ensuring the correct format.
     *
     * @param deadlineInfo The raw deadline information provided by the user.
     * @return A formatted array containing the command and its arguments.
     * @throws TiffyException If the deadline format is incorrect.
     */
    private String[] handleDeadline(String deadlineInfo) throws TiffyException {
        String[] splitInfo = deadlineInfo.split(" /by ");
        validateTaskCommand("deadline", splitInfo);
        return new String[]{"deadline", splitInfo[0], splitInfo[1]};
    }

    /**
     * Parses an event command, ensuring the correct format.
     *
     * @param eventInfo The raw event information provided by the user.
     * @return A formatted array containing the command and its arguments.
     * @throws TiffyException If the event format is incorrect.
     */
    private String[] handleEvent(String eventInfo) throws TiffyException {
        String[] splitInfo = eventInfo.split(" /from | /to ");
        validateTaskCommand("event", splitInfo);
        return new String[]{"event", splitInfo[0], splitInfo[1], splitInfo[2]};
    }

    /**
     * Validates a task-related command to ensure it has the correct arguments.
     *
     * @param command  The task command to validate (e.g., "deadline", "event").
     * @param taskInfo The parsed arguments for the task.
     * @throws TiffyException If the arguments are invalid.
     */
    private void validateTaskCommand(String command, String[] taskInfo) throws TiffyException {
        validateCommand(command, taskInfo);
        if (taskInfo[0].isBlank()) {
            throw new TiffyException("I'm afraid that's an invalid request.",
                    TiffyException.ExceptionType.INVALID_INPUT);
        }
    }

    /**
     * Handles commands that take direct arguments, such as "delete", "mark", "unmark", and "list".
     *
     * @param splitArgument The split user input.
     * @return The processed command with its arguments.
     * @throws TiffyException If the command format is incorrect.
     */
    private String[] handleArguments(String[] splitArgument) throws TiffyException {
        validateCommand(splitArgument[0], splitArgument);
        return splitArgument;
    }

    /**
     * Parses a contact command, ensuring the correct format for contact creation.
     *
     * @param contactInfo The raw contact information provided by the user.
     * @return A formatted array containing the parsed contact details.
     * @throws TiffyException If the contact format is incorrect.
     */
    private String[] handleContact(String contactInfo) throws TiffyException {
        String[] splitInfo = contactInfo.split(" /name | /num | /email ");
        validateCommand("contact", splitInfo);
        return splitInfo;
    }
}