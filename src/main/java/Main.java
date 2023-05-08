/**
 * desc
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import controller.*;
import model.data.DataInput;
import java.util.Scanner;

public class Main { // run the program
    public static void main(String[] args) {
        // By default, when the system run, the program will execute the database files: products.txt + carts.txt
        DataController dataController = new DataController();
        dataController.generateData(); // must generate data first to avoid creating empty controllers

        // Generate controllers
        ProductController productController = new ProductController();
        ShoppingCartController cartController = new ShoppingCartController();
        CouponController couponController = new CouponController();
        Scanner input = DataInput.getInstance().getScanner();

        // --------------MAIN PROGRAM--------------
        int choice = -1;
        AppController.displayInstructions(); // display instructions
        while (choice != 12) {
            AppController.displayMenu(); // display primary menu
            choice = Integer.parseInt(input.nextLine()); // get the user's input
            switch (choice) {
                case 1 -> productController.displayProducts();
                case 2 -> productController.createProduct();
                case 3 -> productController.editProduct();
                case 4 -> couponController.displayAllCoupons();
                case 5 -> cartController.createShoppingCart();
                case 6 -> cartController.addProductToCart();
                case 7 -> cartController.removeProductFromCart();
                case 8 -> cartController.displayCartDetail();
                case 9 -> cartController.editCart();
                case 10 -> cartController.displayAllCartByWeight();
                case 11 -> cartController.printReceipt();
                default -> System.out.println("""
                        Invalid choice. Please enter again!
                        """);
            }
        }

        // Closing message
        System.out.println("\nThank you for using our service! We hope to see you again ^^");
    }
}