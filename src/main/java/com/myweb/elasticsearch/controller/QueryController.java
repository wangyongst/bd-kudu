package com.myweb.elasticsearch.controller;

import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import com.myweb.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {
    @Autowired
    private OneService oneService;

    @PostMapping("/query/depth-price-raw")
    public Object queryDepthPriceRaw(@RequestBody Parameter parameter) {
        return oneService.queryDepthPriceRaw(parameter);
    }
//
//    {
//        "counterparty":["huobi"],
//        "symbol":["EOS-BTC"],
//        "startTimestamp":1533617000000,
//            "endTimestamp":1533617006343,
//            "page":1,
//            "pagesize":20,
//            "order":"desc"
//    }
//
    @PostMapping("/query/trade-history-raw")
    public Object queryTradeHistoryRaw(@RequestBody Parameter parameter) {
        return oneService.queryTradeHistoryRaw(parameter);
    }

    @PostMapping("/query/depth-price-raw2")
    public Object queryDepthPriceRaw2(@RequestBody Parameter parameter) {
        return oneService.queryDepthPriceRaw2(parameter);
    }
}