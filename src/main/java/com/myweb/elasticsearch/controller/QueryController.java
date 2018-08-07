package com.myweb.elasticsearch.controller;

import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.pojo.DepthPriceRaw;
import com.myweb.pojo.TradeHistoryRaw;
import com.myweb.vo.Parameter;
import com.myweb.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class QueryController {
    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @PostMapping("/query")
    public Object queryDepthPriceRaw(@RequestBody Parameter parameter) {
        if (StringUtils.isBlank(parameter.getTableName()) || (!parameter.getTableName().equals("depth-price-raw") && !parameter.getTableName().equals("trade-history-raw"))) {
            Result result = new Result();
            result.setStatus(20001);
            result.setMessage("The parameter tableName must been depth-price-raw or trade-history-raw");
            return result;
        }
        if (parameter.getPage() == null || parameter.getPage().intValue() == 0) parameter.setPage(0);
        if (parameter.getPagesize() == null || parameter.getPagesize().intValue() == 0) parameter.setPagesize(10);
        Pageable pageable = null;
        Sort sort = null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            sort = new Sort(Sort.Direction.DESC, "timestamp");
        } else if (StringUtils.isBlank(parameter.getOrder()) || parameter.getOrder().equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, "timestamp");
        }
        pageable = new PageRequest(parameter.getPage().intValue(), parameter.getPagesize().intValue(), null);
        if (parameter.getCounterparty() == null && parameter.getSymbol() == null && parameter.getStartTimestamp() == null && parameter.getEndTimestamp() == null) {
            if (parameter.getTableName().equals("trade-history-raw")) {
                return depthPriceRawRepository.findAll(pageable);
            } else return tradeHistoryRawRepository.findAll(pageable);
        } else if (parameter.getCounterparty().size() == 0 && parameter.getSymbol().size() > 0) {
            if (parameter.getTableName().equals("trade-history-raw")) {
                return depthPriceRawRepository.findAllBySymbolInAndTimestampBeforeAndTimestampAfter(parameter.getSymbol(), parameter.getEndTimestamp().intValue(), parameter.getStartTimestamp().intValue(), pageable);
            } else return tradeHistoryRawRepository.findAllBySymbolInAndTimestampBeforeAndTimestampAfter(parameter.getSymbol(), parameter.getEndTimestamp().intValue(), parameter.getStartTimestamp().intValue(), pageable);
        } else if (parameter.getCounterparty().size() > 0 && parameter.getSymbol().size() == 0) {
            if (parameter.getTableName().equals("trade-history-raw")) {
                return depthPriceRawRepository.findAllByCounterPartyInAndTimestampBeforeAndTimestampAfter(parameter.getCounterparty(), parameter.getEndTimestamp().intValue(), parameter.getStartTimestamp().intValue(), pageable);
            } else return tradeHistoryRawRepository.findAllByCounterPartyInAndTimestampBeforeAndTimestampAfter(parameter.getCounterparty(), parameter.getEndTimestamp().intValue(), parameter.getStartTimestamp().intValue(), pageable);
        } else if (parameter.getCounterparty().size() > 0 && parameter.getSymbol().size() > 0) {
            if (parameter.getTableName().equals("trade-history-raw")) {
                return depthPriceRawRepository.findAllByCounterPartyInAndSymbolInAndTimestampBeforeAndTimestampAfter(parameter.getCounterparty(), parameter.getCounterparty(), parameter.getEndTimestamp().intValue(), parameter.getStartTimestamp().intValue(), pageable);
            } else
                return tradeHistoryRawRepository.findAllByCounterPartyInAndSymbolInAndTimestampBeforeAndTimestampAfter(parameter.getCounterparty(), parameter.getCounterparty(), parameter.getEndTimestamp().intValue(), parameter.getStartTimestamp().intValue(), pageable);

        }
        return null;
    }

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