package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import database.ShoppingDB;

public class DataInput {
    ProductMap products;
    ShoppingCartList carts;
    Map<String, Double> taxes;
    ArrayList<Coupon> coupons;

    // CONSTRUCTOR
    public DataInput() {
        products = new ProductMap();
        carts = new ShoppingCartList();
        taxes = new HashMap<>();
        coupons = new ArrayList<Coupon>();
    }

    // METHOD
    public void readProductFile() {
        // Storing location of the created products
        // ProductMap products = new ProductMap();

        try {
            Files.lines(Paths.get("src/main/java/database/products.txt"))
                    // .filter(l -> l.length() > 5)
                    // .map(line -> line.length())
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
                                            Coupon coupon = new Coupon(newLine[1],newLine[2],products.getProduct(newLine[4]),Double.parseDouble(newLine[3]));
                                            // Add new coupon to the coupon list
                                            coupons.add(coupon);
                                            break;
                                        case "TAX":
                                            // Initialise values for Tax here
                                            if (newLine[1].equals("normalTax")) {
                                                taxes.put((newLine[1]), Double.parseDouble(newLine[2]));
                                            } else if (newLine[1].equals("luxuryTax")) {
                                                taxes.put((newLine[1]), Double.parseDouble(newLine[2]));
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            });
//            System.out.println(products.viewAllProducts());
            System.out.println("Finish!");

        } catch (IOException e) {}
        // Store all the created products, coupons, taxes into ShoppingDB
        ShoppingDB db = ShoppingDB.getInstance();
        db.setProducts(this.products);
        db.setCoupons(this.coupons);
        db.setTaxes(this.taxes);
    }

    public ShoppingCartList readCartFile() {
//        // Storing location of the created Shopping Cart
//        ShoppingCartList carts = new ShoppingCartList();
        try {
            Files.lines(Paths.get("src/main/java/database/carts.txt"))
                    .forEach(
                            line -> {
                                if (!line.isEmpty()) {
                                    String[] newLine = line.split(",");
                                    int cartID = Integer.parseInt(newLine[0]);
                                    ShoppingCart2 c = new ShoppingCart2(cartID);
                                    String[] coupon = newLine[1].split(":");
                                    if (coupon.length == 2) {
                                        c.setAppliedCouponID(coupon[1]);
                                    }
                                    for (int i = 2; i < newLine.length; i++) {
                                        String[] item = newLine[i].split(":");
                                        c.addItem(item[0], Integer.parseInt(item[1]), products);
                                    }
                                    carts.addCart(c);
                                }
                            });
//            carts.viewCarts();
            System.out.println("Finish!");
        } catch (IOException e) {}
        ShoppingDB db = ShoppingDB.getInstance();
        db.setCarts(this.carts);
    }

    public void readReceipt(int cartID) {
        try {
            Files.lines(Paths.get("src/main/java/database/cart"+cartID+".txt"))
                    .forEach(
                            line -> {
                                System.out.println(line);
                            });
            System.out.println("Finish!");
        } catch (IOException e) {}

    }

    public static void main(String[] args) {
        DataInput dataProcess = new DataInput();
        ShoppingDB db = ShoppingDB.getInstance();
        db.getProducts().viewAvailableProducts();
        db.getCarts().viewCartsAfterSorted();
//        dataProcess.readReceipt(1);
    }
}

