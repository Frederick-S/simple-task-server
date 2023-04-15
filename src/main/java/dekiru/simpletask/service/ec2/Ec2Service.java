package dekiru.simpletask.service.ec2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.LaunchTemplateSpecification;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesRequest;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesResponse;

import java.util.Base64;

/**
 * The service that manages aws ec2 instances.
 */
@Service
public class Ec2Service {
    private static final Logger logger = LoggerFactory.getLogger(Ec2Service.class);

    /**
     * Create a new aws ec2 instance.
     *
     * @param createInstanceRequest {@link CreateInstanceRequest}
     * @return Id of the new instance
     */
    public String createInstance(CreateInstanceRequest createInstanceRequest) {
        try (Ec2Client ec2Client = Ec2Client.builder()
                .region(createInstanceRequest.getRegion())
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {
            LaunchTemplateSpecification launchTemplateSpecification =
                    LaunchTemplateSpecification.builder()
                            .launchTemplateId(createInstanceRequest.getLaunchTemplateId())
                            .version(createInstanceRequest.getLaunchTemplateVersion())
                            .build();
            byte[] startupCommand = createInstanceRequest.getStartupCommand().getBytes();
            RunInstancesRequest runInstancesRequest = RunInstancesRequest.builder()
                    .maxCount(1)
                    .minCount(1)
                    .userData(Base64.getEncoder().encodeToString(startupCommand))
                    .launchTemplate(launchTemplateSpecification)
                    .build();
            RunInstancesResponse runInstancesResponse = ec2Client.runInstances(runInstancesRequest);
            String instanceId = runInstancesResponse.instances().get(0).instanceId();
            Tag tag = Tag.builder()
                    .key("Name")
                    .value(createInstanceRequest.getName())
                    .build();
            CreateTagsRequest createTagsRequest = CreateTagsRequest.builder()
                    .resources(instanceId)
                    .tags(tag)
                    .build();
            ec2Client.createTags(createTagsRequest);

            return instanceId;
        }
    }

    /**
     * Destroy an aws ec2 instance.
     *
     * @param destroyInstanceRequest {@link DestroyInstanceRequest}
     */
    public boolean destroyInstance(DestroyInstanceRequest destroyInstanceRequest) {
        try (Ec2Client ec2Client = Ec2Client.builder()
                .region(destroyInstanceRequest.getRegion())
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {
            TerminateInstancesRequest terminateInstancesRequest =
                    TerminateInstancesRequest.builder()
                            .instanceIds(destroyInstanceRequest.getInstanceId())
                            .build();
            TerminateInstancesResponse terminateInstancesResponse =
                    ec2Client.terminateInstances(terminateInstancesRequest);

            return terminateInstancesResponse.hasTerminatingInstances();
        }
    }
}
