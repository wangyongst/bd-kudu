package com.myweb.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

    private static final Logger logger = LogManager.getLogger(Listener.class);
    @KafkaListener(topics = {"test"})
    public void test(ConsumerRecord<?, ?> record) {
        logger.info("kafka的key: " + record.key());
        logger.info("kafka的value: " + record.value().toString());
    }
}