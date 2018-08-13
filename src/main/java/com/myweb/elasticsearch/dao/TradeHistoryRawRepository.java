package com.myweb.elasticsearch.dao;

import com.myweb.domain.TradeHistoryRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
    public List<TradeHistoryRaw> findByTimestampBetweenOrderByTimestampAsc(long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findByTimestampBetweenOrderByTimestampDesc(long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findBySymbolInAndTimestampBetweenOrderByTimestampAsc(List<String> symbols, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findBySymbolInAndTimestampBetweenOrderByTimestampDesc(List<String> symbols, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findByCounterPartyInAndTimestampBetweenOrderByTimestampAsc(List<String> counterPartys, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findByCounterPartyInAndTimestampBetweenOrderByTimestampDesc(List<String> counterPartys, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampAsc(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp);

    public List<TradeHistoryRaw> findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampDesc(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp);
}