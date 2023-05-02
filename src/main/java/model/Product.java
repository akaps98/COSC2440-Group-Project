package model;

import java.util.ArrayList;

/**
 * The class stores information about the Product
 *
 * @author
 * @since 2023 - 05 - 01
 */
abstract public class Product {
    // ATTRIBUTES
    protected String name;
    protected String description;
    protected int quantity;
    protected double price;
    protected String taxType;

    // CONSTRUCTORS
    public Product(){}
    public Product(String name, String description, int quantity, double price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, String description, int quantity, double price, String taxType) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.taxType = taxType;
    }

    // GETTERS & SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTaxType() {return taxType;}

    public void setTaxType(String taxType) {this.taxType = taxType;}

    // METHODS
    /**
     *
     * @param inputName
     * @param savedProducts
     * @return
     */
    public static boolean checkNameIsUnique(String inputName, ArrayList<Product> savedProducts) { // validation to check the name is unique
        for(int i = 0; i < savedProducts.size(); i++) {
            if(savedProducts.get(i).getName().equals(inputName)) {
                System.out.println("This product exists on our system." +
                    "\nPlease input another product." +
                    "\n--------------------------------");
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to validate the quantity Available number to set for the product
     * @param quantity: must be a positive number
     * @return boolean: boolean value states if the quantity is valid
     */
    public static boolean checkQuantityIsValid(int quantity) {
        if(!(quantity < 0)) { // validation to check the quantity is valid number (non-negative number)
            System.out.println("You cannot input negative number" +
                               "\nPlease input the valid quantity again." +
                               "\n--------------------------------");
            return false;
        }
        return true;
    }
    /* Abstract method to implement in child class */
    abstract public String toString(); // String representation
    abstract public String toData(); // String data written to products.txt
    abstract public String getProductDetail(); // Product detailed information
}
