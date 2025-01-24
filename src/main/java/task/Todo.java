package task;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String getFormattedTask() {
        return "T|" + this.isDone + "|" + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}