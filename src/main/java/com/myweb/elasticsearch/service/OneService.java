package com.myweb.elasticsearch.service;

import com.anoyi.grpc.annotation.GrpcService;
import com.myweb.vo.Parameter;

@GrpcService(server = "oneService")
public interface OneService {

    public Object queryDepthPriceRaw(Parameter parameter);

    public Object queryTradeHistoryRaw(Parameter parameter);

    public void transDepthPriceRaw(Parameter parameter);

    public void transTradeHistoryRaw(Parameter parameter);

    public void restoreDepthPriceRaw(Parameter parameter);

    public void restoreTradeHistoryRaw(Parameter parameter);
}
