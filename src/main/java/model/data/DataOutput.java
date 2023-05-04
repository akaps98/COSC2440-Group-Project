package model.data;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import database.ShoppingDB;
import model.cart.ShoppingCart;
import model.cart.ShoppingCartList;
import model.product.Product;
import model.product.ProductMap;

public class DataOutput {
    public void writeReceipt(int cartID) {
        try {
            FileWriter fWriter = new FileWriter("src/main/java/database/receipts/cart"+cartID+".txt");
            Scanner input = new Scanner(System.in);

            // Ask for user full name
            System.out.print("Enter your full name: ");
            String fullName = input.nextLine();

            // Get shopping carts and products information
            ShoppingDB db = ShoppingDB.getInstance();
            ProductMap products = db.getProducts();
            ShoppingCartList carts = db.getCarts();
            ShoppingCart c = carts.getCart(cartID);

            // Header
            fWriter.write("\t\t\t\tOnline Shopping Service\n\n");
            // Receipt No.
            fWriter.write("Receipt Number: C2440 - G09 - C" + cartID + "\t\t\t\n");

            // Date & Time
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            fWriter.write(formatter.format(date)+"\n");
            fWriter.write("User: " + fullName + "\n");
            fWriter.write("------------------------------------------------\n");
            fWriter.write(String.format("%-40s %s \n","ITEMS","PRICE"));

            // Write each item
            for (String item : c.getCartItems().keySet()) {
                // Get product information
                Product p = products.getProduct(item);

                int quantity = c.getCartItems().get(item);
                double price = p.getPrice();
                double subProductTotal = price * quantity;

                // Write product information
                fWriter.write(item + "\n");
                fWriter.write(String.format("%d x %-35.2f $%.2f\n",quantity,price,subProductTotal));
                // fWriter.write(quantity + "x " + String.format("$%,.2f",p.getPrice()) + "\t\t\t\t\t\t\t\t" + subProductTotal + "\n");
                String coupon = c.getAppliedCouponID();
                if (coupon != null) {
                    // replace later with product price discount and total price of a product
                    fWriter.write(" *Discount(10%)*" + "\t\t\t\t\t" + "($1480.00)" + "\n");
                }
            }
            fWriter.write("------------------------------------------------\n");
            // Cart Items Amount
            fWriter.write("Items Count: " + c.countItems() + "\n\n");
            fWriter.write("Subtotal: \t\t\t\t\t\t\t\t" + "$1923.11" + "\n");
            fWriter.write("Discount: \t\t\t\t\t\t\t\t-" + "$12.50" + "\n");
            fWriter.write("Tax: \t\t\t\t\t\t\t\t\t+" + "$12.50" + "\n\n");
            fWriter.write("TOTAL: \t\t\t\t\t\t\t\t\t" + String.format("$%.2f",c.cartAmount(products)) + "\n");
            fWriter.flush();
            fWriter.close();
            System.out.println("Finish writing the printing receipt for cart #" + c.getCartID() + "!");

        } catch (IOException ioex) {
            // do nothing
        }

    }

    public static void main(String[] args) {
        // Test data input
        DataInput dataProcess = new DataInput();
        dataProcess.readProductFile();
        dataProcess.readCartFile();

        // Test data output
        DataOutput dOut = new DataOutput();
        ShoppingDB db = ShoppingDB.getInstance();
        dOut.writeReceipt(1);
    }
}
