package dekiru.simpletask.dto;

import dekiru.simpletask.entity.Task;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * The data transfer object that represents a task.
 */
public class TaskDto implements Serializable {
    private Long id;

    @NotBlank(message = "Name could not be empty")
    @Size(max = 100, message = "The length of name could not be longer than 100")
    private String name;

    @NotBlank(message = "Description could not be empty")
    @Size(max = 255, message = "The length of description could not be longer than 255")
    private String description;

    @NotBlank(message = "launchTemplateId could not be empty")
    @Size(max = 100, message = "The length of launchTemplateId could not be longer than 100")
    private String launchTemplateId;

    @NotBlank(message = "launchTemplateVersion could not be empty")
    @Size(max = 10, message = "The length of launchTemplateVersion could not be longer than 10")
    private String launchTemplateVersion;

    @NotBlank(message = "startupScript could not be empty")
    private String startupScript;

    @NotNull(message = "timeoutSeconds could not null")
    @Min(value = 1, message = "The value of timeoutSeconds should be greater than 0")
    private Integer timeoutSeconds;

    @NotBlank(message = "schedule could not be empty")
    private String schedule;

    private Integer status;

    private Long createdBy;

    private Long updatedBy;

    private Instant createdAt;

    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLaunchTemplateId() {
        return launchTemplateId;
    }

    public void setLaunchTemplateId(String launchTemplateId) {
        this.launchTemplateId = launchTemplateId;
    }

    public String getLaunchTemplateVersion() {
        return launchTemplateVersion;
    }

    public void setLaunchTemplateVersion(String launchTemplateVersion) {
        this.launchTemplateVersion = launchTemplateVersion;
    }

    public String getStartupScript() {
        return startupScript;
    }

    public void setStartupScript(String startupScript) {
        this.startupScript = startupScript;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Create {@link TaskDto} from {@link Task} entity.
     *
     * @param task The {@link Task} entity.
     * @return {@link TaskDto}
     */
    public static TaskDto fromEntity(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setLaunchTemplateId(task.getLaunchTemplateId());
        taskDto.setLaunchTemplateVersion(task.getLaunchTemplateVersion());
        taskDto.setStartupScript(task.getStartupScript());
        taskDto.setTimeoutSeconds(task.getTimeoutSeconds());
        taskDto.setSchedule(task.getSchedule());
        taskDto.setStatus(task.getStatus());
        taskDto.setCreatedBy(task.getCreatedBy());
        taskDto.setUpdatedBy(task.getUpdatedBy());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setUpdatedAt(task.getUpdatedAt());

        return taskDto;
    }
}
