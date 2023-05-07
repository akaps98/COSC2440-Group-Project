package model.product;

/**
 * The class stores information about all the Products inside a Product List
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import java.util.Map;
import java.util.TreeMap;

public class ProductMap {
    // ATTRIBUTES
    private final Map<String, Product> productList;

    // CONSTRUCTOR
    public ProductMap() { productList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    // METHODS
    public Product getProduct(String productName) {return productList.get(productName);}

    /* Utilities Methods */
    public int countProduct() {
        return productList.size();
    }

    public void resetProductList() {
        productList.clear();
    }

    public boolean containProduct(String productName) {return productList.containsKey(productName);}

    public void addProduct(Product p) {productList.put(p.getName(), p);}

    public void removeProduct(Product p) {productList.remove(p.getName());}

    /**
     * The method used to provide all the product name inside the product list
     *
     * Action: print the StringBuilder that contains the number of products in the list and the all the product names
     */
    public void viewAllProducts() {
        StringBuilder allProducts = new StringBuilder(); // The String that contains information of all existing products
        allProducts.append("[\n");
        for (Product p : productList.values()) { // Loop for each product inside the list
            allProducts.append(p.toString()).append(": ").append(p.getQuantity()).append("\n");
        }
        allProducts.append("]");
        // Display to the console
        System.out.println("Number of Products: " + countProduct());
        System.out.println(allProducts);
    }

    /**
     * The method used to provide all the product name that are currently available to add to a cart inside the product list
     *
     * Condition: product must have availableQuantity > 0
     *
     * Action: print the number of products in the list and the all the product names
     */
    public void viewAvailableProducts() {
        StringBuilder availableProducts = new StringBuilder(); // The String that contains information of all available products
        int availableCount = 0;
        availableProducts.append("[\n");
        for (Product p : productList.values()) { // Loop for each product inside the list
            if (p.getQuantity() != 0) {
                availableProducts.append(p).append(": ").append(p.getQuantity()).append("\n"); // Add
                availableCount++;
            }
        }
        availableProducts.append("]");
        // Display to the console
        System.out.println("Number of Available Products: " + availableCount);
        System.out.println(availableProducts);
    }
}
