package com.myweb.elasticsearch.service.impl;


import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OneService")
@SuppressWarnings("All")
public class OneServiceImpl implements OneService {

    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @Override
    public List<DepthPriceRaw> queryDepthPriceRaw(Parameter parameter) {
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("asc")) {
            if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
                return depthPriceRawRepository.findByTimestampBetweenOrderByTimestampAsc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
                return depthPriceRawRepository.findBySymbolInAndTimestampBetweenOrderByTimestampAsc(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
                return depthPriceRawRepository.findByCounterPartyInAndTimestampBetweenOrderByTimestampAsc(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
                return depthPriceRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampAsc(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            }
        } else if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
                return depthPriceRawRepository.findByTimestampBetweenOrderByTimestampDesc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
                return depthPriceRawRepository.findBySymbolInAndTimestampBetweenOrderByTimestampDesc(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
                return depthPriceRawRepository.findByCounterPartyInAndTimestampBetweenOrderByTimestampDesc(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
                return depthPriceRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampDesc(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            }
        }
        return null;
    }

    @Override
    public List<TradeHistoryRaw> queryTradeHistoryRaw(Parameter parameter) {
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("asc")) {
            if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
                return tradeHistoryRawRepository.findByTimestampBetweenOrderByTimestampAsc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
                return tradeHistoryRawRepository.findBySymbolInAndTimestampBetweenOrderByTimestampAsc(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
                return tradeHistoryRawRepository.findByCounterPartyInAndTimestampBetweenOrderByTimestampAsc(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
                return tradeHistoryRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampAsc(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            }
        } else if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
                return tradeHistoryRawRepository.findByTimestampBetweenOrderByTimestampDesc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
                return tradeHistoryRawRepository.findBySymbolInAndTimestampBetweenOrderByTimestampDesc(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
                return tradeHistoryRawRepository.findByCounterPartyInAndTimestampBetweenOrderByTimestampDesc(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
                return tradeHistoryRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampDesc(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            }
        }
        return null;
    }

    @Override
    public void transDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRaws = queryDepthPriceRaw(parameter);
        //file avro

        //delete
        depthPriceRawRepository.deleteAll(depthPriceRaws);
    }

    @Override
    public void transTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRaws = queryTradeHistoryRaw(parameter);
        //file avro

        //delete
        tradeHistoryRawRepository.deleteAll(tradeHistoryRaws);
    }

    @Override
    public void restoreDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRaws = queryDepthPriceRaw(parameter);
        //file avro

        //delete
        depthPriceRawRepository.deleteAll(depthPriceRaws);
    }

    @Override
    public void restoreTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRaws = queryTradeHistoryRaw(parameter);
        //file avro

        //delete
        tradeHistoryRawRepository.deleteAll(tradeHistoryRaws);
    }
}
