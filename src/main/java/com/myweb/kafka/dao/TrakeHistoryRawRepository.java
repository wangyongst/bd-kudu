package com.myweb.kafka.dao;

import com.myweb.kafka.pojo.TradeHistoryRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TrakeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
}