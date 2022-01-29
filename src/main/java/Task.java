public class Task {
    protected String taskDescription;
    protected boolean isDone;

    public Task(String description) {
        this.taskDescription = description;
        this.isDone = false;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[✓] " + this.taskDescription;
        } else {
            return "[ ] " + this.taskDescription;
        }
    }
}
