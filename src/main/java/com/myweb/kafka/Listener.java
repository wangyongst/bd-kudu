package com.myweb.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

    private static final Logger logger = LogManager.getLogger(Listener.class);

    @KafkaListener(topics = {"csv"})
    public void csv(ConsumerRecord<?, ?> record) {
        logger.info("kafka的key: " + record.key());
        logger.info("kafka的value: " + record.value().toString());
    }

    @KafkaListener(topics = {"api"})
    public void api(ConsumerRecord<?, ?> record) {
        logger.info("kafka的key: " + record.key());
        logger.info("kafka的value: " + record.value().toString());
    }
}