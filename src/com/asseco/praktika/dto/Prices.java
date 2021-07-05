package com.asseco.praktika.dto;

import java.math.BigDecimal;

public class Prices {

    private final BigDecimal shippingPrice;
    private final BigDecimal shippingDiscount;

    public Prices(BigDecimal shippingPrice, BigDecimal shippingDiscount) {
        this.shippingPrice = shippingPrice;
        this.shippingDiscount = shippingDiscount;
    }

    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    public BigDecimal getShippingDiscount() {
        return shippingDiscount;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "shippingPrice=" + shippingPrice +
                ", shippingDiscount=" + shippingDiscount +
                '}';
    }
}
