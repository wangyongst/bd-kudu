package com.myweb.kafka.controller;

import com.myweb.kafka.dao.DepthPriceRawRepository;
import com.myweb.kafka.dao.TradeHistoryRawRepository;
import com.myweb.kafka.pojo.DepthPriceRaw;
import com.myweb.kafka.pojo.TradeHistoryRaw;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class QueryController {
    //@Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    //@Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @RequestMapping("/query/depth-price-raw")
    public void queryDepthPriceRaw() {
        String queryString = "springboot";//搜索关键字
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        Iterable<DepthPriceRaw> searchResult = depthPriceRawRepository.search(builder);
        Iterator<DepthPriceRaw> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @RequestMapping("/query/trade-history-raw")
    public void queryTradeHistoryRaw() {
        String queryString = "springboot";//搜索关键字
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        Iterable<TradeHistoryRaw> searchResult = tradeHistoryRawRepository.search(builder);
        Iterator<TradeHistoryRaw> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}