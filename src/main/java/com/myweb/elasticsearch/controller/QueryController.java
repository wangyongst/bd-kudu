package com.myweb.elasticsearch.controller;

import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {
    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @RequestMapping("/query/depth-price-raw")
    public Object queryDepthPriceRaw() {
        Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
        Pageable pageable = new PageRequest(0, 10, sort);
        return depthPriceRawRepository.findAll(pageable);
    }

    @RequestMapping("/query/trade-history-raw")
    public Object queryTradeHistoryRaw() {
        Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
        Pageable pageable = new PageRequest(0, 10, sort);
        return tradeHistoryRawRepository.findAll(pageable);
    }
}