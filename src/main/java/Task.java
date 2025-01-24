public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public abstract String getFormattedTask();

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markDone(boolean done) throws TiffyException {
        if (this.isDone == done) {
            throw new TiffyException(done ? "Task already marked!" : "Task already marked not done!",
                    done ? TiffyException.ExceptionType.ALREADY_MARKED : TiffyException.ExceptionType.ALREADY_UNMARKED);
        }
        this.isDone = done;
        UiManager.getInstance().printEventFeedback(this,
                done ? UiManager.eventType.TASK_MARKED : UiManager.eventType.TASK_UNMARKED);
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}