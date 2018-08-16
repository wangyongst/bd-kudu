package com.myweb.elasticsearch.dao;

import com.myweb.domain.TradeHistoryRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
    public List<TradeHistoryRaw> findAllByTimestampBetween(long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findAllBySymbolInAndTimestampBetween(List<String> symbols, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findAllByCounterPartyInAndTimestampBetween(List<String> counterPartys, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findAllByCounterPartyInAndSymbolInAndTimestampBetween(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp);

    public void deleteAllByTimestampBetween(long startTimestamp, long endTimestamp);
}