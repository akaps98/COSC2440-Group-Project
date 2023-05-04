package model.product;

import model.Gift;

/**
 * The class stores information about the Product
 *
 * @author
 * @since 2023 - 05 - 01
 */
abstract public class Product implements Gift {
    // ATTRIBUTES
    protected String name;
    protected String description;
    protected int quantity;
    protected double price;
    protected String taxType;
    protected String message;

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
    public static boolean checkProductExisted(String productName, ProductMap products) { // validation to check the name is unique
        if (products.contains(productName)) {
            System.out.println("This product name existed on our system!" +
                    "\nPlease select another name." +
                    "\n--------------------------------");
            return false;
        }
        return true;
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

    /* Gift methods overriding */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        if(this.message == null) {
            return "There is no message on this gift.";
        }
        return this.message;
    }

    /* Abstract method to implement in child class */
    abstract public String toString(); // String representation
    abstract public String toData(); // String data written to products.txt
    abstract public String getProductDetail(); // Product detailed information
}

