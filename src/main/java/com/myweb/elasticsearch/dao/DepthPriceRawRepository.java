package com.myweb.elasticsearch.dao;

import com.myweb.domain.DepthPriceRaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {

    public Page<DepthPriceRaw> findByTimestampBetween(long startTimestamp, long endTimestamp, Pageable pageable);

    public Page<DepthPriceRaw> findBySymbolInAndTimestampBetween(List<String> symbols, long startTimestamp, long endTimestamp, Pageable pageable);

    public Page<DepthPriceRaw> findByCounterPartyInAndTimestampBetween(List<String> counterPartys, long startTimestamp, long endTimestamp, Pageable pageable);

    public Page<DepthPriceRaw> findByCounterPartyInAndSymbolInAndTimestampBetween(List<String> counterPartys, List<String> symbols, long startTimestamp, long endTimestamp, Pageable pageable);

}