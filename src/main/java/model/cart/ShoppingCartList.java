package model.cart;

/**
 * The class stores information about the list which stores all shopping cart information
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ShoppingCartList {
    // ATTRIBUTE
    private final List<ShoppingCart> cartList;

    // CONSTRUCTOR
    public ShoppingCartList() {
        cartList = new ArrayList<>();
    }

    // METHODS

    /**
     * This method add a new cart to the list
     * @param c: added shopping cart
     * @return boolean: states if it is successfully added to the list
     */
    public boolean addCart(ShoppingCart c) {
        if (c != null) {
            cartList.add(c);
            return true;
        }
        return false;
    }

    /**
     * This method remove a cart from the list
     * @param c: removed shopping cart
     * @return boolean: states if it is successfully remove from the list
     */
    public boolean removeCart(ShoppingCart c) { // Unused method
        if (c != null) {
            cartList.remove(c);
            return true;
        }
        return false;
    }

    /**
     * This method check if a cart existed in the list
     * @return boolean: states if the list contains the cart
     */
    public boolean containCart(int cartID) {
        for (ShoppingCart cart : cartList) {
            if (cart.getCartID() == cartID) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method counts the number of carts existed in the list
     * @return int: total number of carts
     */
    public int countCarts() {
        return cartList.size();
    }

    /**
     * The method return the Shopping Cart according to the cartNumber
     *
     * @param cartID: the cart # that is stored inside the Shopping Cart list (not the index of the cart in the list)
     * @return ShoppingCart: the Shopping Cart with associated cartID
     */
    public ShoppingCart getCart(int cartID) {
        for (ShoppingCart cart : cartList) {
            if (cart.getCartID() == cartID) {
                return cart;
            }
        }
        return null;
    }

    /**
     * The method sort all the Shopping Carts in the list according to the weight of the cart
     */
    public static void sortCartsByWeight(ArrayList<ShoppingCart> sortedList) {
        sortedList.sort(new ShoppingCartComparator()); // Using the ShoppingCart Comparator as the indicator for the sorting element
    }

    /**
     * The method displays all the Shopping Carts in the list and also the items they contain
     */
    public void viewCarts() {
        // Loop through all the carts in the list
        Iterator<ShoppingCart> it = cartList.iterator();
        System.out.printf("Number of Shopping Carts: %d%n", countCarts());
        while(it.hasNext()) { // Get the next shopping cart
            ShoppingCart c = it.next();
            System.out.println(c.toString()); // Display the cart detail
        }
    }

    /**
     * The method displays all the Shopping Carts in the list according to the weight of the cart (in ascending order)
     */
    public void viewCartsAfterSorted() {
        ArrayList<ShoppingCart> sortedCartList = new ArrayList<>(); // Create a new list to store the sorted shopping carts
        // Loop through all the carts in the current shopping list (unsorted one)
        Iterator<ShoppingCart> it = this.cartList.iterator();
        while(it.hasNext()) { // Get the next shopping cart
            ShoppingCart c = it.next();
            sortedCartList.add(c); // Add the shopping cart to the sorted cart list
        }

        sortCartsByWeight(sortedCartList); // sort the shopping carts in the new list

        // Now, loop through all the carts in the list that needs to be sorted
        it = sortedCartList.iterator();
        System.out.printf("Number of Shopping Carts: %d%n", countCarts());
        while(it.hasNext()) { // Get the next shopping cart
            ShoppingCart c = it.next();
            System.out.println(c.toString()); // Display the cart detail
        }
    }

}
