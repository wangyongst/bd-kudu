package com.myweb.kafka.pojo;

import java.io.Serializable;

//@Document(indexName = "data-lake", type = "depth-price-raw", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class DepthPriceRaw implements Serializable {
    private String counterParty;
    private String symbol;
    private String timestamp;
    private Price bids;
    private Price asks;

    public Price getBids() {
        return bids;
    }

    public void setBids(Price bids) {
        this.bids = bids;
    }

    public Price getAsks() {
        return asks;
    }

    public void setAsks(Price asks) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}