package com.myweb.elasticsearch.service;


import com.myweb.vo.Parameter;

public interface OneService {

    public Object queryDepthPriceRaw(Parameter parameter);

    public Object queryTradeHistoryRaw(Parameter parameter);

    public Object queryDepthPriceRaw2(Parameter parameter);
}
