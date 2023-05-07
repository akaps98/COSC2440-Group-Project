/**
 * The class stores information about all the Products inside a Product List
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.product.Digital;
import model.product.Physical;
import model.product.ProductMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapTest {
    @Test
    void testCountProduct() {
        ProductMap pm = new ProductMap(); // generate new product map

        // make a products to put in product map
        Physical monitor = new Physical("monitor", "big enough", 30, 250, 20);
        Digital membership = new Digital("internet membership", "useful", 12, 20);

        // add products into product map
        pm.addProduct(monitor);
        pm.addProduct(membership);

        // Now, I put only 2 products on it.
        // It should be returned 2.
        assertEquals(2, pm.countProduct());
    }

    @Test
    void testResetProductList() {
        ProductMap pm = new ProductMap(); // generate new product map

        // make a products to put in product map
        Physical monitor = new Physical("monitor", "big enough", 30, 250, 20);
        Digital membership = new Digital("internet membership", "useful", 12, 20);
        Digital account = new Digital("Netflix account", "watchable", 12, 20);

        // add products into product map
        pm.addProduct(monitor);
        pm.addProduct(membership);
        pm.addProduct(account);

        // Now, it contains 3 products.
        // I will reset the current product map.
        pm.resetProductList();

        // Then, there is no product in it.
        assertEquals(0, pm.countProduct());
    }

    @Test
    void testContainProduct() {
        ProductMap pm = new ProductMap(); // generate new product map

        // make a products to put in product map
        Physical monitor = new Physical("monitor", "big enough", 30, 250, 20);
        Digital membership = new Digital("internet membership", "useful", 12, 20);

        // add products into product map
        pm.addProduct(monitor);
        pm.addProduct(membership);

        assertTrue(pm.containProduct("monitor")); // It contains monitor.
        assertFalse(pm.containProduct("account")); // It doesn't contain account.
    }

    @Test
    void testRemoveProduct() {
        ProductMap pm = new ProductMap(); // generate new product map

        // make a products to put in product map
        Physical monitor = new Physical("monitor", "big enough", 30, 250, 20);
        Digital membership = new Digital("internet membership", "useful", 12, 20);

        // add products into product map
        pm.addProduct(monitor);
        pm.addProduct(membership);

        // It contains monitor currently.
        assertTrue(pm.containProduct("monitor"));

        // Remove monitor
        pm.removeProduct(monitor);

        // It doesn't contain monitor anymore.
        assertFalse(pm.containProduct("monitor"));
    }
}