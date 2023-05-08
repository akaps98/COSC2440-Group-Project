/**
 * The class is a test unit for Physical class
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.product.Physical;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhysicalTest {
    @Test
    void testPrintOut() {
        Physical iPhone = new Physical("monitor", "big enough", 30, 250, 20);
        String expectOutput = "PHYSICAL - <monitor>";
        assertEquals(expectOutput, iPhone.toString());
    }

    @Test
    void testGetMessage() { // test setMessage & getMessage together
        Physical iPhone = new Physical("iPad", "sharp", 30, 300, 7);
        iPhone.setMessage("Happy birthday!");
        assertEquals("Happy birthday!", iPhone.getMessage());
    }
}