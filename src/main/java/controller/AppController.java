package controller;

/**
 * This class is a container for all the other controllers to retrieve the attributes from the database and execute their functions
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

    /**
     * This method displays the console application instructions and greeting message
     */
    public static void displayInstructions() {
        System.out.print("""
                *****************************************************************************
                Welcome to the online shopping service application!
                Here are some guidelines for using our service:
               
                1. Our system already contains 50 products, 50 coupons, 3 Tax values
                and 10 shopping carts for your convenient
                2. You can search a product name with case-insensitive
                3. You can change the applied coupon ID for a shopping cart,
                but cannot change the coupon value or what product the coupon is applied to
                4. You cannot add/remove a coupon to/from the system
                5. You cannot remove a shopping cart from the system
                6. You cannot edit a cart after printing this cart's receipt
                7. You cannot access or modify to the system database (the generated data)
                
                Please follow the instructions from the console to use the shopping features.
                Thank you for reading and hope you enjoy our service!
                *****************************************************************************\n\n""");
    }
}
