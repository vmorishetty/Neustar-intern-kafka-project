import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {

  public static void main(String[] args){
    
    //Consumer consumer = new Consumer();
    String bootstrap_server = "kafka:9092";
    String topic = "topic";
    String groupid = "consumer-group";
    String username = "consumer1";
    String password = "consumer1";
    Boolean beginning = false;

    if (args.length != 11) {
      System.out.println("Usage: java Consumer --bootstrap-server <server_name> --topic <topic_name> --groupid <groupid> --user <username> --password <password> <from-beginning>");
      //return;
    }

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--bootstrap-server")) {
        bootstrap_server = args[i+1];
        i++;
      }
      else if (args[i].equals("--topic")) {
        topic = args[i+1];
        i++;
      }
      else if (args[i].equals("--group")) {
        groupid = args[i+1];
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
      else if (args[i].equals("--from-beginning")) {
        beginning = true;
      }
    }

    System.out.println ("bootstrap_server:" + bootstrap_server);
    System.out.println ("topic:" + topic);
    System.out.println ("groupid:" + groupid);
    System.out.println ("username:" + username);
    System.out.println ("password:" + password);
    System.out.println ("earliest:" + beginning);


    Consumer c = new Consumer();
    c.consumer(bootstrap_server, topic, groupid, username, password, beginning);
    

  }

  public void consumer(String bootstrap_server, String topic, String groupid, String username, String password, Boolean beginning) {

    //Logger logger = LoggerFactory.getLogger(Consumer.class.getName());

    String read_order;
    if (beginning) {
      read_order = "earliest";
    }
    else {
      read_order = "latest";
    }
   

    // create consumer configs
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupid);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, read_order);
    properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
    properties.setProperty(SaslConfigs.SASL_MECHANISM, "PLAIN");
    properties.setProperty(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + password +"\";");
    


    // create consumer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

    // subscribe consumer to topics
    consumer.subscribe(Arrays.asList(topic));

    // poll for new data
    while(true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record: records) {
      System.out.print(" Value: " + record.value() + " ,");
      System.out.println("Partition:" + record.partition() + ", Offset: " + record.offset());
      //logger.info("Key:" + record.key() + ", Value: " + record.value());
      //logger.info("Partition:" + record.partition() + ", Offset: " + record.offset());
      }
    }
  }
}
