package com.myweb.elasticsearch.dao;

import com.myweb.pojo.DepthPriceRaw;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {
}