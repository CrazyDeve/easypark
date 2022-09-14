package com.benben.carmanager.utils;

public class DataConvertUtils {

    public static double getPrice(String priceString){
        double price = 0.00;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static int getInt(String numString) {

        try {
            int num = Integer.parseInt(numString);
            return num;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
