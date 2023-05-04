package model.product;

import database.ShoppingDB;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Group
 */

public class Tax {
    private Map<String, Double> taxes;


    public Tax() {
        taxes = new HashMap<>();
    }

    public void addTax(String taxType, double taxValue) {
        taxes.put(taxType, taxValue);
    }

    /**
     *
     * @param productName : name of the product as a String
     * @return double : the tax percentage in decimal form
     */
    public double getTaxAmount(String productName) {
        // get tax type from the product name
        String taxType = ShoppingDB.getInstance().getProducts().getProduct(productName).getTaxType();
        // return the tax percentage stored in the Map using the tax type as the key
        return taxes.get(taxType);
    }
}
