package com.myweb.elasticsearch.service;

import com.anoyi.grpc.annotation.GrpcService;
import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import com.myweb.vo.Parameter;

import java.util.List;

@GrpcService(server = "oneService")
public interface OneService {

    public List<DepthPriceRaw> queryDepthPriceRaw(Parameter parameter);

    public List<TradeHistoryRaw> queryTradeHistoryRaw(Parameter parameter);

    public boolean transDepthPriceRaw(Parameter parameter);

    public boolean transTradeHistoryRaw(Parameter parameter);

    public boolean restoreDepthPriceRaw(Parameter parameter);

    public boolean restoreTradeHistoryRaw(Parameter parameter);
}
