package com.asseco.praktika.service;

import com.asseco.praktika.dto.Prices;
import com.asseco.praktika.dto.Transactions;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public class DiscountCalculator {

    private BigDecimal shippingDiscount = BigDecimal.ZERO;
    private BigDecimal finalShippingPrice = BigDecimal.ZERO;
    private BigDecimal shippingDiscountLimit = BigDecimal.valueOf(10);
    private final List<Prices> shippingPrices = new ArrayList<>();
    private final Hashtable<Integer, String> packageSizeValue = new Hashtable<>();
    private final Hashtable<Integer, String> providerValue = new Hashtable<>();

    public List<Prices> getShippingPrices() {
        return shippingPrices;
    }

    public void calculateDiscount(List<Transactions> transactions) {

        int everyThirdDay = 1, thirdPackageFree = 1, discountLimitCheck = 1, nextMonth = 2;

        packageSizeValue.put(1, "S");
        packageSizeValue.put(2, "M");
        packageSizeValue.put(3, "L");

        providerValue.put(1, "LP");
        providerValue.put(2, "MR");

        BigDecimal priceOfSLP = BigDecimal.valueOf(1.5);
        BigDecimal priceOfSMR = BigDecimal.valueOf(2);
        BigDecimal priceOfMLP = BigDecimal.valueOf(4.9);
        BigDecimal priceOfMMR = BigDecimal.valueOf(3);
        BigDecimal priceOfLLP = BigDecimal.valueOf(6.9);
        BigDecimal priceOfLMR = BigDecimal.valueOf(4);
        BigDecimal positiveDiscountLimit;

        for (Transactions tran : transactions) {
            Calendar calendar = Calendar.getInstance();
            if(tran.getShippingDate() != null){
                calendar.setTime(tran.getShippingDate());

            }
            int currentMonth = calendar.get(Calendar.MONTH);

            String currentPackageSize = tran.getShippingPackageSize();
            String currentProvider = tran.getShippingProvider();
            String currentTransaction = "";

            boolean SLP = currentPackageSize.equals(packageSizeValue.get(1)) && currentProvider.equals(providerValue.get(1));
            boolean SMR = currentPackageSize.equals(packageSizeValue.get(1)) && currentProvider.equals(providerValue.get(2));
            boolean MLP = currentPackageSize.equals(packageSizeValue.get(2)) && currentProvider.equals(providerValue.get(1));
            boolean MMR = currentPackageSize.equals(packageSizeValue.get(2)) && currentProvider.equals(providerValue.get(2));
            boolean LLP = currentPackageSize.equals(packageSizeValue.get(3)) && currentProvider.equals(providerValue.get(1));
            boolean LMR = currentPackageSize.equals(packageSizeValue.get(3)) && currentProvider.equals(providerValue.get(2));

            if (SLP) currentTransaction = "SLP";
            else if (SMR) currentTransaction = "SMR";
            else if (MLP) currentTransaction = "MLP";
            else if (MMR) currentTransaction = "MMR";
            else if (LLP) currentTransaction = "LLP";
            else if (LMR) currentTransaction = "LMR";

            switch (currentTransaction) {
                case "SLP" -> {
                    finalShippingPrice = priceOfSLP;
                    if (discountLimitCheck > 0) {
                        shippingDiscount = BigDecimal.ZERO;
                        shippingDiscountLimit = shippingDiscountLimit.subtract(shippingDiscount);
                    } else {
                        shippingDiscount = shippingDiscountLimit;
                        shippingDiscountLimit = BigDecimal.ZERO;
                    }
                }

                case "SMR" -> {
                    shippingDiscount = priceOfSMR.subtract(priceOfSLP);
                    if (discountLimitCheck > 0) {
                        positiveDiscountLimit = shippingDiscountLimit;
                        shippingDiscountLimit = shippingDiscountLimit.subtract(shippingDiscount);
                        if (shippingDiscountLimit.compareTo(BigDecimal.ZERO) < 0) {
                            shippingDiscountLimit = positiveDiscountLimit;
                            shippingDiscount = positiveDiscountLimit;
                            shippingDiscountLimit = BigDecimal.ZERO;
                            finalShippingPrice = priceOfSMR.subtract(shippingDiscount);
                        } else if (shippingDiscountLimit.compareTo(BigDecimal.ZERO) == 0) {
                            shippingDiscount = BigDecimal.ZERO;
                            shippingDiscountLimit = BigDecimal.ZERO;
                            finalShippingPrice = priceOfSMR.subtract(shippingDiscount);
                        } else {
                            shippingDiscount = priceOfSMR.subtract(priceOfSLP);
                            finalShippingPrice = priceOfSMR.subtract(shippingDiscount);
                        }
                    } else {
                        shippingDiscountLimit = BigDecimal.ZERO;
                        finalShippingPrice = priceOfSMR.subtract(shippingDiscount);
                    }
                }

                case "MLP" -> {
                    finalShippingPrice = priceOfMLP;
                    if (discountLimitCheck > 0) {
                        shippingDiscount = BigDecimal.ZERO;
                        shippingDiscountLimit = shippingDiscountLimit.subtract(shippingDiscount);
                    } else {
                        shippingDiscount = shippingDiscountLimit;
                        shippingDiscountLimit = BigDecimal.ZERO;
                    }
                }

                case "MMR" -> {
                    finalShippingPrice = priceOfMMR;
                    if (discountLimitCheck > 0) {
                        shippingDiscount = BigDecimal.ZERO;
                        shippingDiscountLimit = shippingDiscountLimit.subtract(shippingDiscount);
                    } else {
                        shippingDiscount = shippingDiscountLimit;
                        shippingDiscountLimit = BigDecimal.ZERO;
                    }
                }

                case "LLP" -> {
                    if (everyThirdDay % 3 == 0 && currentMonth == thirdPackageFree) {
                        if (discountLimitCheck > 0) {
                            finalShippingPrice = BigDecimal.ZERO;
                            shippingDiscount = priceOfLLP;
                            shippingDiscountLimit = shippingDiscountLimit.subtract(shippingDiscount);
                            thirdPackageFree++;
                        } else {
                            finalShippingPrice = priceOfLLP;
                            shippingDiscountLimit = BigDecimal.ZERO;
                            shippingDiscount = shippingDiscountLimit;
                        }
                    } else {
                        finalShippingPrice = priceOfLLP;
                        if (discountLimitCheck > 0) {
                            shippingDiscount = BigDecimal.ZERO;
                            positiveDiscountLimit = shippingDiscountLimit;
                            shippingDiscountLimit = shippingDiscountLimit.subtract(shippingDiscount);
                            if (shippingDiscountLimit.compareTo(BigDecimal.ZERO) < 0) {
                                shippingDiscountLimit = positiveDiscountLimit;
                            }
                        } else {
                            shippingDiscountLimit = BigDecimal.ZERO;
                            shippingDiscount = shippingDiscountLimit;
                        }
                    }
                    everyThirdDay++;
                }

                case "LMR" -> {
                    finalShippingPrice = priceOfLMR;
                    if (discountLimitCheck > 0) {
                        shippingDiscount = BigDecimal.ZERO;
                        shippingDiscountLimit = shippingDiscountLimit.subtract(shippingDiscount);
                    } else {
                        shippingDiscount = shippingDiscountLimit;
                        shippingDiscountLimit = BigDecimal.ZERO;
                    }
                }

                default -> {
                    finalShippingPrice = BigDecimal.ZERO;
                    shippingDiscount = BigDecimal.ZERO;
                }
            }

            if (currentMonth == nextMonth) {
                shippingDiscountLimit = BigDecimal.valueOf(10).subtract(shippingDiscount);
                nextMonth++;
            }

            discountLimitCheck = shippingDiscountLimit.compareTo(BigDecimal.ZERO);
            shippingPrices.add(new Prices(finalShippingPrice.setScale(2, RoundingMode.HALF_UP), shippingDiscount.setScale(2, RoundingMode.HALF_UP)));
        }
    }
}