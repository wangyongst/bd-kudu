package com.myweb.elasticsearch.service.impl;


import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import com.myweb.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        Result result = ServiceUtils.checkParameter(parameter);
        if (result.getStatus() != 0) return result;
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
        return result;
    }

    public Object queryTradeHistoryRaw(Parameter parameter) {
        Result result = ServiceUtils.checkParameter(parameter);
        if (result.getStatus() != 0) return result;
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
        return result;
    }
}
