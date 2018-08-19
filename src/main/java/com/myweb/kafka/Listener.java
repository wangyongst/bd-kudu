package com.myweb.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class Listener {

    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @KafkaListener(topics = {"depth-price-raw"})
    public void depthPriceRaw(ConsumerRecord<?, ?> record) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            DepthPriceRaw depthPriceRaw = mapper.readValue(record.value().toString(), DepthPriceRaw.class);
            depthPriceRawRepository.save(depthPriceRaw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = {"trade-history-raw"})
    public void tradeHistoryRaw(ConsumerRecord<?, ?> record) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TradeHistoryRaw tradeHistoryRaw = mapper.readValue(record.value().toString(), TradeHistoryRaw.class);
            if(tradeHistoryRaw.getSide() == null) tradeHistoryRaw.setSide("null");
            tradeHistoryRawRepository.save(tradeHistoryRaw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}