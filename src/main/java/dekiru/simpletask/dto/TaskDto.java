package dekiru.simpletask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "launchTemplateId could not be empty")
    @Size(max = 100, message = "The length of launchTemplateId could not be longer than 100")
    private String launchTemplateId;

    @NotBlank(message = "launchTemplateVersion could not be empty")
    @Size(max = 10, message = "The length of launchTemplateVersion could not be longer than 10")
    private String launchTemplateVersion;

    @NotBlank(message = "startupScript could not be empty")
    private String startupScript;

    @NotBlank(message = "timeoutSeconds could not empty")
    @Min(value = 1, message = "The value of timeoutSeconds should be greater than 0")
    private Integer timeoutSeconds;

    private Long createdBy;

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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
}