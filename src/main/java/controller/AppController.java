package controller;

/**
 * desc
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import database.ShoppingDB;
import model.data.DataInput;

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
                5.  Create a new shopping cart
                6.  Add product(s) to shopping cart
                7.  Remove product(s) from shopping cart
                8.  View shopping cart(s) detail
                9.  Edit shopping cart
                10. Display all shopping cart
                (sorted based on the cart's weight)
                11. Print shopping cart receipt
                12. Exit
                --------------------------------------------------
                Select the option you want:\040""");
    }
}
