package com.myweb.kafka.dao;

import com.myweb.kafka.pojo.TradeHistoryRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
}