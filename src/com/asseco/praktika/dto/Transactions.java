package com.asseco.praktika.dto;

import java.util.Date;

public class Transactions {

    private final Date shippingDate;
    private final String shippingPackageSize;
    private final String shippingProvider;
    private final String invalidDate;

    public Transactions(Date shippingDate, String shippingPackageSize, String shippingProvider, String invalidDate) {
        this.shippingDate = shippingDate;
        this.shippingPackageSize = shippingPackageSize;
        this.shippingProvider = shippingProvider;
        this.invalidDate = invalidDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public String getShippingPackageSize() {
        return shippingPackageSize;
    }

    public String getShippingProvider() {
        return shippingProvider;
    }

    public String getInvalidDate() {
        return invalidDate;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "shippingDate=" + shippingDate +
                ", shippingPackageSize='" + shippingPackageSize + '\'' +
                ", shippingProvider='" + shippingProvider + '\'' +
                ", invalidDate='" + invalidDate + '\'' +
                '}';
    }
}