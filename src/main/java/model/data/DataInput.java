package model.data;

/**
 * The class control the input of the data from external files
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import database.ShoppingDB;
import model.product.Tax;
import model.cart.ShoppingCart;
import model.cart.ShoppingCartList;
import model.coupon.Coupon;
import model.coupon.CouponList;
import model.coupon.PercentCoupon;
import model.coupon.PriceCoupon;
import model.product.Digital;
import model.product.Physical;
import model.product.Product;
import model.product.ProductMap;

public class DataInput {
    // ATTRIBUTES
    private static DataInput instance = null;
    Scanner scanner;

    // CONSTRUCTOR
    private DataInput() {
        scanner = new Scanner(System.in);
    }

    // GETTERS AND SETTERS
    /**
     * Static method to create instance of DataInput class
     */
    public static synchronized DataInput getInstance() {
        if (instance == null) {
            instance = new DataInput();
        }
        return instance;
    }

    public Scanner getScanner() {return scanner;}

    // METHODS

    /**
     * This method read the products.txt file and retrieve all the products, coupons, and tax data
     *
     * File Syntax:
     * (40 Products, 10 gifts, 50 coupons, 3 tax values)
     * --- PRODUCT syntax ---
     * #type {PHYSICAL / DIGITAL},#name,#description,#quantity,#price($),#taxType,#weight(g) *only for PHYSICAL PRODUCTS*
     *
     * --- GIFT syntax ---
     * #type {GIFT},#type,#name,#description,#quantity,#price($),#taxType,#weight(g) *only for PHYSICAL PRODUCTS*,#giftMessage *only for GIFT PRODUCTS*
     *
     * --- COUPON syntax ---
     * #type {COUPON},#couponID,#couponValue,#productName
     *
     * --- TAX syntax ---
     * #type {COUPON},#taxType,#taxValue
     *
     * Note: each column is separated by the delimiter ","
     */
    public void readProductFile() {
        // Initialise database locations to store data
        ProductMap products = ShoppingDB.getInstance().getProducts();
        CouponList coupons = ShoppingDB.getInstance().getCoupons();
        Tax taxes = ShoppingDB.getInstance().getTaxes();

        // Try to open the products.txt file
        try {
            Files.lines(Paths.get("src/main/java/database/files/products.txt"))
                    // Get each line
                    .forEach(
                            line -> {
                                if (!line.isEmpty()) {
                                    String[] newLine = line.split(",");
                                    String type = newLine[0]; // Get the type of each item in the line

                                    switch(type) {
                                        // Case 1: create a Physical Product
                                        case "PHYSICAL":
                                            products.addProduct(new Physical(newLine[1], newLine[2], Integer.parseInt(newLine[3]), Double.parseDouble(newLine[4]), newLine[5], Double.parseDouble(newLine[6])));
                                            break;
                                        // Case 2: create a Digital Product
                                        case "DIGITAL":
                                            products.addProduct(new Digital(newLine[1], newLine[2], Integer.parseInt(newLine[3]), Double.parseDouble(newLine[4]), newLine[5]));
                                            break;
                                        // Case 3: create a Gift Product
                                        case "GIFT":
                                            Product p;
                                            if (newLine[1].equalsIgnoreCase("PHYSICAL")) {
                                                p = new Physical(newLine[2], newLine[3], Integer.parseInt(newLine[4]), Double.parseDouble(newLine[5]), newLine[6], Double.parseDouble(newLine[7]));
                                                ((Physical) p).setMessage(newLine[8]);
                                            } else {
                                                p = new Digital(newLine[2], newLine[3], Integer.parseInt(newLine[4]), Double.parseDouble(newLine[5]), newLine[6]);
                                                ((Digital) p).setMessage(newLine[7]);
                                            }
                                            products.addProduct(p);
                                            break;
                                        // Case 4: create a Coupon
                                        case "COUPON":
                                            // Create new coupon
                                            Coupon coupon = null;
                                            if (Coupon.getType(newLine[1]).equalsIgnoreCase("price")) { // Create price coupon
                                                coupon = new PriceCoupon(newLine[1], newLine[3], Double.parseDouble(newLine[2]));
                                            } else if (Coupon.getType(newLine[1]).equalsIgnoreCase("percent")) { // Create percent coupon
                                                coupon = new PercentCoupon(newLine[1], newLine[3], Integer.parseInt(newLine[2]));
                                            }
                                            // Add new coupon to the coupon list
                                            if (coupon != null) {
                                                coupons.addCoupon(coupon);
                                            }
                                            break;
                                        case "TAX":
                                            // Initialise values for Tax here
                                            taxes.addTax(newLine[1], Double.parseDouble(newLine[2]));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            });
        } catch (IOException e) {
            System.out.println("Cannot retrieve data from this file!");
        }
        // Store all the created products, coupons, taxes into ShoppingDB
        ShoppingDB.getInstance().setProducts(products);
        ShoppingDB.getInstance().setCoupons(coupons);
        ShoppingDB.getInstance().setTaxes(taxes);

        // System.out.println("Finish retrieving products, coupons, and tax information from database!");
    }

    /**
     * This method read the carts.txt file and retrieve all the carts data
     *
     * Syntax:
     * (10 shopping carts)
     *
     * --- Cart syntax (normal) ---
     * #cartID,#coupon:,#product1:#quantity1,#product2:#quantity2,#product3:#quantity3,...
     *
     * --- Cart syntax (contains coupon and gift message) ---
     * #cartID,#coupon:#couponID,#product1:#quantity1-#giftMessage1,#product2:#quantity2,#product3:#quantity3-#giftMessage3,...
     *
     * Note:
     * 1/ each column is separated by the delimiter ","
     * 2/ The couponID and the product quantity is separated by the delimiter ":"
     * 3/ The product gift message is separated by the delimiter "-"
     */
    public void readCartFile() {
        // Storing location of the created Shopping Cart
        ShoppingCartList carts = ShoppingDB.getInstance().getCarts();

        // Get products information from database
        ProductMap products = ShoppingDB.getInstance().getProducts();

        // Try to open carts.txt file
        try {
            Files.lines(Paths.get("src/main/java/database/files/carts.txt"))
                    // Get each line
                    .forEach(
                            line -> {
                                if (!line.isEmpty()) {
                                    // Get the cart information
                                    String[] newLine = line.split(",");
                                    int cartID = Integer.parseInt(newLine[0]);
                                    ShoppingCart c = new ShoppingCart(cartID);
                                    String[] coupon = newLine[1].split(":");
                                    // Check if there is a coupon id existed
                                    if (coupon.length == 2) {
                                        c.setAppliedCouponID(coupon[1]);
                                    }
                                    // Get the cart items
                                    for (int i = 2; i < newLine.length; i++) {
                                        String[] item = newLine[i].split(":");

                                        // Get product
                                        Product p = products.getProduct(item[0]);

                                        // Check for gift message existence
                                        if (item[1].contains("-")) { // If found a message for this product in the cart
                                            String[] itemDetail = item[1].split("-"); // Get the quantity and gift message
                                            c.addItem(item[0], Integer.parseInt(itemDetail[0]), products);
                                            c.setItemGiftMessage(item[0],itemDetail[1]);
                                        } else { // No gift message found, just add a normal item to the cart
                                            c.addItem(item[0], Integer.parseInt(item[1]), products);
                                            if (p.getDefaultMessage() != null) { // Add gift message if this product has a default message
                                                c.setItemGiftMessage(item[0],p.getDefaultMessage());
                                            }
                                        }

                                    }
                                    carts.addCart(c);
                                }
                            });
        } catch (IOException e) {
            System.out.println("Cannot retrieve data from this file!");
        }
        // Store all the created carts into ShoppingDB
        ShoppingDB.getInstance().setCarts(carts);

        // System.out.println("Finish retrieving shopping carts information from database!");
    }

    /**
     * This method read the receipt and print to the console application for user to view
     * @param cartID
     */
    public void readReceipt(int cartID, String fileName) {
        // Try to find and open the cart file
        try {
            Files.lines(Paths.get("src/main/java/database/receipts/"+fileName+".txt"))
                    // Print each line from the receipt to the console
                    .forEach(
                            line -> {
                                System.out.println(line);
                            });
        } catch (IOException e) {}

    }

    public static void main(String[] args) {
        DataInput dataProcess = DataInput.getInstance();
        dataProcess.readProductFile();
        dataProcess.readCartFile();
        ShoppingDB db = ShoppingDB.getInstance();
        db.getProducts().viewAvailableProducts();
        db.getCarts().viewCartsAfterSorted();
        db.getCoupons().viewAllCoupons();
//        dataProcess.readReceipt(1);
    }
}

