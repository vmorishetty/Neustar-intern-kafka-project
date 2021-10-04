import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {

  public static void main(String[] args){

    short replicationFactor = 1;
    //NewTopic topic_test = new NewTopic("topic", 3, replicationFactor);
    
    // configures properties based on system input
    String bootstrap_server = "localhost:9092";
    String topic = "test";
    String[] messages = new String[]{"aghl", "adlkgh"};
    String username = "producer1";
    String password = "producer1";

    if (args.length >= 4) {
      if (args[0].equals("--bootstrap-server")) {
        bootstrap_server = args[1];
        if (args[2].equals("--topic")) {
          topic = args[3];
          if (args[4].equals("--user")) {
            username = args[5];
            if (args[6].equals("--password")) {
              password = args[7];
            }
          }
        }
      }
    }

    //messages = Arrays.copyOfRange(args, 4, args.length);

    System.out.println ("bootstrap_server:" + bootstrap_server);
    System.out.println ("topic:" + topic);


    Producer p = new Producer();
    p.producer(bootstrap_server, topic, messages, username, password);
    

  }

  public void producer(String bootstrap_server, String topic, String[] messages, String username, String password) {

    /**
    short replicationFactor = 1;
    NewTopic topic = new NewTopic("topic", 3, replicationFactor);
    String bootstrap = "kafka:9092";
    */
    final Logger logger = LoggerFactory.getLogger(Producer.class); 

    // create Producer Properties
    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
    properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
    properties.setProperty(SaslConfigs.SASL_MECHANISM, "PLAIN");
    properties.setProperty(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"producer1\" password=\"producer1\";");
    
    NewTopic create = new NewTopic("test", 3, (short) 1);

    /*
    for (int i = 0; i < 10; i++) {
      String value = "hello world " + i;
      String key = "id " + i;
      // create the producer
      KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

      // create producer record
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
      logger.info("Key:" + key);

      // send data
      producer.send(record, new Callback() {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
          if (e == null) {
            logger.info("Received new metadata. \n "
                + "Topic:" + recordMetadata.topic() + "\n"
                + "Partition:" + recordMetadata.partition() + "\n"
                + "Offset:" + recordMetadata.offset() + "\n"
                + "Timestamp:" + recordMetadata.timestamp() + "\n");
          }
        }
      });
      producer.flush();
    }
    **/
  }
}
