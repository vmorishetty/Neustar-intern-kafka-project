import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;

public class Topic {

   public static void main(String[] args) {

    //Consumer consumer = new Consumer();
    String bootstrap_server = "NaN";
    String topic = "NaN";
    int partitions = 3;
    short replicationFactor = 1;
    String username = "producer1";
    String password = "producer1";

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--bootstrap-server")) {
        bootstrap_server = args[i+1];
        i++;
      }
      else if (args[i].equals("--create")) {
          if (args[i+1].equals("--topic")) {
            topic = args[i+2];
          }
        i+=2;
      }
      else if (args[i].equals("--partitions")) {
        partitions = Integer.valueOf(args[i+1]);
        i++;
      }
      else if (args[i].equals("--replication-factor")) {
        replicationFactor = Short.valueOf(args[i+1]);
        i++;
      }
      else if (args[i].equals("--user")) {
        username = args[i+1];
        i++;
      }
      else if (args[i].equals("--password")) {
        password = args[i+1];
        i++;
      }
    }

    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
    properties.setProperty(SaslConfigs.SASL_MECHANISM, "PLAIN");
    properties.setProperty(SaslConfigs.SASL_JAAS_CONFIG,
     "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + password +"\";");
    

    NewTopic create = new NewTopic("topic", 3, replicationFactor);
        
    }  
}
