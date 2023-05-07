package model.product;

import model.Gift;

import java.util.Objects;

/**
 * The class stores information about the Digital Product information
 *
 * @author
 * @since 2023 - 05 - 31
 */

public class Digital extends Product implements Gift {

    // CONSTRUCTORS
    public Digital(String name, String description, int quantity, double price) {
        super(name, description, quantity, price);
    }

    public Digital(String name, String description, int quantity, double price, String taxType) {
        super(name, description, quantity, price, taxType);
    }

    // METHODS
    /**
     * This method is used to store the String representation of the product
     * @return String - This returns the Digital Product's String representation
     */
    @Override
    public String toString() {
        return "DIGITAL - <" + name + ">";
    }

    /**
     * This method is used to store the String data of the product to store in the txt file
     * @return String - This returns the Digital Product's String Data
     */
    @Override
    public String toData() {
        return String.format("{%s,%s,%s,%d,%,.2f,%s}",name,"DIGITAL",description,quantity,price,taxType);
    }

    /**
     * This method is used to provide the full detailed information of the Digital Product
     * @return String - This String store the Digital Product's Information: name, description, quantity available, price, taxType, and gift's message (if this product is a gift)
     */
    @Override
    public String getProductDetail() {
        // Set gift message accordingly if product is a gift
        String giftMessage = "None";
        if (defaultMessage != null) {
            giftMessage = defaultMessage;
        }
        // Provide the String to store detail
        return String.format("{\nProduct Name: %s \nType: %s \nProduct Description: %s \nQuantity Available: %d \nPrice: $ %,.2f \nTaxType: %s \nDefault gift message: %s \n}",name,"DIGITAL",description,quantity,price,taxType,giftMessage);
    }

    /* Gift methods overriding */
    @Override
    public void setMessage(String message) {
        this.defaultMessage = message;
    }

    @Override
    public String getMessage() {
        return Objects.requireNonNullElse(this.defaultMessage, "There is no message on this gift.");
    }
}
