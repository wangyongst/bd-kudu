package com.myweb.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "trade-history", type = "trade-history-raw")
public class TradeHistoryRaw implements Serializable {
    @Field(store=true,type= FieldType.Keyword)
    private String counterParty;
    @Field(store=true,type= FieldType.Keyword)
    private String symbol;
    private String side;
    private String price;
    private String quantity;
    private Boolean marketMaker;
    private String refId;
    @Id
    @Field(store=true,type= FieldType.Long)
    private Long timestamp;

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

    public Boolean getMarketMaker() {
        return marketMaker;
    }

    public void setMarketMaker(Boolean marketMaker) {
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}