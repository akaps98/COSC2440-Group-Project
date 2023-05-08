package database;

/**
 * The class is the database that stores all the information about the system
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.cart.ShoppingCartList;
import model.coupon.CouponList;
import model.product.ProductMap;
import model.product.Tax;

public class ShoppingDB {
    // ATTRIBUTES
    private static ShoppingDB instance = null;
    ProductMap products; // Store all the products
    ShoppingCartList carts; // Store all the Shopping Carts
    CouponList coupons; // Store all the coupons
    Tax taxes; // Store all the taxes

    // CONSTRUCTOR
    private ShoppingDB() {
        products = new ProductMap();
        carts = new ShoppingCartList();
        taxes = new Tax();
        coupons = new CouponList();
    }

    // Static method to create instance of ShoppingDB class
    public static synchronized ShoppingDB getInstance() {
        if (instance == null) {
            instance = new ShoppingDB();
        }
        return instance;
    }

    // GETTERS & SETTERS
    public ProductMap getProducts() {
        return products;
    }

    public void setProducts(ProductMap products) {
        this.products = products;
    }

    public ShoppingCartList getCarts() {
        return carts;
    }

    public void setCarts(ShoppingCartList carts) {
        this.carts = carts;
    }

    public CouponList getCoupons() {return coupons;}

    public void setCoupons(CouponList coupons) {this.coupons = coupons;}

    public Tax getTaxes() {
        return taxes;
    }

    public void setTaxes(Tax taxes) {
        this.taxes = taxes;
    }
}
