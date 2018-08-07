package com.myweb.elasticsearch.dao;

import com.myweb.pojo.TradeHistoryRaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
public interface TradeHistoryRawRepository extends ElasticsearchRepository<TradeHistoryRaw, Long> {
    public Page<TradeHistoryRaw> findAll(Pageable pageable);
}