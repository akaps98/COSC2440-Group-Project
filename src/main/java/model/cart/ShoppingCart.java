package model.cart;

/**
 * The class store information about the Shopping Cart
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import database.ShoppingDB;
import model.product.Product;
import model.product.ProductMap;
import model.product.Physical;
import model.coupon.Coupon;
import model.coupon.PriceCoupon;
import model.coupon.PercentCoupon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ShoppingCart implements Comparable<ShoppingCart> {

    // ATTRIBUTES
    protected int cartID;
    protected String appliedCouponID;
    protected Map<String, Integer> cartItems;
    protected Map<String, String> cartGiftMessages;
    protected double totalWeight;


    // CONSTRUCTORS
    public ShoppingCart(int cartID){
        this.cartID = cartID;
        cartItems = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        cartGiftMessages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        totalWeight = 0;
    }

    public ShoppingCart(int cartID, String couponID){
        this.cartID = cartID;
        this.appliedCouponID = couponID;
        cartItems = new HashMap<>();
        totalWeight = 0;
    }

    // GETTERS & SETTERS
    public int getCartID() {return cartID;}

    public void setCartID(int cartID) {this.cartID = cartID;}

    public Map<String, Integer> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<String, Integer> cartItems) {
        this.cartItems = cartItems;
    }

    public String getAppliedCouponID() {return this.appliedCouponID;}
    public void setAppliedCouponID(String appliedCouponID) {this.appliedCouponID = appliedCouponID;}

    public double getTotalWeight() {return this.totalWeight;}
    public void setTotalWeight(double totalWeight) {this.totalWeight = totalWeight;}

    // This setter put the gift message to the associated product in the list
    public boolean setMessage(String productName, String msg) {
        if (cartItems.containsKey(productName)) {
            cartGiftMessages.put(productName, msg);
            return true;
        }
        System.out.println("Product does not exist in the shopping cart and cannot set gift message");
        return false;
    }

    public String getMessage(String productName) {
        return cartGiftMessages.get(productName);
    }

    // METHOD

    /* Utilities Method */
    /**
     * This method use count the unique item names in the shopping cart
     * @return int: the number of keys in the cartItems map - number of different product name
     */
    public int countUniqueItems() {
        return cartItems.keySet().size();
    }

    /**
     * This method use count the total number of items in the shopping cart
     * @return int: the total values of all different keys in the cartItems map
     */
    public int countItems() {
        int itemCount = 0;
        for (int itemQuantity : cartItems.values()) {
            itemCount += itemQuantity;
        }
        return itemCount;
    }

    /**
     * This method use to check if the item is existed in the shopping cart
     * @return boolean: this value states whether the cart contain the product or not
     */
    public boolean containItem(String productName) {
        for (String item : cartItems.keySet()) {
            if (item.equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method use to check if the item in the shopping cart contains a gift message
     * @return boolean: this value states whether the item has a gift message or not
     */
    public boolean containGiftMessage(String productName) {
        if (cartGiftMessages.containsKey(productName)) {
            if (cartGiftMessages.get(productName) != null) {
                return true;
            }
            System.out.println("Product does not contain any gift message in this cart!");
        }
        return false;
    }

    /**
     * This method use to set a new gift message for an existed product in the shopping cart
     *
     */
    public void setItemGiftMessage(String productName, String msg) {
        cartGiftMessages.put(productName, msg);
    }


    /**
     * This method use to reset all items in the shopping cart
     */
    public void resetCart() {cartItems.clear();}

    /**
     * This method use to check if the shopping cart details has been exported to a receipt in the database
     * @return boolean: check if the receipt of this cart existed
     */
    public boolean existReceipt() {
        Path path = Paths.get("src/main/java/database/receipts/cart" + cartID + ".txt");
        return Files.exists(path);
    }

    /* Main methods */
    /**
     * This method add the new item with the specific number of quantity to the item map
     *
     * Conditions:
     * 1. The product is existed
     * 2. There is available quantity (> 0) for that Product in the Product List
     *
     * @param productName: name of the added product
     * @param quantity: number of product to add
     * @param productList: the list contains all the existed product
     * @return boolean: Boolean value states if the product item has been added successfully to the cart
     *
     * Action: update the quantity available for that product: quantityAvailable - 1
     */
    public boolean addItem(String productName, int quantity, ProductMap productList) {
        // Check if the item map contains the existed product name
        if (!productList.containProduct(productName)) {
            System.out.println("Product not existed!");
            return false;
        }

        // Check if there is enough available quantity for the added product(s)
        Product p = productList.getProduct(productName);
        if (p.getQuantity() < quantity) {
            System.out.println("Not enough available quantity for this product");
            return false;
        }

        // Add product to the shopping cart
        if (cartItems.containsKey(productName)) { // Check if item already existed in the cart
            cartItems.put(productName, cartItems.get(productName) + quantity);
        } else {
            cartItems.put(productName, quantity);
        }

        p.setQuantity(p.getQuantity() - quantity); // update available quantity for the product
        getTotalWeight(productList); // Calculate the new total weight for the shopping cart
        return true;
    }
    /**
     * The method use to remove item(s) (Product) into the shopping cart
     *
     * Conditions:
     * 1. The product must exist in the Shopping Cart
     * 2. The quantity to remove must <= the current product quantity in the cart
     *
     * @param productName - Name of the product
     * @param quantity: number of product to remove
     * @param productList: the list contains all the existed product
     * @return boolean - Boolean value states if the product has been added successfully to the cart
     * Action: update the quantity available for that product: quantityAvailable + 1
     */
    public boolean removeItem(String productName, int quantity, ProductMap productList) {
        // Check if the product is not existed in the shopping cart
        if (!cartItems.containsKey(productName)) {
            System.out.println("Product not existed!");
            return false;
        }

        // Check if the quantity number to remove is valid
        if (cartItems.get(productName) < quantity) {
            System.out.println("""
            Your quantity number is larger than the current number of this product in the cart.
            Please try again!""");
            return false;
        }

        // Get the Product from the product name
        Product p = productList.getProduct(productName);

        // Reduce a number of quantity from the product
        cartItems.put(productName, cartItems.get(productName) - quantity);
        p.setQuantity(p.getQuantity() + quantity); // update available quantity for the product
        getTotalWeight(productList); // Calculate the new total weight for the shopping cart

        // Remove the product entirely if the quantity reduced to 0
        if (cartItems.get(productName) == 0) {
            cartItems.remove(productName);
        }
        return true;
    }

    /**
     * The method use to calculate the total weight of the shopping cart
     * Note:
     * 1/ public method and only used as a part the cartAmount() method
     * 2/ The product list is a dependency injection from the App class
     *
     * @return double - the double value is the total weight of the Physical Products in the cart
     * Action: set the total weight of
     */
    public double getTotalWeight(ProductMap productList) {
        // Check if shopping cart is empty
        if (cartItems.size() == 0) {
            return 0;
        }
        double weight = 0;
        // Iterate through all the product names in the cart items
        for (String productName : cartItems.keySet()) { // checking the next Product
            Product p = productList.getProduct(productName);
            if (p instanceof Physical) { // check if the product is a Physical Product
                weight += ((Physical) p).getWeight() * cartItems.get(productName); // get the weight by casting PhysicalProduct type, and add to the weight variable
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
    public double cartAmount(ProductMap productList) {
        double totalPrice = 0;

        // Iterate through all the product names in the shopping cart
        for (String productName : cartItems.keySet()) { // checking the next Product
//            Get the subtotal for all quantities of this product, its tax and discount (if any)
            double itemSubtotal = getItemSubtotal(productName, productList);
            double tax = getItemTax(productName, productList);
            double discount = getItemDiscount(productName, productList);

//            Formula for total price is item subtotal - discount + tax
            totalPrice += itemSubtotal; // price of all the products before coupons and taxes
            totalPrice -= discount; // price after subtracting the discount
            totalPrice += tax; // price after adding tax
        }

//        Shipping fee is added to the total price
        totalPrice += getShippingFee(productList); // add the shipping fee to the total price

        return totalPrice;
    }

    /**
     * Calculate the shipping fee of this cart
     * @param productList List of all products
     * @return double: shipping fee
     */
    public double getShippingFee(ProductMap productList) {
        getTotalWeight(productList);
        return totalWeight * 0.1;
    }

    /**
     * This method is used for calculating the item subtotal using the formula:
     * price of product * quantity of product.
     * The price is taken from the list of products, using the productName as a parameter.
     * The quantity is taken from a Map which stores the productName as a key and quantity as the value.
     * @param productName Name of product to be calculated
     * @param productList List of all products
     * @return Double: The subtotal for that product with quantity accounted for
     */
    public Double getItemSubtotal(String productName, ProductMap productList) {
        return productList.getProduct(productName).getPrice() * cartItems.get(productName);
    }

    /**
     * This method is used for calculating the tax amount for an item using the formula:
     * taxPercentage * itemSubtotal.
     * The reason for itemSubtotal is so that the tax is calculated for all quantities of this product
     * @param productName Name of product to be calculated
     * @param productList List of all products
     * @return Double: The tax amount for all quantity of an item
     */
    public Double getItemTax(String productName, ProductMap productList) {
//        Get tax percentage of the product then multiplying it to the item subtotal to get tax for all number of products
        return ShoppingDB.getInstance().getTaxes().getTaxPercentage(productName) * getItemSubtotal(productName, productList);
    }

    /**
     * Used for calculating the discount from the coupon applied using the formula:
     * For price coupon: couponValue * quantity.
     * This is so that a coupon can be applied to all quantities of this product.
     * For percentage coupon: couponValue/100 * itemSubtotal.
     * couponValue is divided by 100 because it is an integer that represents a percentage.
     * itemSubtotal has to be used so that the percentage is applied to all quantities of the product.
     * @param productName Name of product to be calculated
     * @param productList List of all products
     * @return Double: The discount amount for a product
     */
    public Double getItemDiscount(String productName, ProductMap productList) {
        double discount = 0;

//        Traverse through the coupon list, looking for the CouponID
        for (Coupon coupon : ShoppingDB.getInstance().getCoupons().getCoupons()) {
//            Check if the 2 couponIDs match
            if (coupon.getCouponID().equals(appliedCouponID)) {
//                    The coupon is only valid for 1 product, so it must match the product name for it to work
                if (coupon.getProductName().equals(productName)) {
//                        Check for the coupon type, price or percent
                    if (Coupon.getType(appliedCouponID).equals("price")) {
                        PriceCoupon priceCoupon = (PriceCoupon) coupon;
//                            Add the discount for all the matching products
                        discount = priceCoupon.getCouponValue() * cartItems.get(productName);
//                        A break is used to stop the loop after the coupon has been matched with the product
                        break;
                    } else if (Coupon.getType(appliedCouponID).equals("percent")) {
                        PercentCoupon percentCoupon = (PercentCoupon) coupon;
//                            Add the discount after percentage calculation
                        discount = (getItemSubtotal(productName, productList)*percentCoupon.getCouponValue()/100);
                        break;
                    }
                }
            }
        }

        return discount;
    }

    /**
     * This method display all the detailed information about the Shopping Cart
     * Included: gift messages, appliedCouponID, cartWeight, cartAmount
     */
    public void viewCartInfo() {
        StringBuilder cartInfo = new StringBuilder();
        cartInfo.append(this);
        this.viewCartGiftMessages();
        if (appliedCouponID != null) {
            cartInfo.append("\nCart applied coupon: ").append(appliedCouponID);
        } else {
            cartInfo.append("\nCart applied coupon: none");
        }
        cartInfo.append("\nCart weight: ").append(totalWeight).append("g");
        cartInfo.append(String.format("\nCart amount: $%.2f", cartAmount(ShoppingDB.getInstance().getProducts())));
        System.out.println(cartInfo);
    }

    /**
     * This method display all gift messages contained in the shopping cart
     */
    public void viewCartGiftMessages() {
        StringBuilder cartItemGiftMessages = new StringBuilder();
        cartItemGiftMessages.append("Cart gift messages: \n[");
        for (String nextProduct : cartGiftMessages.keySet()) {
            cartItemGiftMessages.append(nextProduct).append(": ").append(cartGiftMessages.get(nextProduct)).append(", ");
        }
        cartItemGiftMessages.append("]");
        // Display to the console
        System.out.println("Number of unique items contain gift message: ");
        System.out.println(cartItemGiftMessages);
    }

    /**
     * The method use to print out the cart information: Cart #number, and all the cart items (Product) info
     *
     * @return String - the String contains the cart information
     */
    @Override
    public String toString() {
        String cartInfo = String.format("Cart #%d --- Cart items: ", cartID);
        StringBuilder cartItemsInfo = new StringBuilder();
        for (String nextProduct : cartItems.keySet()) {
            cartItemsInfo.append(nextProduct).append(": ").append(cartItems.get(nextProduct)).append(", ");
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
    public int compareTo(ShoppingCart c) {
        // sorted based on weights
        return Double.compare(totalWeight, c.getTotalWeight());
    }
}

