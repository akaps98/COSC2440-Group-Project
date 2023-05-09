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
        // If the cart is empty or only contains digital products --> compared by number of items
        if (c1.totalWeight == 0 && c2.totalWeight == 0) {
            return Double.compare(c1.countItems(), c2.countItems());
        }
        // Compared by weights
        return Double.compare(c1.getTotalWeight(), c2.getTotalWeight());
    }
}

