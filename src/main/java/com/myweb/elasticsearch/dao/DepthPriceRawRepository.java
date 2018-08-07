package com.myweb.elasticsearch.dao;

import com.myweb.pojo.DepthPriceRaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DepthPriceRawRepository extends ElasticsearchRepository<DepthPriceRaw, Long> {
    public Page<DepthPriceRaw> findAll(Pageable pageable);
}