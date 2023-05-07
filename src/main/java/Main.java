/**
 * @author
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
        System.out.println("\nWelcome to the online shopping service application!");
        while (choice != 13) {
            AppController.displayMenu();
            choice = Integer.parseInt(input.nextLine()); // get the user's input
            switch (choice) {
                case 1 -> productController.displayProducts();
                case 2 -> productController.createProduct();
                case 3 -> productController.editProduct();
                case 4 -> couponController.displayAllCoupons();
                case 5 -> couponController.applyCoupon();
                case 6 -> cartController.createShoppingCart();
                case 7 -> cartController.addProductToCart();
                case 8 -> cartController.removeProductFromCart();
                case 9 -> cartController.displayCartDetail();
                case 10 -> cartController.editCart();
                case 11 -> cartController.displayAllCartByWeight();
                case 12 -> cartController.printReceipt();
                default -> System.out.println("""
                        Invalid choice. Please enter again!
                        """);
            }
        }

        // Closing message
        System.out.println("\nThank you for using our service! We hope to see you again ^^");
    }
}