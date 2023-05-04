/**
 * @author <Kang Junsik - s3916884>
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.*;

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