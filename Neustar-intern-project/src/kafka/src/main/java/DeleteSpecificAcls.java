import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DeleteAclsResult;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AccessControlEntryFilter;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePatternFilter;
import org.apache.kafka.common.resource.ResourceType;

public class DeleteSpecificAcls {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties config = new Properties();
        String username = "admin";
        String password = "admin";
        
        // configures the properties
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        config.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        config.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + password +"\";");
        AdminClient admin = AdminClient.create(config);

        // deletes the specified acls
        Collection<AclBindingFilter> list = new ArrayList<>();
        list.add(new AclBindingFilter(new ResourcePatternFilter(ResourceType.TOPIC, "topic", PatternType.LITERAL), 
         new AccessControlEntryFilter("consumer1", "kafka:9092", AclOperation.READ, AclPermissionType.ALLOW)));

        DeleteAclsResult result = admin.deleteAcls(list);
        Collection<AclBinding> res = admin.describeAcls(AclBindingFilter.ANY).values().get();

        for (AclBinding aclBinding : res) {
            System.out.println(aclBinding.toString());
            
        }
    } 
}
