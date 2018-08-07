package com.myweb.elasticsearch.dao;

import com.myweb.pojo.TradeHistoryRaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
    public Page<TradeHistoryRaw> findAll(Pageable pageable);

    public Page<TradeHistoryRaw> findAllBySymbolInAndTimestampBeforeAndTimestampAfter(List<String> symbols, int endTimestamp, int startTimestamp, Pageable pageable);

    public Page<TradeHistoryRaw> findAllByCounterPartyInAndTimestampBeforeAndTimestampAfter(List<String> counterPartys, int endTimestamp, int startTimestamp, Pageable pageable);

    public Page<TradeHistoryRaw> findAllByCounterPartyInAndSymbolInAndTimestampBeforeAndTimestampAfter(List<String> counterPartys, List<String> symbols, int endTimestamp, int startTimestamp, Pageable pageable);
}