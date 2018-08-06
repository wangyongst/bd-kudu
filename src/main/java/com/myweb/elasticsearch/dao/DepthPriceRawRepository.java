package com.myweb.elasticsearch.dao;

import com.myweb.pojo.DepthPriceRaw;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {
}