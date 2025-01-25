package utility;

public class Parser {
    public String[] handleRequests(String request) throws exception.TiffyException {
        String[] partition = request.split(" ");
        String task = request.replaceFirst(partition[0], "");
        switch (partition[0]) {
            case "list" -> {
                return new String[]{"list"};
            }
            case "todo" -> {
                if (task.isBlank()) {
                    throw new exception.TiffyException("Adding empty tasks to feel productive?",
                            exception.TiffyException.ExceptionType.EMPTY_TASK);
                }
                return new String[]{"todo", task};
            }
            case "deadline" -> {
                String[] parts = task.split(" /by ");
                if (parts.length < 2 || parts[0].isBlank()) {
                    throw new exception.TiffyException("I'm afraid that's an invalid request.",
                            exception.TiffyException.ExceptionType.INVALID_INPUT);
                }
                return new String[]{"deadline", parts[0], parts[1]};
            }
            case "event" -> {
                String[] parts = task.split(" /from | /to ");
                if (parts.length < 3 || parts[0].isBlank()) {
                    throw new exception.TiffyException("I'm afraid that's an invalid request.",
                            exception.TiffyException.ExceptionType.INVALID_INPUT);
                }
                return new String[]{"event", parts[0], parts[1], parts[2]};
            }
            case "mark" -> {
                return new String[]{"mark", partition[1]};
            }
            case "unmark" -> {
                return new String[]{"unmark", partition[1]};
            }
            case "delete" -> {
                return new String[]{"delete", partition[1]};
            }
            case "find" -> {
                if (task.isBlank()) {
                    throw new exception.TiffyException("I'm afraid that's an invalid request.",
                            exception.TiffyException.ExceptionType.INVALID_INPUT);
                }
                return new String[]{"find", task};
            }
            default -> {
                throw new exception.TiffyException("I'm afraid that's an invalid request.",
                        exception.TiffyException.ExceptionType.INVALID_INPUT);
            }
        }
    }
}
