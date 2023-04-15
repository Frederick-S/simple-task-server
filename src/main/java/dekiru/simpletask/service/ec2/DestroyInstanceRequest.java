package dekiru.simpletask.service.ec2;

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
}
