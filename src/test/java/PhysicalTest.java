/**
 * @author <Kang Junsik - s3916884>
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.*;

class PhysicalTest {

    @Test
    void testPrintOut() {
        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        String expectOutput = "PHYSICAL - <iPhone>";
        assertEquals(expectOutput, iPhone.toString());
    }

    @Test
    void testGetMessage() { // test setMessage & getMessage together
        Physical iPhone = new Physical("iPhone", "Cool", 20, 200, 5);
        iPhone.setMessage("Happy birthday!");
        assertEquals("Happy birthday!", iPhone.getMessage());
    }
}