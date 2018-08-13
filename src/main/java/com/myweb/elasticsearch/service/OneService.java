package com.myweb.elasticsearch.service;

import com.anoyi.grpc.annotation.GrpcService;
import com.myweb.vo.Parameter;

@GrpcService(server = "oneService")
public interface OneService {

    public Object queryDepthPriceRaw(Parameter parameter);

    public Object queryTradeHistoryRaw(Parameter parameter);

    public boolean transDepthPriceRaw(Parameter parameter);

    public boolean transTradeHistoryRaw(Parameter parameter);

    public boolean restoreDepthPriceRaw(Parameter parameter);

    public boolean restoreTradeHistoryRaw(Parameter parameter);
}
