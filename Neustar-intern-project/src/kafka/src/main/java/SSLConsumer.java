import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLConsumer {

  public void consumer() {
    
    String bootstrap = "localhost:9093";
    String topic  = "topic2";
    String groupid = "new";

    final Logger logger = LoggerFactory.getLogger(SSLConsumer.class.getName());

    // create consumer configs
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupid);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
    properties.setProperty(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "C:/ssl-cert/client.truststore");
    properties.setProperty(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "password");
    properties.setProperty(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "C:/ssl-cert/varunm.keystore");
    properties.setProperty(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "password");
    properties.setProperty(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "password");
    // create consumer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

    // subscribe consumer to topics
    consumer.subscribe(Arrays.asList(topic));

    // poll for new data
    while(true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record: records) {
        //System.out.print(" Value: " + record.value() + " ,");
        //System.out.println("Partition:" + record.partition() + ", Offset: " + record.offset());
        logger.info("Key:" + record.key() + ", Value: " + record.value());
        logger.info("Partition:" + record.partition() + ", Offset: " + record.offset());
      }
      //consumer.close();
    }
  }
}
