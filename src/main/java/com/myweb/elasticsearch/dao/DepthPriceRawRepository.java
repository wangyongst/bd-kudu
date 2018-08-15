package com.myweb.elasticsearch.dao;

import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {

    public List<DepthPriceRaw> findByTimestampBetween(long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findBySymbolInAndTimestampBetween(List<String> symbols, long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findByCounterPartyInAndTimestampBetween(List<String> counterPartys, long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findByCounterPartyInAndSymbolInAndTimestampBetween(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp);

}