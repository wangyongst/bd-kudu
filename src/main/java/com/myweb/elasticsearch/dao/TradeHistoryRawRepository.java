package com.myweb.elasticsearch.dao;

import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
    public Page<TradeHistoryRaw> findByTimestampBetween(long startTimestamp, long endTimestamp, Pageable pageable);

    public Page<TradeHistoryRaw> findBySymbolInAndTimestampBetween(List<String> symbols, long startTimestamp, long endTimestamp, Pageable pageable);

    public Page<TradeHistoryRaw> findByCounterPartyInAndTimestampBetween(List<String> counterPartys, long startTimestamp, long endTimestamp, Pageable pageable);

    public Page<TradeHistoryRaw> findByCounterPartyInAndSymbolInAndTimestampBetween(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp, Pageable pageable);
}