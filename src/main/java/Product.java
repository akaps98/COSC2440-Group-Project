import java.util.ArrayList;

/**
 * @author <Kang Junsik - s3916884>
 */

abstract public class Product {
    private String name;
    private String description;
    private int quantity;
    private double price;

    public Product(String name, String description, int quantity, double price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    abstract public String printOut(); // String representation

    static boolean checkNameIsUnique(String inputName, ArrayList<Product> savedProducts) { // validation to check the name is unique
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

    static boolean checkQuantityIsValid(int quantity) { // validation to check the quantity is valid number (non-negative number)
        if(quantity < 0) {
            System.out.println("You cannot input negative number." +
                               "\nPlease input the valid quantity again." +
                               "\n--------------------------------");
            return false;
        }
        return true;
    }

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
}
