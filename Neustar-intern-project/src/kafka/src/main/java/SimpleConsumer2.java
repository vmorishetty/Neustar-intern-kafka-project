import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleConsumer2 {

  public void consumer() {
    
    String bootstrap = "kafka:9092";
    String topic  = "test_topic";
    String groupid = "consumer-group";
    String username = "consumer1";
    String password = "consumer1";

    //final Logger logger = LoggerFactory.getLogger(SimpleConsumer2.class.getName());

    // create consumer configs
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupid);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
    properties.setProperty(SaslConfigs.SASL_MECHANISM, "PLAIN");
    properties.setProperty(SaslConfigs.SASL_JAAS_CONFIG, 
    "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + password +"\";");

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
      //consumer.close();
    }
  }
}
