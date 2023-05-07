package model.product;

/**
 * The class stores information about all the Products inside a Product List
 *
 * @author Nguyen Anh Duy - s3878141
 * @since 2023 - 04 - 01
 */

import model.product.Product;

import java.util.Map;
import java.util.HashMap;

public class ProductMap {
    // ATTRIBUTES
    private Map<String, Product> productList;

    // CONSTRUCTOR
    public ProductMap() { productList = new HashMap<>();
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

    public void addProduct(Product p) {
        productList.put(p.getName(), p);
    }

    public void removeProduct(Product p) {
        productList.remove(p.getName());
    }

    /**
     * The method used to provide all the product name inside the product list
     *
     * @return StringBuilder: the StringBuilder contains the number of products in the list and the all the product names
     */
    public StringBuilder viewAllProducts() {
        StringBuilder allProducts = new StringBuilder(); // The String that contains information of all existing products
        allProducts.append("[\n");
        for (Product p : productList.values()) { // Loop for each product inside the list
            allProducts.append(p.toString() + ": " + p.getQuantity() + "\n");
        }
        allProducts.append("]");
        // Display to the console
        System.out.println("Number of Products: " + countProduct());
        System.out.println(allProducts);
        return allProducts;
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
                availableProducts.append(p.toString() + ": " + p.getQuantity() + "\n"); // Add
                availableCount++;
            }
        }
        availableProducts.append("]");
        // Display to the console
        System.out.println("Number of Available Products: " + availableCount);
        System.out.println(availableProducts);
    }
}
