/**
 * @author <Kang Junsik - s3916884>
 */

import model.PercentCoupon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercentCouponTest {
    @Test
    void testToString() {
        PercentCoupon pc = new PercentCoupon("p0937a", "computer", 3);
        String expectOutput = "PercentCoupon - <p0937a: 3%, applied to: computer>";

        // It prints out the type of coupon, then ID, value(with a symbol %) and product name in a row.
        assertEquals(expectOutput, pc.toString());
    }
}