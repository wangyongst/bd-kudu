package com.myweb.elasticsearch.controller;

import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
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

//{
//    "counterparty":["huobi"],
//    "symbol":["EOS-BTC","ETH-USDT"],
//    "startTimestamp":0,
//        "endTimestamp":1533900063164,
//        "page":0,
//        "pagesize":20,
//        "order":"asc"
//}

    @PostMapping("/query/trade-history-raw")
    public Object queryTradeHistoryRaw(@RequestBody Parameter parameter) {
        return oneService.queryTradeHistoryRaw(parameter);
    }
}