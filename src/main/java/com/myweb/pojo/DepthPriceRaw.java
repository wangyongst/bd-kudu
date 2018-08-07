package com.myweb.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;

@Document(indexName = "depth-price-raw", type = "depth-price-raw", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class DepthPriceRaw implements Serializable {

    private String counterParty;
    private String symbol;
    @Id
    private Number timestamp;
    private List<Price> bids;
    private List<Price> asks;

    public List<Price> getBids() {
        return bids;
    }

    public void setBids(List<Price> bids) {
        this.bids = bids;
    }

    public List<Price> getAsks() {
        return asks;
    }

    public void setAsks(List<Price> asks) {
        this.asks = asks;
    }

    public String getCounterParty() {
        return counterParty;
    }

    public void setCounterParty(String counterParty) {
        this.counterParty = counterParty;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Number getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Number timestamp) {
        this.timestamp = timestamp;
    }
}