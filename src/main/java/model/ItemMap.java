package model;

/**
 * The class stores information about all the Products inside a Product List
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
    public ItemMap(Product p) {
        itemMap = new HashMap<>();

    }

    // METHODS
    public boolean addProduct(Product p) {
        if (productList.containsValue(p)) { // Check if the product list contains the existed product name
            return false;
        }
        productList.put(p.getName(), p);
        return true;
    }

    public boolean removeProduct(Product p) {
        if (productList.containsValue(p)) { // Check if the product list contains the existed product name
            productList.remove(p.getName());
            return true;
        }
        return false;

    }

    public int countProduct() {
        return productList.size();
    }

    public void resetProductList() {
        productList.clear();
    }

    public Product getProduct(String productName) {
        return productList.get(productName);
    }

    public boolean containProduct(String productName) {
        return productList.containsKey(productName);
    }

    /**
     * The method used to provide all the product name inside the product list
     *
     * @return StringBuilder: the StringBuilder contains the number of products in the list and the all the product names
     */
    public StringBuilder viewAllProducts() {
        StringBuilder allProducts = new StringBuilder(); // The String that contains information of all existing products
        allProducts.append("[");
        for (Product p : productList.values()) { // Loop for each product inside the list
            allProducts.append(p.toString() + ", ");
        }
        allProducts.append("]");
        // Display to the console
        System.out.println(String.format("Number of Products: %d", countProduct()));
        System.out.println(allProducts);
        return allProducts;
    }

    /**
     * The method used to provide all the product name that are currently available to add to a cart inside the product list
     *
     * Condition: product must have availableQuantity > 0
     *
     * @return StringBuilder: the StringBuilder contains the number of products in the list and the all the product names
     */
    public StringBuilder viewAvailableProducts() {
        StringBuilder availableProducts = new StringBuilder(); // The String that contains information of all available products
        int availableCount = 0;
        for (Product p : productList.values()) { // Loop for each product inside the list
            if (p.getQuantity() != 0) {
                availableProducts.append(p.toString() + ", "); // Add
                availableCount++;
            }
        }
        availableProducts.append("]");
        // Display to the console
        System.out.println(String.format("Number of Available Products: %d", availableCount));
        System.out.println(availableProducts);
        return availableProducts;
    }
}
