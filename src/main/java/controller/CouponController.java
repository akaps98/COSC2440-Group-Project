package controller;

/**
 * This class is the controller for all the coupons functions (features)
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.coupon.CouponList;

public class CouponController extends AppController {
    // ATTRIBUTES
    private final CouponList coupons; // Store all the coupons information from the database

    // CONSTRUCTOR
    public CouponController() {
        super();
        coupons = db.getCoupons();

    }

    // METHOD
    /**
     * Function 4: this method is used to display all the coupons in the system
     *
     * Action: display all the coupons that are existed in the system
     */
    public void displayAllCoupons() {
        coupons.viewAllCoupons();
    }

}
