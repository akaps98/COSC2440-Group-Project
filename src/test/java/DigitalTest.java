/**
 * @author <Kang Junsik - s3916884>
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitalTest {

    @Test
    void testPrintOut() {
        Digital eBook = new Digital("eBook", "Popular", 10, 15);
        String expectOutput = "DIGITAL - <eBook>";
        assertEquals(expectOutput, eBook.printOut());
    }

    @Test
    void testGetMessage() { // test setMessage & getMessage together
        Digital eBook = new Digital("eBook", "Popular", 10, 15);
        eBook.setMessage("For you to study Java!");
        assertEquals("For you to study Java!", eBook.getMessage());
    }
}