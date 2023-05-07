package model.data;

/**
 * The class is to control data input
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
    private static DataInput instance = null;
    Scanner scanner;

    // CONSTRUCTOR
    private DataInput() {
        scanner = new Scanner(System.in);
    }

    // METHOD
    public static synchronized DataInput getInstance() {
        if (instance == null) {
            instance = new DataInput();
        }
        return instance;
    }

    public Scanner getScanner() {return scanner;}

    public void readProductFile() {
        // Initialise database locations to store data
        ProductMap products = ShoppingDB.getInstance().getProducts();
        CouponList coupons = ShoppingDB.getInstance().getCoupons();
        Tax taxes = ShoppingDB.getInstance().getTaxes();

        // Try to open the products.txt file
        try {
            Files.lines(Paths.get("src/main/java/database/files/products.txt"))
                    .forEach(
                            line -> {
                                if (!line.isEmpty()) {
                                    String[] newLine = line.split(",");
                                    String type = newLine[0];

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
                                            if (Coupon.getType(newLine[1]).equalsIgnoreCase("price")) {
                                                coupon = new PriceCoupon(newLine[1], newLine[3], Double.parseDouble(newLine[2]));
                                            } else if (Coupon.getType(newLine[1]).equalsIgnoreCase("percent")) {
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
        } catch (IOException e) {}
        System.out.println("Finish retrieving products, coupons, and tax information from database!");
        // Store all the created products, coupons, taxes into ShoppingDB
        ShoppingDB.getInstance().setProducts(products);
        ShoppingDB.getInstance().setCoupons(coupons);
        ShoppingDB.getInstance().setTaxes(taxes);
    }

    public void readCartFile() {
        // Storing location of the created Shopping Cart
        ShoppingCartList carts = ShoppingDB.getInstance().getCarts();

        // Get products information from database
        ProductMap products = ShoppingDB.getInstance().getProducts();

        try {
            Files.lines(Paths.get("src/main/java/database/files/carts.txt"))
                    .forEach(
                            line -> {
                                if (!line.isEmpty()) {
                                    String[] newLine = line.split(",");
                                    int cartID = Integer.parseInt(newLine[0]);
                                    ShoppingCart c = new ShoppingCart(cartID);
                                    String[] coupon = newLine[1].split(":");
                                    if (coupon.length == 2) {
                                        c.setAppliedCouponID(coupon[1]);
                                    }
                                    for (int i = 2; i < newLine.length; i++) {
                                        String[] item = newLine[i].split(":");

                                        // Get product
                                        Product p = products.getProduct(item[0]);

                                        // Check for gift message existence
                                        if (item[1].contains("-")) {
                                            String[] itemDetail = item[1].split("-");
                                            c.addItem(item[0], Integer.parseInt(itemDetail[0]), products);
                                            c.setItemGiftMessage(item[0],itemDetail[1]);
                                        } else {
                                            c.addItem(item[0], Integer.parseInt(item[1]), products);
                                            if (p.getDefaultMessage() != null) {
                                                c.setItemGiftMessage(item[0],p.getDefaultMessage());
                                            }
                                        }

                                    }
                                    carts.addCart(c);
                                }
                            });
            System.out.println("Finish retrieving shopping carts information from database!");
        } catch (IOException e) {}
        // Store all the created carts into ShoppingDB
        ShoppingDB.getInstance().setCarts(carts);
    }

    public void readReceipt(int cartID) {
        try {
            Files.lines(Paths.get("src/main/java/database/cart"+cartID+".txt"))
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

