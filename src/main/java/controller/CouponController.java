package controller;

/**
 * desc
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.coupon.Coupon;
import model.coupon.CouponList;
import model.product.ProductMap;

public class CouponController extends AppController {
    // ATTRIBUTES
    private final ProductMap products;
    private final CouponList coupons;

    // CONSTRUCTOR
    public CouponController() {
        super();
        products = db.getProducts();
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
