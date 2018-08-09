package com.myweb.elasticsearch.service;

import com.anoyi.grpc.annotation.GrpcService;
import com.myweb.vo.Parameter;

@GrpcService(server = "OneGrpcService")
public interface OneService {

    public Object queryDepthPriceRaw(Parameter parameter);

    public Object queryTradeHistoryRaw(Parameter parameter);
}
