/**
 * @author <Kang Junsik - s3916884>
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    @Test
    void testAddItemWithShoppingCartSize() {
        ShoppingCart testSc = new ShoppingCart();
        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        Digital eBook = new Digital("eBook", "Popular", 10, 15);

        ArrayList<Product> savedProductsOnSystem = new ArrayList<>(); // test Product arraylist (all saved products on system)

        savedProductsOnSystem.add(iPhone); // so, this process is to register new product on the store(system)
        savedProductsOnSystem.add(eBook);

        testSc.addItem("iPhone", savedProductsOnSystem); // save the products on shopping cart
        testSc.addItem("eBook", savedProductsOnSystem);

        // now, I added two products on shopping cart; iPhone and eBook.
        // Let's check the size of shopping cart. I added 2 products.

        assertEquals(2, testSc.getProductsName().size());
    }

    @Test
    void testAddItemWithProductNameInShoppingCart() {
        ShoppingCart testSc = new ShoppingCart();
        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        Digital eBook = new Digital("eBook", "Popular", 10, 15);

        ArrayList<Product> savedProductsOnSystem = new ArrayList<>(); // test Product arraylist (all saved products on system)

        savedProductsOnSystem.add(iPhone); // so, this process is to register new product on the store(system)
        savedProductsOnSystem.add(eBook);

        testSc.addItem("iPhone", savedProductsOnSystem); // save the products on shopping cart
        testSc.addItem("eBook", savedProductsOnSystem);

        // now, I added two products on shopping cart; iPhone and eBook.
        // In shopping carts, if it has same names with 'iPhone' and 'eBook', it means that the system saved successfully.

        assertTrue(testSc.getProductsName().contains("iPhone"));
        assertTrue(testSc.getProductsName().contains("eBook"));
    }

    @Test
    void testRemoveItem() {
        ShoppingCart testSc = new ShoppingCart();
        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        Digital eBook = new Digital("eBook", "Popular", 10, 15);

        ArrayList<Product> savedProductsOnSystem = new ArrayList<>(); // test Product arraylist (all saved products on system)

        savedProductsOnSystem.add(iPhone); // so, this process is to register new product on the store(system)
        savedProductsOnSystem.add(eBook);

        testSc.addItem("iPhone", savedProductsOnSystem); // save the products on shopping cart
        testSc.addItem("eBook", savedProductsOnSystem);

        testSc.removeItem("eBook", savedProductsOnSystem); // remove the product 'eBook' on shopping cart

        // I only removed 'eBook', so 'iPhone' should be remained.

        assertFalse(testSc.getProductsName().contains("eBook")); // eBook is deleted,
        assertTrue(testSc.getProductsName().contains("iPhone")); // iPhone is still remained.
    }

    @Test
    void testCartAmount() {
        ShoppingCart testSc = new ShoppingCart();
        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        Digital eBook = new Digital("eBook", "Popular", 10, 15);

        ArrayList<Product> savedProductsOnSystem = new ArrayList<>(); // test Product arraylist (all saved products on system)

        savedProductsOnSystem.add(iPhone); // so, this process is to register new product on the store(system)
        savedProductsOnSystem.add(eBook);

        testSc.addItem("iPhone", savedProductsOnSystem); // save the products on shopping cart
        testSc.addItem("eBook", savedProductsOnSystem);

        // Let's calculate the total cost. Only iPhone is a physical product.
        // (200 + 15) + (5 * 0.1) = 215.5

        assertEquals(testSc.cartAmount(savedProductsOnSystem), 215.5);
    }

    @Test
    void testCompareTo() {
        // Let's test it sorts the array of shopping carts as ascending order properly.
        ArrayList<ShoppingCart> savedShoppingCartsOnSystem = new ArrayList<>(); // test Shopping Cart arraylist (all saved shopping carts on system)

        ShoppingCart testSc1 = new ShoppingCart(); // it has numbering 1
        ShoppingCart testSc2 = new ShoppingCart(); // it has numbering 2
        ShoppingCart testSc3 = new ShoppingCart(); // it has numbering 3

        savedShoppingCartsOnSystem.add(testSc1);
        savedShoppingCartsOnSystem.add(testSc2);
        savedShoppingCartsOnSystem.add(testSc3);

        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        Physical tablet = new Physical("tablet", "Sharp", 30, 150, 8);
        Physical monitor = new Physical("monitor", "Big", 25, 250, 12);

        ArrayList<Product> savedProductsOnSystem = new ArrayList<>(); // test Product arraylist (all saved products on system)

        savedProductsOnSystem.add(iPhone); // so, this process is to register new product on the store(system)
        savedProductsOnSystem.add(tablet);
        savedProductsOnSystem.add(monitor);

        testSc3.addItem("iPhone", savedProductsOnSystem);
        testSc3.addItem("tablet", savedProductsOnSystem);
        testSc3.addItem("monitor", savedProductsOnSystem); // total weight = 5 + 8 + 12 = 25
        testSc3.cartAmount(savedProductsOnSystem);

        testSc1.addItem("monitor", savedProductsOnSystem);
        testSc1.addItem("iPhone", savedProductsOnSystem); // total weight = 12 + 5 = 17
        testSc1.cartAmount(savedProductsOnSystem);

        testSc2.addItem("iPhone", savedProductsOnSystem); // total weight = 5
        testSc2.cartAmount(savedProductsOnSystem);

        // according to the total weight, it should be testSc3 -> testSc1 -> testSc2 after sorting as ascending order
        Collections.sort(savedShoppingCartsOnSystem);

        // They have their own number, let's check by it.
        assertEquals(3, savedShoppingCartsOnSystem.get(0).getNumbering()); // testSc3
        assertEquals(1, savedShoppingCartsOnSystem.get(1).getNumbering()); // testSc1
        assertEquals(2, savedShoppingCartsOnSystem.get(2).getNumbering()); // testSc2
    }

    @Test
    void testNumbering() {
        // This is my own function.
        // Shopping cart will get the number by the order of creation.
        ShoppingCart testSc1 = new ShoppingCart(); // no.1
        ShoppingCart testSc2 = new ShoppingCart(); // no.2
        ShoppingCart testSc3 = new ShoppingCart(); // no.3

        assertEquals(1, testSc1.getNumbering());
        assertEquals(2, testSc2.getNumbering());
        assertEquals(3, testSc3.getNumbering());
    }
}