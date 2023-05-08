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

    // METHODS
    public void addCoupon(Coupon coupon) {
        couponList.add(coupon);
    }

    public void removeCoupon(Coupon coupon) {
        couponList.remove(coupon);
    }

    public int countCoupon() {
        return couponList.size();
    }

    public Coupon getCoupon(String couponID) {
        for (Coupon coupon : couponList) {
            if (coupon.getCouponID().equals(couponID)) {
                return coupon;
            }
        }
        return null;
    }

    public boolean contains(String id) {
        for (Coupon c : couponList) {
            if (c.getCouponID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Coupon> getCoupons() {
        return couponList;
    }

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
