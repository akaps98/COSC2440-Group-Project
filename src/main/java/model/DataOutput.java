package model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import database.ShoppingDB;

public class DataOutput {
    public void writeReceipt(ShoppingCart2 c) {
        try {
            FileWriter fWriter = new FileWriter("src/main/java/database/cart"+c.getCartID()+".txt");
            // Header
            fWriter.write("\t\t\t\tOnline Shopping Service\n");
            fWriter.write("\n");
            // Receipt No.
            fWriter.write("Receipt Number: C2440 - G09 - C" + c.getCartID() + "\t\t\t\n");

            // Date & Time
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            fWriter.write(formatter.format(date)+"\n");
            fWriter.write("User: Nguyen Anh Duy \n");
            fWriter.write("---------------------------------------------\n");
            fWriter.write("ITEMS \t\t\t QUANTITY \t\t\t PRICE\n");

            // Write each item
            for (String item : c.getCartItems().keySet()) {
                int quantity = c.getCartItems().get(item);
                fWriter.write(item + "\t\t\t\t" + quantity + "\t\t\t\t" + " $1480.00" + "\n");
                String coupon = c.getAppliedCouponID();
                if (coupon != null) {
                    // replace later with product price discount and total price of a product
                    fWriter.write(" *Discount(10%)*" + "\t\t\t\t\t" + "($1480.00)" + "\n");
                }
            }
            fWriter.write("---------------------------------------------\n");
            // Cart Items Amount
            fWriter.write("Items Count:\t\t" + c.totalItems() + "\n\n");
            fWriter.write("Subtotal: \t\t\t\t\t\t\t" + " $1923.11" + "\n");
            fWriter.write("Discount: \t\t\t\t\t\t\t" + " -" + "$12.50" + "\n");
            fWriter.write("Tax: \t\t\t\t\t\t\t\t" + " +" + "$12.50" + "\n\n");
            fWriter.write("TOTAL: \t\t\t\t\t\t\t\t" + " $1904.23" + "\n");
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
        ProductMap products = dataProcess.readProductFile();
        ShoppingCartList carts = dataProcess.readCartFile();

        // Test data output
        DataOutput dOut = new DataOutput();
        ShoppingCart2 c = carts.getCart(1);
        dOut.writeReceipt(c);
    }
}
