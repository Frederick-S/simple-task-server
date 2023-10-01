package dekiru.simpletask.service.ec2;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import software.amazon.awssdk.regions.Region;

/**
 * The data model to destroy an aws ec2 instance.
 */
public class DestroyInstanceRequest {
    private Region region;

    private String instanceId;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * Validate the fields.
     */
    public void validate() {
        Validate.isTrue(region != null, "Region is required");
        Validate.isTrue(StringUtils.isNotBlank(instanceId), "Instance id is required");
    }
}
