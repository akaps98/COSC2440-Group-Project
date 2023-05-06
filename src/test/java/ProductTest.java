/**
 * @author <Kang Junsik - s3916884>
 */

import model.product.Digital;
import model.product.Physical;
import model.product.Product;
import model.product.ProductMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testCheckNameIsUnique() {
        // This is a validation to check the name is unique
        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        Digital eBook = new Digital("eBook", "Popular", 10, 15);

        ProductMap savedProductsOnSystem = new ProductMap(); // test Productmap (all saved products on system)

        savedProductsOnSystem.addProduct(iPhone); // so, this process is to register new product on the store(system)
        savedProductsOnSystem.addProduct(eBook);

        // if the input name by user is not unique, it returns false.
        // but it is unique, it returns true.

        assertFalse(Product.checkProductAlreadyExisted("iPhone", savedProductsOnSystem));
        assertTrue(Product.checkProductAlreadyExisted("Comic", savedProductsOnSystem));
    }

    @Test
    void testCheckQuantityIsValid() {
        // This is a validation to check the quantity is valid
        // if the quantity is below 0, it is invalid quantity.

        assertFalse(Product.checkQuantityIsValid(-5));
        assertTrue(Product.checkQuantityIsValid(4));
    }
}