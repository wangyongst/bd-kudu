package com.myweb.domain;

import java.io.Serializable;

public class Price implements Serializable {
    private Double price;
    private Double quantity;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
