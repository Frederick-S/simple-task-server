package dekiru.simpletask.service.ec2;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import software.amazon.awssdk.regions.Region;

/**
 * The data model to create an aws ec2 instance.
 */
public class CreateInstanceRequest {
    private Region region;

    private String name;

    private String launchTemplateId;

    private String launchTemplateVersion;

    private String startupCommand;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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

    public String getStartupCommand() {
        return startupCommand;
    }

    public void setStartupCommand(String startupCommand) {
        this.startupCommand = startupCommand;
    }

    /**
     * Validate the fields.
     */
    public void validate() {
        Validate.isTrue(region != null, "Region is required");
        Validate.isTrue(StringUtils.isNotBlank(name), "Name is required");
        Validate.isTrue(StringUtils.isNotBlank(launchTemplateId), "Launch template id required");
        Validate.isTrue(StringUtils.isNotBlank(launchTemplateVersion),
                "Launch template version required");
        Validate.isTrue(StringUtils.isNotBlank(startupCommand), "Startup command required");
    }
}
