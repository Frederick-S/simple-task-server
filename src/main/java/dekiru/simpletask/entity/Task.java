package dekiru.simpletask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * The task entity.
 */
@Entity
public class Task extends BaseEntity {
    @Column
    private String name;

    @Column
    private String launchTemplateId;

    @Column
    private String launchTemplateVersion;

    @Column(columnDefinition = "text")
    private String startupScript;

    @Column
    private Integer timeoutSeconds;

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

    public void setTimeoutSeconds(Integer timeoutMinutes) {
        this.timeoutSeconds = timeoutMinutes;
    }
}
