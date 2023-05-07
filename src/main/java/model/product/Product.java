package model.product;

/**
 * The class stores information about the Product
 *
 * @author Group 9
 * @since 2023 - 05 - 07
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

    /* Abstract method to implement in child class */
    public abstract String toString(); // String representation
    public abstract  String toData(); // String data written to products.txt
    public abstract String getProductDetail(); // Product detailed information

    public String getDefaultMessage() {return defaultMessage;}
}

