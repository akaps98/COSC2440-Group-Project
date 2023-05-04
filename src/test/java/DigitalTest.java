/**
 * @author <Kang Junsik - s3916884>
 */

import model.product.Digital;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitalTest {
    @Test
    void testPrintOut() {
        Digital eBook = new Digital("internet membership", "useful", 12, 20);
        String expectOutput = "DIGITAL - <internet membership>";
        assertEquals(expectOutput, eBook.toString());
    }

    @Test
    void testGetMessage() { // test setMessage & getMessage together
        Digital eBook = new Digital("Java ebook", "thick", 8, 17);
        eBook.setMessage("For you to study Java!");
        assertEquals("For you to study Java!", eBook.getMessage());
    }
}