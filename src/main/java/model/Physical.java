package model;
/**
 * The class stores information about the Physical Product information
 *
 * @author
 * @since 2023 - 05 - 01
 */

public class Physical extends Product implements Gift {
    // ATTRIBUTES
    private double weight;
    private String message;

    // CONSTRUCTORS
    public Physical(String name, String description, int quantity, double price, double weight) {
        super(name, description, quantity, price);
        this.weight = weight;
    }

    public Physical(String name, String description, int quantity, double price, String taxType, double weight) {
        super(name, description, quantity, price, taxType);
        this.weight = weight;
    }

    // GETTERS AND SETTERS
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // METHODS
    /**
     * This method is used to store the String representation of the product
     * @return String - This returns the Physical Product's String representation
     */
    @Override
    public String toString() {
        return "PHYSICAL - <" + name + ">";
    }

    /**
     * This method is used to store the String data of the product to store in the txt file
     * @return String - This returns the Physical Product's String Data
     */
    @Override
    public String toData() {
        return String.format("{%s,%s,%s,%d,%,.2f,%s,%,.2f}",name,"PHYSICAL",description,quantity,price,taxType,weight);
    }

    /**
     * This method is used to provide the full detailed information of the Physical Product
     * @return String - This String store the Physical Product's Information: name, description, quantity available, price, taxType, weight, and gift's message (if this product is a gift)
     */
    @Override
    public String getProductDetail() {
        // Set gift message accordingly if product is a gift
        String giftMessage = "None";
        if (message != null) {
            giftMessage = message;
        }
        // Provide the String to store detail
        return String.format("{\nProduct Name: %s \nType: %s \nProduct Description: %s \nQuantity Available: %d \nPrice: $ %,.2f \nTaxType: %s \nWeight: %,.2f g \nGift message: %s \n}",name,"PHYSICAL",description,quantity,price,taxType,weight,giftMessage);
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
}
