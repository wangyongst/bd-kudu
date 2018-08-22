package com.myweb.elasticsearch.dao;

import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {

    public List<TradeHistoryRaw> findAllByTimestampBetweenOrderByTimestampAsc(long startTimestamp, long endTimestamp, Pageable pageable);

    public void deleteAllByTimestampBetween(long startTimestamp, long endTimestamp);

}