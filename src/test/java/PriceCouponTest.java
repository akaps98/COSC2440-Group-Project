/**
 * @author <Kang Junsik - s3916884>
 */

import model.coupon.PriceCoupon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceCouponTest {
    @Test
    void testToString() {
        PriceCoupon pc = new PriceCoupon("d0937a", "Netflix Membership", 10.25);
        String expectOutput = "PriceCoupon - <d0937a: $10.25, applied to: Netflix Membership>";

        // It prints out the type of coupon, then ID, value(with a symbol $) and product name in a row.
        assertEquals(expectOutput, pc.toString());
    }
}