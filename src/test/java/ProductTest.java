/**
 * @author <Kang Junsik - s3916884>
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testCheckNameIsUnique() {
        // This is a validation to check the name is unique
        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        Digital eBook = new Digital("eBook", "Popular", 10, 15);

        ArrayList<Product> savedProductsOnSystem = new ArrayList<>(); // test Product arraylist (all saved products on system)

        savedProductsOnSystem.add(iPhone); // so, this process is to register new product on the store(system)
        savedProductsOnSystem.add(eBook);

        // if the input name by user is not unique, it returns false.
        // but it is unique, it returns true.

        assertFalse(Product.checkNameIsUnique("iPhone", savedProductsOnSystem));
        assertTrue(Product.checkNameIsUnique("Comic", savedProductsOnSystem));
    }

    @Test
    void testCheckQuantityIsValid() {
        // This is a validation to check the quantity is valid
        // if the quantity is below 0, it is invalid quantity.

        assertFalse(Product.checkQuantityIsValid(-3));
        assertTrue(Product.checkQuantityIsValid(2));
    }
}