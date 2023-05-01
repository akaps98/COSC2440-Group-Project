package model; /**
 * @author <Kang Junsik - s3916884>
 */

// This class saves the entered products and carts by the user while the system is running.

import java.util.ArrayList;

public class ShoppingService {
    private ArrayList<Product> products;  // to save created products
    private ArrayList<ShoppingCart> carts; // to save created shopping carts

    public ShoppingService() {
        products = new ArrayList<Product>();
        carts = new ArrayList<ShoppingCart>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<ShoppingCart> getCarts() {
        return carts;
    }

    public void setCarts(ArrayList<ShoppingCart> carts) {
        this.carts = carts;
    }
}
