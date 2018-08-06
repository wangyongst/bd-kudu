package com.myweb.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

//

public class Listener {
    //protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @KafkaListener(topics = {"test"})
    public void listenReal(ConsumerRecord<?, ?> record) {
        System.out.println("kafka的key: " + record.key());
        System.out.println("kafka的value: " + record.value().toString());
    }

    @KafkaListener(topics = {"test"})
    public void listenHistory(ConsumerRecord<?, ?> record) {
        System.out.println("kafka的key: " + record.key());
        System.out.println("kafka的value: " + record.value().toString());
    }
}