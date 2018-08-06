package com.myweb.kafka;

import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

public class Listener {
    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @KafkaListener(topics = {"depth-price-raw"})
    public void depthPriceRaw(ConsumerRecord<?, ?> record) {
        System.out.println(record.value().toString());
    }

    @KafkaListener(topics = {"trade-history-raw"})
    public void tradeHistoryRaw(ConsumerRecord<?, ?> record) {
        System.out.println(record.value().toString());
    }
}