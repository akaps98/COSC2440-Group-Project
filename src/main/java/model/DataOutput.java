package model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataOutput {
    public void writeFile(ShoppingCart2 c) {
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
            fWriter.write("----------------------------------------------------\n");

            // Write each item
            for (String item : c.getCartItems().keySet()) {
                fWriter.write(item + "\n");
                int quantity = c.getCartItems().get(item);
                String coupon = c.getAppliedCouponID();
                if (coupon != null) {
                    // replace later with product price discount and total price of a product
                    fWriter.write(quantity + "x " + "$180.00 (-10%)" + "\t\t\t\t\t\t\t" + "$1480.00" + "\n");
                } else {
                    // replace later with product price discount and total price of a product
                    fWriter.write(quantity + "x " + "$180.00" + "\t\t\t\t\t\t\t" + "$1480.00" + "\n");
                }
            }
            fWriter.write("----------------------------------------------------\n");

            // Cart Items Amount
            fWriter.write("Cart Items:\t" + c.totalItems() + "\n\n");
            fWriter.write("Cart Discount: " + "50%" + "\t\t\t\t\t\t\t" + "$1200.50" + "\n\n");
            fWriter.write("Subtotal: \t\t\t\t\t\t\t\t\t" + "$1923.11" + "\n\n");
            fWriter.write("TOTAL: \t\t\t\t\t\t\t\t\t\t" + "$1904.23" + "\n");
            fWriter.write("(already included tax)");
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
        dOut.writeFile(c);
    }
}
