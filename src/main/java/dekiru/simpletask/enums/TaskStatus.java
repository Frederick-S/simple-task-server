package dekiru.simpletask.enums;

/**
 * Status of a @{dekiru.simpletask.dto.TaskDto}.
 */
public enum TaskStatus {
    ENABLED(1),
    DISABLED(2);

    private int value;

    TaskStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
