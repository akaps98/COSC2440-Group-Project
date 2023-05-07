package model.cart;

/**
 * The class stores information about all the shopping carts
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.cart.ShoppingCart;
import model.cart.ShoppingCartComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ShoppingCartList {
    // ATTRIBUTE
    private List<ShoppingCart> cartList;

    // CONSTRUCTOR
    public ShoppingCartList() {
        cartList = new ArrayList<>();
    }

    // METHODS
    public boolean addCart(ShoppingCart c) {
        if (c != null) {
            cartList.add(c);
            return true;
        }
        return false;
    }

    public boolean removeCart(ShoppingCart c) { // Unused method
        if (c != null) {
            cartList.remove(c);
            return true;
        }
        return false;
    }

    public boolean containCart(int cartID) {
        for (ShoppingCart cart : cartList) {
            if (cart.getCartID() == cartID) {
                return true;
            }
        }
        return false;
    }


    public int indexOf(ShoppingCart c) {
        return cartList.indexOf(c);
    }

    public int countCarts() {
        return cartList.size();
    }

    /**
     * The method return the Shopping Cart according to the cartNumber
     *
     * @param cartID: the cart # that is stored inside the Shopping Cart list (not the index of the cart in the list)
     * @return ShoppingCart: the Shopping Cart instance at the cartNumber - 1 position in the list
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
        System.out.println(String.format("Number of Shopping Carts: %d", countCarts()));
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
        System.out.println(String.format("Number of Shopping Carts: %d", countCarts()));
        while(it.hasNext()) { // Get the next shopping cart
            ShoppingCart c = it.next();
            System.out.println(c.toString()); // Display the cart detail
        }
    }

}
