package com.myweb.elasticsearch.dao;

import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {

    public List<DepthPriceRaw> findAllByTimestampBetween(long startTimestamp, long endTimestamp, Pageable pageable);

    public List<DepthPriceRaw> findAllByTimestampBetweenOrderByTimestampAsc(long startTimestamp, long endTimestamp, Pageable pageable);

    public List<DepthPriceRaw> findAllBySymbolInAndTimestampBetween(List<String> symbols, long startTimestamp, long endTimestamp, Pageable pageable);

    public List<DepthPriceRaw> findAllByCounterPartyInAndTimestampBetween(List<String> counterPartys, long startTimestamp, long endTimestamp, Pageable pageable);

    public List<DepthPriceRaw> findAllByCounterPartyInAndSymbolInAndTimestampBetween(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp, Pageable pageable);

    public void deleteAllByTimestampBetween(long startTimestamp, long endTimestamp);

}