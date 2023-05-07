/**
 * The class is a test unit for Coupon class
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.coupon.Coupon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {
    @Test
    void testValidationID() {
        // Valid ID requirement:
        // 1. Starts with "p" or "d"
        // 2. Ends with "a" or "b"
        // 3. 4 numbers in the middle must follow this format: 09__, the remaining 2 digits can be any number

        Coupon cp1 = new Coupon("p0901a", "iPhone") { // valid ID
            @Override
            public String toString() {
                return null;
            }
        };

        Coupon cp2 = new Coupon("d0901b", "eBook") { // valid ID
            @Override
            public String toString() {
                return null;
            }
        };

        Coupon cp3 = new Coupon("h0901b", "eBook") { // invalid; it starts letter 'h' which is neither 'd' or 'b'.
            @Override
            public String toString() {
                return null;
            }
        };

        Coupon cp4 = new Coupon("d0701b", "eBook") { // invalid; it starts number 07 which is not 09.
            @Override
            public String toString() {
                return null;
            }
        };

        assertTrue(Coupon.validateID(cp1.getCouponID())); // valid
        assertTrue(Coupon.validateID(cp2.getCouponID())); // valid
        assertFalse(Coupon.validateID(cp3.getCouponID())); // invalid
        assertFalse(Coupon.validateID(cp4.getCouponID())); // invalid
    }

    @Test
    void testGetType() {
        Coupon cp1 = new Coupon("p0901a", "iPhone") { // last letter is 'a', so price type
            @Override
            public String toString() {
                return null;
            }
        };

        Coupon cp2 = new Coupon("d0901b", "eBook") { // last letter is 'b', so percentage type
            @Override
            public String toString() {
                return null;
            }
        };

        assertEquals("price", Coupon.getType(cp1.getCouponID())); // price
        assertEquals("percent", Coupon.getType(cp2.getCouponID())); // price
    }
}