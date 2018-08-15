package com.myweb.elasticsearch.dao;

import com.myweb.domain.TradeHistoryRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
    public List<TradeHistoryRaw> findByTimestampBetween(long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findBySymbolInAndTimestampBetween(List<String> symbols, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findByCounterPartyInAndTimestampBetween(List<String> counterPartys, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findByCounterPartyInAndSymbolInAndTimestampBetween(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp);

    public void deleteAllByTimestampBetween(long startTimestamp, long endTimestamp);
}