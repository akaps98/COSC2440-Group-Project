package database;

import model.Coupon;
import model.ProductMap;
import model.ShoppingCartList;

import java.util.ArrayList;

public class ShoppingDB {
    private static ShoppingDB instance = null;
    ProductMap products;
    ShoppingCartList carts;
    ArrayList<Coupon> coupons;
    // CONSTRUCTOR
    private ShoppingDB() {
        products = new ProductMap();
        carts = new ShoppingCartList();
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
    public ArrayList<Coupon> getCoupons() {return coupons;}
    public void setCoupons(ArrayList<Coupon> coupons) {this.coupons = coupons;}
}
