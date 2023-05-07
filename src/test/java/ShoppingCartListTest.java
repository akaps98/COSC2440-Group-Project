/**
 * The class stores information about all the Products inside a Product List
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import controller.DataController;
import database.ShoppingDB;
import model.cart.ShoppingCart;
import model.cart.ShoppingCartList;
import model.product.ProductMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartListTest {
    @Test
    void testContainCart() {
        ShoppingCartList scl = new ShoppingCartList(); // generate new shopping cart list

        ShoppingCart testSc1 = new ShoppingCart(1); // generate new shopping cart
        ShoppingCart testSc2 = new ShoppingCart(2); // generate new shopping cart
        ShoppingCart testSc3 = new ShoppingCart(3); // generate new shopping cart

        // save the cart on shopping carts list
        scl.addCart(testSc1);
        scl.addCart(testSc2);
        scl.addCart(testSc3);

        assertTrue(scl.containCart(1)); // it contains testSc1 which is the ID is 1.
        assertFalse(scl.containCart(4)); // But, it doesn't contain the cart which is the ID is 4.
    }

    @Test
    void testRemoveCart() {
        ShoppingCartList scl = new ShoppingCartList(); // generate new shopping cart list

        ShoppingCart testSc1 = new ShoppingCart(1); // generate new shopping cart
        ShoppingCart testSc2 = new ShoppingCart(2); // generate new shopping cart
        ShoppingCart testSc3 = new ShoppingCart(3); // generate new shopping cart

        // save the cart on shopping carts list
        scl.addCart(testSc1);
        scl.addCart(testSc2);
        scl.addCart(testSc3);

        scl.removeCart(testSc2); // Remove only the cart which is ID 2.

        assertEquals(2, scl.countCarts()); // it now contains only 2 carts(3-1=2),
        assertFalse(scl.containCart(2)); // it doesn't contain the cart which is ID2 anymore.
    }

    @Test
    void testIndexOf() {
        ShoppingCartList scl = new ShoppingCartList(); // generate new shopping cart list

        ShoppingCart testSc1 = new ShoppingCart(1); // generate new shopping cart
        ShoppingCart testSc2 = new ShoppingCart(2); // generate new shopping cart
        ShoppingCart testSc3 = new ShoppingCart(3); // generate new shopping cart

        // save the cart on shopping carts list
        scl.addCart(testSc1);
        scl.addCart(testSc2);
        scl.addCart(testSc3);

        assertEquals(0, scl.indexOf(testSc1)); // index of testSc1 is 0; because it added first.
        assertEquals(1, scl.indexOf(testSc2)); // index of testSc2 is 1.
        assertEquals(2, scl.indexOf(testSc3)); // index of testSc3 is 2.
    }

    @Test
    void testGetCart() {
        ShoppingCartList scl = new ShoppingCartList(); // generate new shopping cart list

        ShoppingCart testSc1 = new ShoppingCart(1); // generate new shopping cart
        ShoppingCart testSc2 = new ShoppingCart(2); // generate new shopping cart
        ShoppingCart testSc3 = new ShoppingCart(3); // generate new shopping cart

        // save the cart on shopping carts list
        scl.addCart(testSc1);
        scl.addCart(testSc2);
        scl.addCart(testSc3);

        assertEquals(testSc2, scl.getCart(2)); // the ID of testSc2 is 2.
        assertEquals(null, scl.getCart(5)); // there is no cart which contains ID of 5, so it returns null.
    }

    @Test
    void testSortCartsByWeight() {
        // Let's test it sorts the array of shopping carts as ascending order properly.
        ShoppingCart testSc1 = new ShoppingCart(1); // generate new shopping cart
        ShoppingCart testSc2 = new ShoppingCart(2); // generate new shopping cart
        ShoppingCart testSc3 = new ShoppingCart(3); // generate new shopping cart

        // Generate data
        DataController dataController = new DataController();
        dataController.generateData(); // to read data from database;

        ProductMap products = ShoppingDB.getInstance().getProducts(); // get list of products from database
        ArrayList<ShoppingCart> carts = new ArrayList<>();

        // save the products on shopping cart
        testSc1.addItem("Galaxy S23", 4, products); // total weight = 168 * 4 = 672
        testSc2.addItem("iPhone", 3, products); // total weight = 148 * 3 = 444
        testSc3.addItem("iPad", 1, products); // total weight = 487 * 1 = 487

        // save the cart on shopping carts list
        carts.add(testSc1);
        carts.add(testSc2);
        carts.add(testSc3);

        // according to the total weight, it should be testSc2 -> testSc3 -> testSc1 after sorting as ascending order
        ShoppingCartList.sortCartsByWeight(carts);

        // They have their own ID, let's check by it.
        assertEquals(2, carts.get(0).getCartID()); // testSc2
        assertEquals(3, carts.get(1).getCartID()); // testSc3
        assertEquals(1, carts.get(2).getCartID()); // testSc1
    }
}