package model.coupon;

/**
 * The class contains information about the list which stores all the couponList information
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import java.util.ArrayList;
import java.util.List;

public class CouponList {
    // ATTRIBUTES
    private final List<Coupon> couponList;

    // CONSTRUCTOR
    public CouponList() {
        couponList = new ArrayList<>();
    }

    // GETTERS & SETTERS
    public List<Coupon> getCoupons() {
        return couponList;
    }

    // METHODS

    /**
     * Add a coupon to the list
     * @param coupon: the coupon Object to be added
     */
    public void addCoupon(Coupon coupon) {
        couponList.add(coupon);
    }

    /**
     * Remove a coupon fromthe list
     * @param coupon: the coupon Object to be removed
     */
    public void removeCoupon(Coupon coupon) {
        couponList.remove(coupon);
    }

    /**
     * Count total number of coupons in the list
     * @return int: the total number of coupons
     */
    public int countCoupon() {
        return couponList.size();
    }

    /**
     * Retrieve a coupon from the list
     * @param couponID: the ID associated with the coupon object
     * @return Coupon: the object needed to be returned
     */
    public Coupon getCoupon(String couponID) {
        for (Coupon coupon : couponList) {
            if (coupon.getCouponID().equals(couponID)) {
                return coupon;
            }
        }
        return null;
    }

    /**
     * Check if the list contain a coupon with the input ID
     * @param id: the coupon ID
     * @return boolean: states if the coupon existed or not
     */
    public boolean contains(String id) {
        for (Coupon c : couponList) {
            if (c.getCouponID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Display all the coupons in the list
     */
    public void viewAllCoupons() {
        StringBuilder allcouponList = new StringBuilder();
        allcouponList.append("[\n");
        for (Coupon coupon : couponList) {
            allcouponList.append(coupon.toString()).append("\n");
        }
        allcouponList.append("]");
        System.out.printf("Number of couponList: %d%n", countCoupon());
        System.out.println(allcouponList);
    }
}
