import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.config.SaslConfigs;

public class ListTopics {

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
        // lists all the topics
        for (TopicListing topicListing : admin.listTopics().listings().get()) {
            System.out.println(topicListing);
        }
    }
}
