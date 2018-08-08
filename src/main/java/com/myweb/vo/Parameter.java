package com.myweb.vo;

import java.util.List;

public class Parameter {

    private List<String> counterparty;
    private List<String> symbol;
    private Number startTimestamp;
    private Number endTimestamp;
    private Number page;
    private Number pagesize;
    private String order;

    public List<String> getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(List<String> counterparty) {
        this.counterparty = counterparty;
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

    public Number getPage() {
        return page;
    }

    public void setPage(Number page) {
        this.page = page;
    }

    public Number getPagesize() {
        return pagesize;
    }

    public void setPagesize(Number pagesize) {
        this.pagesize = pagesize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}




