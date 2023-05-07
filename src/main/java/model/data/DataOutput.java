package model.data;

/**
 * The class is to control data output
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import database.ShoppingDB;
import model.cart.ShoppingCart;
import model.cart.ShoppingCartList;
import model.coupon.Coupon;
import model.coupon.CouponList;
import model.coupon.PercentCoupon;
import model.coupon.PriceCoupon;
import model.product.Product;
import model.product.ProductMap;

public class DataOutput {
    private static DataOutput instance;
    StringBuilder outputText;

    // CONSTRUCTOR
    private DataOutput() {
        outputText = new StringBuilder();
    }

    public static synchronized DataOutput getInstance() {
        if (instance == null) {
            instance = new DataOutput();
        }
        return instance;
    }

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
            CouponList coupons = db.getCoupons();

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
            double subCartTotal = 0;
            double cartTax = 0;
            double cartDiscount = 0;

            for (String item : c.getCartItems().keySet()) {
                // Get product information
                Product p = products.getProduct(item);

                int quantity = c.getCartItems().get(item);
                double price = p.getPrice();
                double subProductTotal = c.getItemSubtotal(p.getName(), products);
                subCartTotal += subProductTotal;
                cartTax += c.getItemTax(p.getName(), products);

                // Write product information
                fWriter.write(item + "\n");
                fWriter.write(String.format("%d x %-35.2f $%.2f\n",quantity,price,subProductTotal));
                // fWriter.write(quantity + "x " + String.format("$%,.2f",p.getPrice()) + "\t\t\t\t\t\t\t\t" + subProductTotal + "\n");
                String couponID = c.getAppliedCouponID();
                if (couponID != null) {
                    // Get the coupon
                    Coupon coupon = coupons.getCoupon(couponID);

                    // Check if the cart contains the product that can apply coupon
                    if (coupon.getProductName().equals(p.getName())) {
                        cartDiscount = c.getItemDiscount(p.getName(), products);
                        // Write discount price for price coupon and percent coupon
                        if (coupon instanceof PriceCoupon) {
                            fWriter.write(String.format("  *Discount(-%.2f)*",((PriceCoupon) coupon).getCouponValue())+ "\t\t\t\t\t\t" + String.format("($%.2f)", cartDiscount) + "\n");
                        } else {
                            fWriter.write(String.format("  *Discount(%d%%)*", ((PercentCoupon) coupon).getCouponValue()) + "\t\t\t\t\t\t" + String.format("($%.2f)", cartDiscount) + "\n");
                        }
                    }
                }
            }
            fWriter.write("------------------------------------------------\n");
            // Cart Items Amount
            fWriter.write("Items Count: " + c.countItems() + "\n\n");
            fWriter.write("Subtotal: \t\t\t\t\t\t\t\t" + String.format("$%.2f", subCartTotal) + "\n");
            fWriter.write("Discount: \t\t\t\t\t\t\t\t-" + String.format("$%.2f", cartDiscount) + "\n");
            fWriter.write("Tax: \t\t\t\t\t\t\t\t\t+" + String.format("$%.2f", cartTax) + "\n\n");
            fWriter.write("Shipping: \t\t\t\t\t\t\t\t\t+" + String.format("$%.2f", c.getShippingFee(products)) + "\n\n");
            fWriter.write("TOTAL: \t\t\t\t\t\t\t\t" + String.format("$%.2f",c.cartAmount(products)) + "\n");
            fWriter.flush();
            fWriter.close();
            System.out.println("Finish writing the printing receipt for cart #" + c.getCartID() + "!");

        } catch (IOException ioex) {
            // do nothing
        }

    }

    public static void main(String[] args) {
        // Test data input
        DataInput dataProcess = DataInput.getInstance();
        dataProcess.readProductFile();
        dataProcess.readCartFile();

        // Test data output
        DataOutput dOut = DataOutput.getInstance();
        dOut.writeReceipt(10);
        ShoppingCart c = ShoppingDB.getInstance().getCarts().getCart(10);
        System.out.println(c.existReceipt());
    }
}
