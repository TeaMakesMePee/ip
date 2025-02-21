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

    private void validateCommand(String command, String[] arguments) throws TiffyException {
        Set<Integer> validCount = argumentCount.get(command);
        if (!validCount.contains(arguments.length)) {
            throw new TiffyException("Invalid command syntax.", exception.TiffyException.ExceptionType.INVALID_ARGUMENT);
        }
    }

    private String[] handleStringArguments(String[] splitRequest) throws TiffyException {
        String mergedStringArgument = "";
        if (splitRequest.length > 1) {
            mergedStringArgument = String.join(" ",
                    Arrays.copyOfRange(splitRequest,
                            1,
                            splitRequest.length));
        }
        validateCommand(splitRequest[0], splitRequest);
        return new String[]{splitRequest[0], mergedStringArgument};
    }

    private String[] handleDeadline(String deadlineInfo) throws TiffyException {
        String[] splitInfo = deadlineInfo.split(" /by ");
        validateTaskCommand("deadline", splitInfo);
        return new String[]{"deadline", splitInfo[0], splitInfo[1]};
    }

    private String[] handleEvent(String eventInfo) throws TiffyException {
        String[] splitInfo = eventInfo.split(" /from | /to ");
        validateTaskCommand("event", splitInfo);
        return new String[]{"event", splitInfo[0], splitInfo[1], splitInfo[2]};
    }

    private void validateTaskCommand(String command, String[] taskInfo) throws TiffyException {
        validateCommand(command, taskInfo);
        if (taskInfo[0].isBlank()) {
            throw new TiffyException("I'm afraid that's an invalid request.",
                    TiffyException.ExceptionType.INVALID_INPUT);
        }
    }

    private String[] handleArguments(String[] splitArgument) throws TiffyException {
        validateCommand(splitArgument[0], splitArgument);
        return splitArgument;
    }

    private String[] handleContact(String contactInfo) throws TiffyException {
        String[] splitInfo = contactInfo.split(" /name | /num | /email ");
        validateCommand("contact", splitInfo);
        return splitInfo;
    }
}