package com.asseco.praktika.utils;

import com.asseco.praktika.dto.Transactions;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InputProcessor {

    private Date shippingDates;
    private String shippingPackageSize;
    private String shippingProviders;
    private String invalidShippingData;
    private String invalidDate;
    private static boolean invalidInputFile;
    private final List<Transactions> shippingTransactions = new ArrayList<>();

    public List<Transactions> getShippingTransactions() {
        return shippingTransactions;
    }

    public static boolean isInvalidInputFile() {
        return invalidInputFile;
    }

    public void readValues(String inputFileName) {
        String line, currentYear, leapYear;
        GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (new File(inputFileName).exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName))) {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] column = line.split("\s");
                    currentYear = column[0].substring(0, 4);
                    leapYear = currentYear + ("-02-29");

                    if (!gregorianCalendar.isLeapYear(Integer.parseInt(currentYear)) && column[0].equals(leapYear)) {
                        invalidDate = column[0];
                        if (column.length == 3) {
                            shippingPackageSize = column[1];
                            shippingProviders = column[2];
                            shippingTransactions.add(new Transactions(null, shippingPackageSize, shippingProviders, invalidDate));
                        } else if (column.length == 2) {
                            invalidShippingData = column[1];
                            shippingTransactions.add(new Transactions(null, invalidShippingData, invalidShippingData, invalidDate));
                        }
                    } else {
                        if (column.length == 3) {
                            shippingDates = dateFormat.parse(column[0]);
                            shippingPackageSize = column[1];
                            shippingProviders = column[2];
                            shippingTransactions.add(new Transactions(shippingDates, shippingPackageSize, shippingProviders, null));
                        } else if (column.length == 2) {
                            shippingDates = dateFormat.parse(column[0]);
                            invalidShippingData = column[1];
                            shippingTransactions.add(new Transactions(shippingDates, invalidShippingData, invalidShippingData, null));
                        }
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid input file name");
            invalidInputFile = true;
        }
    }
}