package model;

import database.ShoppingDB;

import java.util.Map;

/**
 * @author Group
 */

public class Tax {
    private static Map<String, Double> taxes;


    public Tax() {
        taxes = ShoppingDB.getInstance().getTaxes();
    }

    /**
     *
     * @param productName : name of the product as a String
     * @return double : the tax percentage in decimal form
     */
    public static double getTaxAmount(String productName) {
        // get tax type from the product name
        String taxType = ShoppingDB.getInstance().getProducts().getProduct(productName).getTaxType();
        // return the tax percentage stored in the Map using the tax type as the key
        return taxes.get(taxType);
    }
}
