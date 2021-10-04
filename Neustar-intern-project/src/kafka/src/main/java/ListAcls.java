import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.config.SaslConfigs;

public class ListAcls {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties config = new Properties();
        String username = "admin";
        String password = "admin";
        
        // configures the properties
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        config.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        config.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + password +"\";");
        AdminClient admin = AdminClient.create(config);
        for (TopicListing topicListing : admin.listTopics().listings().get()) {
            System.out.println(topicListing);
            
        }/*
        AccessControlEntry acl = new AccessControlEntry("User:producer1", "localhost:9092", AclOperation.READ, AclPermissionType.ALLOW);
        AclBinding bind = new AclBinding(new ResourcePattern(ResourceType.TOPIC, "test", PatternType.LITERAL), acl);
        Collection<AclBinding> list = new ArrayList<>();
        list.add(bind);
        CreateAclsResult result = admin.createAcls(list);
        **/
        // lists alls the acls
        Collection<AclBinding> res = admin.describeAcls(AclBindingFilter.ANY).values().get();

        for (AclBinding aclBinding : res) {
            System.out.println(aclBinding.toString());
            
        }
        
        
        


    }
    
}
