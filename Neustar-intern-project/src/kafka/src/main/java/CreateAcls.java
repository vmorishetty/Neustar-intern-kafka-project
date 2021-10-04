import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateAclsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourceType;

public class CreateAcls {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // the configuration to create a topic
        Properties config = new Properties();
        String username = "admin";
        String password = "admin";
        String bootstrap = "kafka:9092";
        String topic = "test_topic";
        int partition = 3;
        short replicationFactor = 1;
        
        //configures the properties to create acls
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        config.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        config.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + password +"\";");
        //config.setProperty(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "C:/ssl-cert/client.truststore");
        //config.setProperty(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "password");
        //config.setProperty(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "C:/ssl-cert/varunm.keystore");
        //config.setProperty(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "password");
        //config.setProperty(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "password");
        AdminClient admin = AdminClient.create(config);
        
        // creates the topic and the permissions for each user
        NewTopic create_topic = new NewTopic(topic, partition, replicationFactor);
        AccessControlEntry acl = new AccessControlEntry("User:consumer1", "*", AclOperation.READ, AclPermissionType.ALLOW);
        AccessControlEntry acl2 = new AccessControlEntry("User:producer1", "*", AclOperation.CREATE, AclPermissionType.ALLOW);
        AccessControlEntry acl3 = new AccessControlEntry("User:producer1", "*", AclOperation.WRITE, AclPermissionType.ALLOW);
        //AccessControlEntry acl4 = new AccessControlEntry("User:consumer1", bootstrap, AclOperation.READ, AclPermissionType.ALLOW);


        // binds each permission to the topic
        Collection<AclBinding> list = new ArrayList<>();
        list.add( new AclBinding(new ResourcePattern(ResourceType.TOPIC, topic, PatternType.LITERAL), acl));
        list.add(new AclBinding(new ResourcePattern(ResourceType.TOPIC, topic, PatternType.LITERAL), acl2));
        list.add(new AclBinding(new ResourcePattern(ResourceType.TOPIC, topic, PatternType.LITERAL), acl3));
        list.add(new AclBinding(new ResourcePattern(ResourceType.GROUP, "consumer-group", PatternType.LITERAL), acl));

        CreateAclsResult result = admin.createAcls(list);
        Collection<AclBinding> res = admin.describeAcls(AclBindingFilter.ANY).values().get();

        for (AclBinding aclBinding : res) {
            System.out.println(aclBinding.toString());
            
        }
    } 
}
