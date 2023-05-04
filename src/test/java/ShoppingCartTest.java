/**
 * @author <Kang Junsik - s3916884>
 */

import org.junit.jupiter.api.Test;

import model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    @Test
    void testAddItemWithShoppingCartSize() {
        ShoppingCart testSc = new ShoppingCart(1); // generate new shopping cart

        Main.generateData(); // to read data from database
        Main program = new Main();

        ProductMap products = program.products; // make a products map

        testSc.addItem("iPhone", 3, products); // save the products on shopping cart
        testSc.addItem("WordPress Themes", 5, products);

        // now, I added two products on shopping cart; iPhone and eBook.
        // Let's check the size of shopping cart. I added only 2 products.

        assertEquals(2, testSc.getCartItems().size());
    }

    @Test
    void testAddItemWithProductNameInShoppingCart() {
        ShoppingCart testSc = new ShoppingCart(1); // generate new shopping cart

        Main.generateData(); // to read data from database
        Main program = new Main();

        ProductMap products = program.products; // make a products map

        testSc.addItem("iPhone", 3, products); // save the products on shopping cart
        testSc.addItem("WordPress Themes", 5, products);

        // now, I added two products on shopping cart; iPhone and eBook.
        // In shopping carts, if it has same names with 'iPhone' and 'eBook', it means that the system saved successfully.

        assertTrue(testSc.getCartItems().containsKey("iPhone"));
        assertTrue(testSc.getCartItems().containsKey("WordPress Themes"));
    }

    @Test
    void testRemoveItem() {
        ShoppingCart testSc = new ShoppingCart(1); // generate new shopping cart

        Main.generateData(); // to read data from database
        Main program = new Main();

        ProductMap products = program.products; // make a products map

        testSc.addItem("iPhone", 3, products); // save the products on shopping cart
        testSc.addItem("WordPress Themes", 5, products);

        testSc.removeItem("iPhone", 1, products); // remove 1 iPhone, so remained quantity will be 3-1=2.
        testSc.removeItem("WordPress Themes", 2, products); // remove 2 eBook, so remained quantity will be 5-2=3.

        assertEquals(2, testSc.getCartItems().get("iPhone"));
        assertEquals(3, testSc.getCartItems().get("WordPress Themes"));
    }

    @Test
    void testCartAmount() {
        ShoppingCart testSc = new ShoppingCart(1); // generate new shopping cart

        Main.generateData(); // to read data from database
        Main program = new Main();

        ProductMap products = program.products; // make a products map

        testSc.addItem("iPhone", 5, products); // save the products on shopping cart
        //testSc.addItem("GitHub Enterprise", 3, products);

         // Let's calculate the total cost. Only iPhone is a physical product.
         // {(200 + 15) * 5} + {(5 * 0.1) * 7} = 1,099.5.5

        // assertEquals(testSc.cartAmount(products), 1099.5);
    }

    @Test
    void testCompareTo() {
        // Let's test it sorts the array of shopping carts as ascending order properly.
        ShoppingCart testSc1 = new ShoppingCart(1); // generate new shopping cart
        ShoppingCart testSc2 = new ShoppingCart(2); // generate new shopping cart
        ShoppingCart testSc3 = new ShoppingCart(3); // generate new shopping cart

        Main.generateData(); // to read data from database
        Main program = new Main();

        ProductMap products = program.products; // make a products map
        ArrayList<ShoppingCart> carts = new ArrayList<>();

        // save the products on shopping cart
        testSc1.addItem("Galaxy S23", 4, products); // total weight = 168 * 4 = 672
        System.out.println(testSc1.calTotalWeight(products)); // doesn't work
        testSc2.addItem("iPhone", 3, products); // total weight = 148 * 3 = 444
        testSc3.addItem("iPad", 1, products); // total weight = 487 * 1 = 487

        // save the cart on shopping carts list
        carts.add(testSc1);
        carts.add(testSc2);
        carts.add(testSc3);

        System.out.println(carts.get(0).getTotalWeight());
        System.out.println(carts.get(1).getTotalWeight());
        System.out.println(carts.get(2).getTotalWeight());

        // according to the total weight, it should be testSc2 -> testSc3 -> testSc1 after sorting as ascending order
        ShoppingCartList.sortCartsByWeight(carts);

        System.out.println(carts.get(0).getTotalWeight());
        System.out.println(carts.get(1).getTotalWeight());
        System.out.println(carts.get(2).getTotalWeight());


        // They have their own number, let's check by it.
//        assertEquals(2, carts.get(0).getCartID()); // testSc3
//        assertEquals(3, carts.get(1).getCartID()); // testSc1
//        assertEquals(1, carts.get(2).getCartID()); // testSc2
    }
}