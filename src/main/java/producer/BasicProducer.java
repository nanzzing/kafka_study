package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class BasicProducer {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Producer setting
        props.put("bootstrap.servers","localhost:9092, localhost:9094, localhost:9096");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        // Producer create with setting
        Producer<String, String> producer = new KafkaProducer<>(props);

        // disallocate after use
        producer.close();
    }
}
