package com.myweb.elasticsearch.controller;

import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.pojo.DepthPriceRaw;
import com.myweb.pojo.TradeHistoryRaw;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class QueryController {
    @Qualifier("DepthPriceRawRepository")
    private DepthPriceRawRepository depthPriceRawRepository;

    @Qualifier("TradeHistoryRawRepository")
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