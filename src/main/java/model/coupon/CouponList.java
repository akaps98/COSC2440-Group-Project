package model.coupon;

import model.coupon.Coupon;

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

    public boolean contains(String id) {
        for (Coupon c : coupons) {
            if (c.getCouponID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void viewAllCoupons() {
        StringBuilder allCoupons = new StringBuilder();
        allCoupons.append("[");
        for (Coupon coupon : coupons) {
            allCoupons.append(coupon.toString()).append(", ");
        }
        allCoupons.append("]");
        System.out.printf("Number of Coupons: %d%n", countCoupon());
        System.out.println(allCoupons);
    }
}
