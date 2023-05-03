package model;

import java.util.ArrayList;
import java.util.List;

public class CouponList {
    private List<Coupon> coupons;

    // CONSTRUCTOR
    public CouponList() {
        coupons = new ArrayList<>();
    }

    // METHODS
    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    public void removeCoupon(Coupon coupon) {
        coupons.remove(coupon);
    }

    public int countCoupon() {
        return coupons.size();
    }

    public Coupon getCoupon(String couponID) {
        for (Coupon coupon : coupons) {
            if (coupon.getCouponID().equals(couponID)) {
                return coupon;
            }
        }
        return null;
    }

    public boolean contains(String couponID) {
        for (Coupon coupon : coupons) {
            if (coupon.getCouponID().equals(couponID)) {
                return true;
            }
        }
        return false;
    }

    public StringBuilder viewAllCoupons() {
        StringBuilder allCoupons = new StringBuilder();
        allCoupons.append("[");
        for (Coupon coupon : coupons) {
            allCoupons.append(coupon.toString() + ", ");
        }
        allCoupons.append("]");
        System.out.println(String.format("Number of Coupons: %d", countCoupon()));
        System.out.println(allCoupons);
        return allCoupons;
    }
}
