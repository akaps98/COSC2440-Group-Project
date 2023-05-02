package model;

/**
 * The class stores information about all the Products inside a shopping cart
 *
 * @author Nguyen Anh Duy - s3878141
 * @since 2023 - 04 - 01
 */

import java.util.Map;
import java.util.HashMap;

public class ItemMap {
    // ATTRIBUTES
    private Map<String, Integer> itemMap;

    // CONSTRUCTOR
    public ItemMap() {
        itemMap = new HashMap<>();
    }

    // METHODS

    /**
     * This method add the new item with the specific number of quantity to the item map
     * @param productName: name of the added product
     * @param quantity: number of product to add
     * @param productList: the list contains all the existed product
     * @return boolean: this value state whether the item has been added successfully
     */
    public boolean addItem(String productName, int quantity, HashMap<String, Product> productList) {
        // Check if the item map contains the existed product name
        if (!productList.containsKey(productName)) {
            System.out.println("Product not existed!");
            return false;
        }
        // Check if there is enough available quantity for the added product(s)
        if (productList.get(productName).getQuantity() < quantity) {
            System.out.println("Not enough available quantity for this product");
            return false;
        }

        // Add product to the shopping cart
        if (itemMap.containsKey(productName)) { // Check if item already existed in the cart
            itemMap.put(productName, itemMap.get(productName) + quantity);
        } else {
            itemMap.put(productName, quantity);
        }
        return true;
    }

    public boolean removeItem(String productName, int quantity, HashMap<String, Product> productList) {
        // Check if the item map contains the existed product name
        if (!productList.containsKey(productName)) {
            System.out.println("Product not existed!");
            return false;
        }
        // Check if the quantity number to remove is valid
        if (itemMap.get(productName) < quantity) {
            System.out.println("Your quantity number is larger than the current number of this product in the cart!");
            return false;
        }

        // Remove the product entirely if the quantity reduced to 0
        itemMap.put(productName, itemMap.get(productName) - quantity);
        if (itemMap.get(productName) == 0) {
            itemMap.remove(productName);
        }
        return true;

    }

    public int countItems() {
        return itemMap.size();
    }

    public boolean containItem(String productName) {
        return itemMap.containsKey(productName);
    }
}
