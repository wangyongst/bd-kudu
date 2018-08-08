package com.myweb.domain;

import java.io.Serializable;

public class Price implements Serializable {
    private Number price;
    private Number quantity;

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public Number getQuantity() {
        return quantity;
    }

    public void setQuantity(Number quantity) {
        this.quantity = quantity;
    }
}
