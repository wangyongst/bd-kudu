package com.myweb.elasticsearch.service.impl;


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
import org.springframework.stereotype.Service;

@Service("OneService")
@SuppressWarnings("All")
public class OneServiceImpl implements OneService {

    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @Override
    public Object queryDepthPriceRaw(Parameter parameter) {
        Result result = new Result();
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) {
            result.setStatus(2001);
            result.setMessage("Both of startTimestamp and endTimestamp can not be empty");
            return result;
        }
        if (parameter.getPage() == null) parameter.setPage(0);
        if (parameter.getPagesize() == null || parameter.getPagesize().intValue() == 0) parameter.setPagesize(10);
        Sort sort = null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            sort = new Sort(Sort.Direction.DESC, "timestamp");
        } else if (StringUtils.isBlank(parameter.getOrder()) || parameter.getOrder().equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, "timestamp");
        }
        Pageable pageable = PageRequest.of(parameter.getPage().intValue(), parameter.getPagesize().intValue(), sort);
        if (parameter.getCounterparty() == null && parameter.getSymbol() == null) {
            return depthPriceRawRepository.findByTimestampBetween(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() == null && parameter.getSymbol() != null) {
            return depthPriceRawRepository.findBySymbolInAndTimestampBetween(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() != null && parameter.getSymbol() == null) {
            return depthPriceRawRepository.findByCounterPartyInAndTimestampBetween(parameter.getCounterparty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() != null && parameter.getSymbol() != null) {
            return depthPriceRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetween(parameter.getCounterparty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        }
        return "";
    }


    @Override
    public Object queryDepthPriceRaw2(Parameter parameter) {
        Result result = new Result();
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) {
            result.setStatus(2001);
            result.setMessage("Both of startTimestamp and endTimestamp can not be empty");
            return result;
        }
        if (parameter.getPage() == null) parameter.setPage(0);
        if (parameter.getPagesize() == null || parameter.getPagesize().intValue() == 0) parameter.setPagesize(10);
        Sort sort = null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            sort = new Sort(Sort.Direction.DESC, "timestamp");
        } else if (StringUtils.isBlank(parameter.getOrder()) || parameter.getOrder().equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, "timestamp");
        }
        Pageable pageable = PageRequest.of(parameter.getPage().intValue(), parameter.getPagesize().intValue(), sort);
        if (parameter.getCounterparty() == null && parameter.getSymbol() == null) {
            return depthPriceRawRepository.findByTimestampBetween(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() == null && parameter.getSymbol() != null) {
            return depthPriceRawRepository.findBySymbolInAndTimestampBetween(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() != null && parameter.getSymbol() == null) {
            return depthPriceRawRepository.findByCounterPartyInAndTimestampBetween(parameter.getCounterparty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() != null && parameter.getSymbol() != null) {
            return depthPriceRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetween(parameter.getCounterparty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        }
        return "";
    }



    public Object queryTradeHistoryRaw(Parameter parameter) {
        Result result = new Result();
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) {
            result.setStatus(2001);
            result.setMessage("Both of startTimestamp and endTimestamp can not be empty");
            return result;
        }
        if (parameter.getPage() == null) parameter.setPage(0);
        if (parameter.getPagesize() == null || parameter.getPagesize().intValue() == 0) parameter.setPagesize(10);
        Sort sort = null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            sort = new Sort(Sort.Direction.DESC, "timestamp");
        } else if (StringUtils.isBlank(parameter.getOrder()) || parameter.getOrder().equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, "timestamp");
        }
        Pageable pageable = PageRequest.of(parameter.getPage().intValue(), parameter.getPagesize().intValue(), sort);
        if (parameter.getCounterparty() == null && parameter.getSymbol() == null) {
            return tradeHistoryRawRepository.findByTimestampBetween(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() == null && parameter.getSymbol() != null) {
            return tradeHistoryRawRepository.findBySymbolInAndTimestampBetween(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() != null && parameter.getSymbol() == null) {
            return tradeHistoryRawRepository.findByCounterPartyInAndTimestampBetween(parameter.getCounterparty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        } else if (parameter.getCounterparty() != null && parameter.getSymbol() != null) {
            return tradeHistoryRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetween(parameter.getCounterparty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        }
        return "";
    }
}
