package com.myweb.elasticsearch.dao;

import com.myweb.pojo.TradeHistoryRaw;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
}