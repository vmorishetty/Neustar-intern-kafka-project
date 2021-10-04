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

public class SimpleProducer {

    public void producer() {
        String username = "producer1";
        String password = "producer1";
        String bootstrap = "kafka:9092";
        String topic = "test_topic";
        

        final Logger logger = LoggerFactory.getLogger(Producer.class);

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        properties.setProperty(SaslConfigs.SASL_MECHANISM, "PLAIN");
        properties.setProperty(SaslConfigs.SASL_JAAS_CONFIG,
        "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\""+ password + "\";");

        

        
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
            producer.close();
        }
    
    }
}
