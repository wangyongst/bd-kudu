package com.myweb.elasticsearch.dao;

import com.myweb.pojo.DepthPriceRaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {
    public Page<DepthPriceRaw> findAll(Pageable pageable);

    public Page<DepthPriceRaw> findAllBySymbolInAndTimestampBeforeAndTimestampAfter(List<String> symbols, int endTimestamp, int startTimestamp, Pageable pageable);

    public Page<DepthPriceRaw> findAllByCounterPartyInAndTimestampBeforeAndTimestampAfter(List<String> counterPartys, int endTimestamp, int startTimestamp, Pageable pageable);

    public Page<DepthPriceRaw> findAllByCounterPartyInAndSymbolInAndTimestampBeforeAndTimestampAfter(List<String> counterPartys, List<String> symbols, int endTimestamp, int startTimestamp, Pageable pageable);
}