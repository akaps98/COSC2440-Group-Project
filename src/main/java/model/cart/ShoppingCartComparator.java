package model.cart;

/**
 * The Comparator class manually created to compare the shopping carts in a shopping cart list (for sorting)
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.cart.ShoppingCart;

import java.util.Comparator;

public class ShoppingCartComparator implements Comparator<ShoppingCart>{
    // public static final int COMPARE_BY_WEIGHT = 1;
    // private int compareBy;

    // CONSTRUCTOR
    public ShoppingCartComparator() {
        // this.compareBy = comparedBy;
    };

    // METHODS
    /**
     * The method is used to compare the 2 shopping carts for sorting
     *
     */
    @Override
    public int compare(ShoppingCart c1, ShoppingCart c2) {
        return Double.compare(c1.getTotalWeight(), c2.getTotalWeight());
    }
}

