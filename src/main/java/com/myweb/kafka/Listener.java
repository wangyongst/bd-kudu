package com.myweb.kafka;

import com.myweb.kafka.pojo.DepthPriceRaw;
import com.myweb.kafka.pojo.TradeHistoryRaw;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

    @KafkaListener(topics = {"depth-price-raw"})
    public void depthPriceRaw(ConsumerRecord<?, ?> record) {
        //DepthPriceRaw depthPriceRaw = (DepthPriceRaw) record.value();
        System.out.println(record.value());
    }

//    @KafkaListener(topics = {"trade-history-raw"})
//    public void tradeHistoryRaw(ConsumerRecord<?, ?> record) {
//        //TradeHistoryRaw tradeHistoryRaw = (TradeHistoryRaw) record.value();
//        System.out.println(record.value());
//    }
}