package model;

/**
 * The class stores information about all the shopping carts
 *
 * @author Nguyen Anh Duy - s3878141
 * @since 2023 - 03 - 31
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ShoppingCartList {
    // ATTRIBUTE
    private List<ShoppingCart2> cartList;

    // CONSTRUCTOR
    public ShoppingCartList() {
        cartList = new ArrayList<>();
    }

    // METHODS
    public boolean addCart(ShoppingCart2 c) {
        if (c != null) {
            cartList.add(c);
            return true;
        }
        return false;
    }

    public boolean removeCart(ShoppingCart2 c) { // Unused method
        if (c != null) {
            cartList.remove(c);
            return true;
        }
        return false;
    }

    public int indexOf(ShoppingCart2 c) {
        return cartList.indexOf(c);
    }

    public int countCarts() {
        return cartList.size();
    }

    /**
     * The method return the Shopping Cart according to the cartNumber
     *
     * @param cartNumber: the cart # that is stored inside the Shopping Cart list (not the index of the cart in the list)
     * @return ShoppingCart: the Shopping Cart instance at the cartNumber - 1 position in the list
     */
    public ShoppingCart2 getCart(int cartID) {
        for (ShoppingCart2 cart : cartList) {
            if (cart.getCartID() == cartID) {
                return cart;
            }
        }
        return null;
    }

    /**
     * The method sort all the Shopping Carts in the list according to the weight of the cart
     */
    private void sortCartsByWeight(ArrayList<ShoppingCart2> sortedList) {
        sortedList.sort(new ShoppingCartComparator()); // Using the ShoppingCart Comparator as the indicator for the sorting element
    }

    /**
     * The method displays all the Shopping Carts in the list and also the items they contain
     */
    public void viewCarts() {
        // Loop through all the carts in the list
        Iterator<ShoppingCart2> it = cartList.iterator();
        System.out.println(String.format("Number of Shopping Carts: %d", countCarts()));
        while(it.hasNext()) { // Get the next shopping cart
            ShoppingCart2 c = it.next();
            System.out.println(c.toString()); // Display the cart detail
        }
    }

    /**
     * The method displays all the Shopping Carts in the list according to the weight of the cart (in ascending order)
     */
    public void viewCartsAfterSorted() {
        ArrayList<ShoppingCart2> sortedCartList = new ArrayList<>(); // Create a new list to store the sorted shopping carts
        // Loop through all the carts in the current shopping list (unsorted one)
        Iterator<ShoppingCart2> it = this.cartList.iterator();
        while(it.hasNext()) { // Get the next shopping cart
            ShoppingCart2 c = it.next();
            sortedCartList.add(c); // Add the shopping cart to the sorted cart list
        }

        sortCartsByWeight(sortedCartList); // sort the shopping carts in the new list

        // Now, loop through all the carts in the list that needs to be sorted
        it = sortedCartList.iterator();
        System.out.println(String.format("Number of Shopping Carts: %d", countCarts()));
        while(it.hasNext()) { // Get the next shopping cart
            ShoppingCart2 c = it.next();
            System.out.println(c.toString()); // Display the cart detail
        }
    }

}