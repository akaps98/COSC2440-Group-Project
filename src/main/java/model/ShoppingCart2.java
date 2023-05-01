package model;

/**
 * The class store information about the Shopping Cart
 *
 * @author Nguyen Anh Duy - s3878141
 * @since 2023 - 03 - 31
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class ShoppingCart2 implements Comparable<ShoppingCart2> {

    //? ATTRIBUTES
    protected Set<String> cartItems;
    protected double totalWeight;
    protected String appliedCouponID;

    //? CONSTRUCTOR
    public ShoppingCart2(){
        cartItems = new HashSet<>();
        totalWeight = 0;
    };

    //? GETTERS & SETTERS
    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getAppliedCouponID() {
        return this.appliedCouponID;
    }

    public void setAppliedCouponID(String appliedCouponID) {
        this.appliedCouponID = appliedCouponID;
    }


    // METHOD

    /**
     * The method use to add a item (Product) into the shopping cart
     *
     * Conditions:
     * 1. The product has not been added to the Shopping Cart yet
     * 2. The product with the productName existed in the Product List
     * 3. There is available quantity (> 0) for that Product in the Product List
     *
     * @param productName - Name of the product
     * @return boolean - Boolean value states if the product has been added successfully to the cart
     * Action: update the quantity available for that product: quantityAvailable - 1
     */
    public boolean addItem(String productName) {
        // Check if the product is existed in the shopping cart
        if (cartItems.contains(productName)) {
            return false;
        }
        // Get the Product from the product name
        Product p = App.productList.getProduct(productName);
        // Check if a product existed, and if there is available quantity for that product
        if (p != null && p.getQuantity() > 0) {
            cartItems.add(productName);
            p.setQuantity(p.getQuantity() - 1); // update available quantity for the product
            calTotalWeight(); // Calculate the new total weight for the shopping cart
            return true;
        }
        return false;
    }

    /**
     * The method use to remove a item (Product) into the shopping cart
     *
     * Conditions:
     * 1. The product must existed in the Shopping Cart
     *
     * @param productName - Name of the product
     * @return boolean - Boolean value states if the product has been added successfully to the cart
     * Action: update the quantity available for that product: quantityAvailable + 1
     */
    public boolean removeItem(String productName) {
        // Check if the product is not existed in the shopping cart
        if (!(cartItems.contains(productName))) {
            return false;
        }
        // Get the Product from the product name
        Product p = App.productList.getProduct(productName);
        if (p != null) {
            cartItems.remove(productName);
            p.setQuantity(p.getQuantity() + 1); // update available quantity for the product
            calTotalWeight(); // Calculate the new total weight for the shopping cart
            return true;
        }
        return false;
    }

    /**
     * The method use to calculate the total weight of the shopping cart
     * Note:
     * 1/ private method and only used as a part the cartAmount() method
     * 2/ The product list is a dependency injection from the App class
     *
     * @return double - the double value is the total weight of the Physical Products in the cart
     * Action: set the total weight of
     */
    private double calTotalWeight() {
        // Check if shopping cart is empty
        if (cartItems.size() == 0) {
            return 0;
        }
        double weight = 0;
        // for (String productName : cartItems) {
        //     Product p = App.productList.getProduct(productName);
        //     if (p instanceof PhysicalProduct) {
        //         weight += ((PhysicalProduct) p).getWeight();
        //     }
        // }

        // Iterate through all the product names in the cart items
        Iterator<String> it = cartItems.iterator();
        while(it.hasNext()) { // checking the next Product
            String productName = it.next();
            Product p = App.productList.getProduct(productName);
            if (p instanceof Physical) { // check if the product is a Physical Product
                weight += ((Physical) p).getWeight(); // get the weight by casting PhysicalProduct type, and add to the weight variable
            }
        }
        setTotalWeight(weight);
        return weight;
    }

    /**
     * The method use to calculate the total price of the shopping cart:
     * Total price = price of all products + shippingFee, where shipping Fee = total weight * 0.1
     * Note:
     * 1/ The product list is a dependency injection from the App class
     *
     * @return double - the double value is the total price of the shopping cart
     * and update the quantity available for that product: quantityAvailable + 1
     */
    public double cartAmount() {
        double totalPrice = 0;
        // for (String productName : cartItems) {
        //     Product p = App.productList.getProduct(productName);
        //     totalPrice += p.getPrice();
        // }

        // Iterate through all the product names in the shopping cart
        Iterator<String> it = cartItems.iterator();
        while(it.hasNext()) { // checking the next Product
            String productName = it.next();
            Product p = App.productList.getProduct(productName);
            totalPrice += p.getPrice(); // adding up the price
        }

        double shippingFee = totalWeight * 0.1;
        totalPrice += shippingFee; // add the shipping fee to the total price

        return totalPrice;
    }

    /**
     * The method use to print out the cart information: Cart #number, and all the cart items (Product) info
     *
     * @return String - the String contains the cart information
     */
    @Override
    public String toString() {
        String cartInfo = String.format("Cart #%d --- Cart items: ", App.cartList.indexOf(this) + 1);
        String cartItemsInfo = "";
        Iterator<String> it = cartItems.iterator();
        while (it.hasNext()) {
            cartItemsInfo += it.next() + ", ";
        }
        return cartInfo + cartItemsInfo;
    }

    /**
     * The method use to compare one cart to another cart (the method is overriding the method compareTo() from the Comparable interface)
     *
     * Condition:
     * 1/ In the function, the shopping cart is compared by the cart's weight
     *
     * @return int - the int value state the comparison results of the 2 cart
     * For example: cart1.compareTo(ShoppingCart cart2)
     * 0: cart1 == cart2
     * 1: cart1 > cart2
     * 2: cart1 < cart2
     */
    @Override
    public int compareTo(ShoppingCart2 c) {
        if (totalWeight == ((ShoppingCart2) c).getTotalWeight()) {
            return 0;
        } else if (totalWeight > ((ShoppingCart2) c).getTotalWeight()) {
            return 1;
        } else return -1;
    }

}

