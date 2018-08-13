package com.myweb.elasticsearch.dao;

import com.myweb.domain.DepthPriceRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {

    public List<DepthPriceRaw> findByTimestampBetweenOrderByTimestampAsc(long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findByTimestampBetweenOrderByTimestampDesc(long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findBySymbolInAndTimestampBetweenOrderByTimestampAsc(List<String> symbols, long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findBySymbolInAndTimestampBetweenOrderByTimestampDesc(List<String> symbols, long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findByCounterPartyInAndTimestampBetweenOrderByTimestampAsc(List<String> counterPartys, long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findByCounterPartyInAndTimestampBetweenOrderByTimestampDesc(List<String> counterPartys, long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampAsc(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp);

    public List<DepthPriceRaw> findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampDesc(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp);

}