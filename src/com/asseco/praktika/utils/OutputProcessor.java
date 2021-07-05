package com.asseco.praktika.utils;

import com.asseco.praktika.dto.Prices;
import com.asseco.praktika.dto.Transactions;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class OutputProcessor {

    public void writeValues(List<Transactions> shippingTransactions, List<Prices> shippingPrices, String outputFileName, String outputFileType) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int shippingDiscountCheck, shippingPricesCheck;

        if (!InputProcessor.isInvalidInputFile()) {
            try (FileWriter fileWriter = new FileWriter(outputFileName + "." + outputFileType)) {
                PrintWriter printWriter = new PrintWriter(fileWriter);

                for (int i = 0; i < shippingPrices.size(); i++) {
                    shippingDiscountCheck = shippingPrices.get(i).getShippingDiscount().compareTo(BigDecimal.ZERO);
                    shippingPricesCheck = shippingPrices.get(i).getShippingPrice().compareTo(BigDecimal.ZERO);

                    if (shippingDiscountCheck == 0 && shippingPricesCheck == 0) {
                        printWriter.println(shippingTransactions.get(i).getInvalidDate() + " " + shippingTransactions.get(i).getShippingPackageSize() + " Ignored");
                    } else if (shippingTransactions.get(i).getShippingDate() == null) {
                        printWriter.println(shippingTransactions.get(i).getInvalidDate() + " Ignored");
                    } else {
                        if (shippingTransactions.get(i).getShippingDate() != null) {
                            if (shippingDiscountCheck > 0) {
                                printWriter.println(dateFormat.format(shippingTransactions.get(i).getShippingDate())
                                        + " " + shippingTransactions.get(i).getShippingPackageSize()
                                        + " " + shippingTransactions.get(i).getShippingProvider()
                                        + " " + shippingPrices.get(i).getShippingPrice()
                                        + " " + shippingPrices.get(i).getShippingDiscount());
                            } else {
                                printWriter.println(dateFormat.format(shippingTransactions.get(i).getShippingDate())
                                        + " " + shippingTransactions.get(i).getShippingPackageSize()
                                        + " " + shippingTransactions.get(i).getShippingProvider()
                                        + " " + shippingPrices.get(i).getShippingPrice() + " -");
                            }
                        }
                    }
                }
                printWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}