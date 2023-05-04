package model;

/**
 * The class store information about the Shopping Cart
 *
 * @author Nguyen Anh Duy - s3878141
 * @since 2023 - 03 - 31
 */

import database.ShoppingDB;

import java.util.*;

public class ShoppingCart implements Comparable<ShoppingCart> {

    // ATTRIBUTES
    protected int cartID;
    protected String appliedCouponID;
    protected Map<String, Integer> cartItems;
    protected double totalWeight;


    //? CONSTRUCTOR
    public ShoppingCart(int cartID){
        this.cartID = cartID;
        cartItems = new HashMap<>();
        totalWeight = 0;
    }

    public ShoppingCart(int cartID, String couponID){
        this.cartID = cartID;
        this.appliedCouponID = couponID;
        cartItems = new HashMap<>();
        totalWeight = 0;
    }

    //? GETTERS & SETTERS
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


    // METHOD

    /* Utilities Method */
    public void resetCart() {cartItems.clear();}

    public int countUniqueItems() {
        return cartItems.keySet().size();
    }
    public int countItems() {
        int itemCount = 0;
        for (int itemQuantity : cartItems.values()) {
            itemCount += itemQuantity;
        }
        return itemCount;
    }

    public boolean containItem(String productName) {
        for (String item : cartItems.keySet()) {
            if (productName.equals(item)) {
                return true;
            }
        }
        System.out.println("Product is not existed in the shopping cart!");
        return false;
    }

    public static boolean checkCartExisted(int cartID, ShoppingCartList carts){
        if (carts.contains(cartID)) {
            System.out.println("This cart is already existed on our system!" +
                    "\nPlease select another ID." +
                    "\n--------------------------------");
            return false;
        }
        return true;
    }


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
        if (!productList.contains(productName)) {
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

        p.setQuantity(p.getQuantity() - 1); // update available quantity for the product
        calTotalWeight(productList); // Calculate the new total weight for the shopping cart
        return true;
    }
    /**
     * The method use to remove item(s) (Product) into the shopping cart
     *
     * Conditions:
     * 1. The product must existed in the Shopping Cart
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
            System.out.println("Your quantity number is larger than the current number of this product in the cart!");
            return false;
        }

        // Get the Product from the product name
        Product p = productList.getProduct(productName);

        // Reduce a number of quantity from the product
        cartItems.put(productName, cartItems.get(productName) - quantity);
        p.setQuantity(p.getQuantity() + quantity); // update available quantity for the product
        calTotalWeight(productList); // Calculate the new total weight for the shopping cart

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
    public double calTotalWeight(ProductMap productList) {
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
        Iterator<String> it = cartItems.keySet().iterator();
        while(it.hasNext()) { // checking the next Product
            String productName = it.next();
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
        double overallSubtotal = 0;
        double discount = 0;
        // for (String productName : cartItems) {
        //     Product p = App.productList.getProduct(productName);
        //     totalPrice += p.getPrice();
        // }

        // Iterate through all the product names in the shopping cart
        for (String productName : cartItems.keySet()) { // checking the next Product
            Product p = productList.getProduct(productName);

            double itemSubtotal = p.getPrice() * cartItems.get(productName);
            double tax = ShoppingDB.getInstance().getTaxes().getTaxAmount(productName);


//            Traverse through the coupon list, looking for the CouponID
            for (Coupon coupon : ShoppingDB.getInstance().getCoupons().getCoupons()) {
                if (coupon.getCouponID().equals(appliedCouponID)) {
//                    The coupon must match the product name for it to work
                    if (coupon.getProductName().equals(productName)) {
//                        Check for the coupon type, price or percent
                        if (Coupon.getType(appliedCouponID).equals("price")) {
                            PriceCoupon priceCoupon = (PriceCoupon) coupon;
//                            Subtract the price for all the matching products
                            discount += priceCoupon.getCouponValue() * cartItems.get(productName);
                        } else if (Coupon.getType(appliedCouponID).equals("percent")) {
                            PercentCoupon percentCoupon = (PercentCoupon) coupon;
//                            Subtract the price after percentage calculation
                            discount += (itemSubtotal*percentCoupon.getCouponValue()/100);
                        }
                    }
                }
            }

            overallSubtotal += itemSubtotal; // for subtotal before coupons and taxes

            totalPrice += itemSubtotal; // price of all the products before coupons and taxes
            totalPrice -= discount; // coupon placeholder for price after coupon
            totalPrice += tax; // price after tax
        }

        calTotalWeight(productList);
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
        String cartInfo = String.format("Cart #%d --- Cart items: ", cartID);
        String cartItemsInfo = "";
        Iterator<String> it = cartItems.keySet().iterator();
        while (it.hasNext()) {
            String nextProduct = it.next();
            cartItemsInfo += nextProduct + ": " + cartItems.get(nextProduct) + ", ";
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
        return Double.compare(totalWeight, c.getTotalWeight());

//        if (totalWeight == ((ShoppingCart2) c).getTotalWeight()) {
//            return 0;
//        } else if (totalWeight > ((ShoppingCart2) c).getTotalWeight()) {
//            return 1;
//        } else return -1;
    }

}

