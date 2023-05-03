package database;

import model.Coupon;
import model.CouponList;
import model.ProductMap;
import model.ShoppingCartList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


public class ShoppingDB {
    private static ShoppingDB instance = null;
    ProductMap products;
    ShoppingCartList carts;
    CouponList coupons;
    Map<String, Double> taxes;

    // CONSTRUCTOR
    private ShoppingDB() {
        products = new ProductMap();
        carts = new ShoppingCartList();
        taxes = new HashMap<>();
    }

    // Static method to create instance of ShoppingDB class
    public static synchronized ShoppingDB getInstance() {
        if (instance == null)
            instance = new ShoppingDB();
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

    public Map<String, Double> getTaxes() {
        return taxes;
    }

    public void setTaxes(Map<String, Double> taxes) {
        this.taxes = taxes;
    }
}
