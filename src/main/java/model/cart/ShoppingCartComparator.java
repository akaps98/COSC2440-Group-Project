package model.cart;
/**
 * The Comparator class manually created to compare the shopping carts in a shopping cart list (for sorting)
 *
 * @author Nguyen Anh Duy - s3878141
 * @since 2023 - 03 - 31
 */

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
        return c1.compareTo(c2);
    }
}

