package com.myweb.kafka.pojo;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "data-lake", type = "trade-history-raw", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class TradeHistoryRaw implements Serializable {
    private String counterParty;
    private String symbol;
    private String side;
    private String price;
    private String quantity;
    private String marketMaker;
    private String refId;
    private String timestamp;

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMarketMaker() {
        return marketMaker;
    }

    public void setMarketMaker(String marketMaker) {
        this.marketMaker = marketMaker;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
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