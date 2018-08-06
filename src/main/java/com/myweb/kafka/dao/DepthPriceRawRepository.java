package com.myweb.kafka.dao;

import com.myweb.kafka.pojo.DepthPriceRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {
}