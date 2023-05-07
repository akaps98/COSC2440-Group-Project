package model.product;

import database.ShoppingDB;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Group
 */

public class Tax {
    private final Map<String, Double> taxes;

    public Tax() {
        taxes = new HashMap<>();
    }

    public void addTax(String taxType, double taxValue) {
        taxes.put(taxType, taxValue);
    }

    /**
     * Method used to get the tax percentage
     * The tax type of the product is taken from the database.
     * The tax type taken is used for the Map to match the tax type with the tax percentage
     * @param productName Name of the product
     * @return Double: the tax percentage in decimal form
     */
    public double getTaxPercentage(String productName) {
        // get tax type from the product name
        String taxType = ShoppingDB.getInstance().getProducts().getProduct(productName).getTaxType();
        // return the tax percentage stored in the Map using the tax type as the key
        return taxes.get(taxType);
    }
}
