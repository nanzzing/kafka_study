package producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class BasicProducer {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Producer setting
        props.put("bootstrap.servers","localhost:9092, localhost:9094, localhost:9096");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        // 프로듀서 아이디 설정
        props.put("client.id", "order-producer");

        // acks: 메시지 전송을 성공으로 판단하기 위해 필요한 브로커의 확인 수준
        // 0: 브로커의 확인 응답을 기다리지 않음
        // 1: 리더가 메시지를 기록하면 성공
        // all: 해당 파티션의 ISR 전체가 메시지를 기록한 것을 확인하면 성공
        props.put("acks", "all");


        // Producer create with setting
        Producer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record =
                new ProducerRecord<>(
                        "CustomerCountry",
                        "Precision Products",
                        "France"
                ); // 각각 topic, key, value 이다.

        // 1. 파이어 앤 포겟
        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. 동기 전송
        try {
            producer.send(record).get();
        } catch(Exception e) {
            e.printStackTrace();
        }

        // 3. 비동기 전송
        try {
            producer.send(record, new DemoProducerCallback());
        } catch(Exception e) {
            e.printStackTrace();
        }


        // disallocate after use
        producer.close();
    }

    // 오류를 위한 Callback 등록
    private static class DemoProducerCallback implements Callback {
        @Override
        public void onCompletion (RecordMetadata recordMetadata, Exception e) {
            if(e != null) {
                e.printStackTrace();
            }
            else {
                System.out.println("전송 성공!");
                System.out.println("토픽: " + recordMetadata.topic());
                System.out.println("파티션: " + recordMetadata.partition());
                System.out.println("오프셋: " + recordMetadata.offset());
            }
        }
    }
}
