import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.*;

public class Admin {

  public static void main(String[] args) throws InterruptedException, ExecutionException{

    // creates the properties
    Properties config = new Properties();
    String username = "admin";
    String password = "admin";
    
    // configures the properties with the admin login
    config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
    // chooses security protocols
    config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
    config.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
    config.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required user_admin=\"admin\";");

    AdminClient admin = AdminClient.create(config);

  }
}
