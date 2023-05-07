/**
 * The class stores information about all the Products inside a Product List
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.coupon.PercentCoupon;
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