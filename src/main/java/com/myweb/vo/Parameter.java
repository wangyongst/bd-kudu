package com.myweb.vo;

import java.util.List;

public class Parameter {

    private List<String> counterParty;
    private List<String> symbol;
    private Number startTimestamp;
    private Number endTimestamp;
    private String order;

    public List<String> getCounterParty() {
        return counterParty;
    }

    public void setCounterParty(List<String> counterParty) {
        this.counterParty = counterParty;
    }

    public List<String> getSymbol() {
        return symbol;
    }

    public void setSymbol(List<String> symbol) {
        this.symbol = symbol;
    }

    public Number getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Number startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Number getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Number endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}




