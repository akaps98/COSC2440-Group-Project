/**
 * @author <Kang Junsik - s3916884>
 */

import model.cart.ShoppingCart;
import model.cart.ShoppingCartList;
import model.product.ProductMap;
import org.junit.jupiter.api.Test;

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
    void testGetItemDiscount() {
        ShoppingCart testSc = new ShoppingCart(1); // generate new shopping cart

        Main.generateData(); // to read data from database
        Main program = new Main();

        ProductMap products = program.products; // make a products map

        testSc.addItem("Airpods", 2, products);
        testSc.setAppliedCouponID("p0904a"); // register coupon for airpods

        // There is 2 airpods, and coupon of airpods provides 18.75 discount for price.
        // 18.75 * 2 = 37.5, so it provides total 37.5 price discount.

        assertEquals(37.5, testSc.getItemDiscount("Airpods", products));
    }

    @Test
    void testGetItemTax() {
        ShoppingCart testSc = new ShoppingCart(1); // generate new shopping cart

        Main.generateData(); // to read data from database
        Main program = new Main();

        ProductMap products = program.products; // make a products map

        testSc.addItem("iPhone", 3, products); // save the products on shopping cart

        double iPhoneTaxAmount = testSc.getItemTax("iPhone", products);

        // There is 3 iPhones in total, and tax type of iPhone is normal. Only 10% tax for each.
        // Price of iPhone is 1430. So, (1430 * 0.1) * 3 = 429.

        assertEquals(429, iPhoneTaxAmount); //  Total tax amount is 429.
    }

    @Test
    void testCartAmount() {
        ShoppingCart testSc = new ShoppingCart(1); // generate new shopping cart

        Main.generateData(); // to read data from database
        Main program = new Main();

        ProductMap products = program.products; // make a products map

        testSc.setAppliedCouponID("d0901a"); // register coupon for ChatGPT Account

        testSc.addItem("iPad", 2, products); // save the products on shopping cart
        testSc.addItem("ChatGPT Account", 3, products);

        // Let's calculate the total cost. iPad is a physical product.
        // price of iPad: 1200 * 2 = 2400
        // total weight: 487 * 2 = 974, shipping fee: 974 * 0.1 = 97.4
        // tax type: normal tax, so only 10% -> 2400 * 0.1 = 240
        // -> 2400 + 97.4 + 240 = 2737.4

        // ChatGPT Account is digital product. No weight.
        // It has a coupon for ChatGPT Account as price type. 1.25 for each.
        // price of ChatGPT Account: 30 * 3 = 90
        // 90 - (1.25 * 3) = 86.25
        // tax type: normal tax, so only 10% -> 90 * 0.1 = 9
        // -> 86.25 + 9 = 95.25

        // therefore, total price is 2737.4 + 95.25 = 2832.65

        assertEquals(testSc.cartAmount(products), 2832.65);
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