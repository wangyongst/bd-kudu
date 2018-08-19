package com.myweb.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Price implements Serializable {
    private BigDecimal price;
    private BigDecimal quantity;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
