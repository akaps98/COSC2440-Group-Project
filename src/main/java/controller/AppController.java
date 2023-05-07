package controller;

import database.ShoppingDB;
import model.cart.ShoppingCartList;
import model.coupon.CouponList;
import model.data.DataInput;
import model.product.ProductMap;
import model.product.Tax;

import java.util.Scanner;

public abstract class AppController {
    // ATTRIBUTES
    protected ShoppingDB db; // System Database
    protected Scanner input; // Getting user input

    // CONSTRUCTOR
    public AppController() {
        // Retrieve data from the database
        db = ShoppingDB.getInstance();

        // Get scanner from DataInput Singleton class
        input = DataInput.getInstance().getScanner();
    }

    // METHODS
    /**
     * This method displays the console application primary menu (menu for user to select the feature)
     */
    public static void displayMenu() {
        System.out.print("""
                --------------------------------------------------
                         *** ONLINE SHOPPING SERVICE  ***
                --------------------------------------------------
                1.  View product(s) detail
                2.  Create new product
                3.  Edit product
                4.  View all coupons
                5.  Apply a coupon to product
                6.  Create a new shopping cart
                7.  Add product(s) to shopping cart
                8.  Remove product(s) from shopping cart
                9.  View shopping cart(s) detail
                10. Edit shopping cart
                11. Display all shopping cart
                (sorted based on the cart's weight)
                12. Print shopping cart receipt
                13. Exit
                --------------------------------------------------
                Select the option you want:\040""");
    }
}
