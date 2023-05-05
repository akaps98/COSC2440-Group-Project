package model.product;

import model.Gift;

/**
 * The class stores information about the Product
 *
 * @author
 * @since 2023 - 05 - 01
 */
public abstract class Product {
    // ATTRIBUTES
    protected String name;
    protected String description;
    protected int quantity;
    protected double price;
    protected String taxType;
    protected String defaultMessage;

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
     * This method check if the product is existed or not
     *
     * @param productName: name of the product
     * @param products: list that contains all the products
     * @return
     */
    public static boolean checkProductAlreadyExisted(String productName, ProductMap products) { // validation to check the name is unique
        if (products.contains(productName)) {
            System.out.println("This product name is already existed on our system!" +
                    "\nPlease select another name." +
                    "\n--------------------------------");
            return true;
        }
        return false;
    }

    public static boolean checkProductNotExisted(String productName, ProductMap products) { // validation to check the name is unique
        if (!products.contains(productName)) {
            System.out.println("This product name is not existed on our system!" +
                    "\nPlease select another name." +
                    "\n--------------------------------");
            return true;
        }
        return false;
    }

    /**
     * This method is used to validate the quantity Available number to set for the product
     * @param quantity: must be a positive number
     * @return boolean: boolean value states if the quantity is valid
     */
    public static boolean checkQuantityIsValid(int quantity) {
        if(quantity < 0) { // validation to check the quantity is valid number (non-negative number)
            System.out.println("You cannot input negative number" +
                               "\nPlease input the valid quantity again." +
                               "\n--------------------------------");
            return false;
        }
        return true;
    }

    public static boolean checkQuantityIsEnough(int inputQuantity, String productName, ProductMap products) {
        Product p = products.getProduct(productName);
        if (inputQuantity > p.quantity) { // validation to check if the input quality exceeds the available quantity
            System.out.println("The quantity you want exceeds our stock for this product!" +
                    "\nPlease enter another quantity." +
                    "\n--------------------------------");
            return false;
        }
        return true;
    }

    /* Abstract method to implement in child class */
    public abstract String toString(); // String representation
    public abstract  String toData(); // String data written to products.txt
    public abstract String getProductDetail(); // Product detailed information

    public String getDefaultMessage() {return defaultMessage;}
}

