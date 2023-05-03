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

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public StringBuilder viewAllCoupons() {
        StringBuilder allCoupons = new StringBuilder();
        allCoupons.append("[");
        for (Coupon coupon : coupons) {
            allCoupons.append(coupon.toString()).append(", ");
        }
        allCoupons.append("]");
        System.out.printf("Number of Coupons: %d%n", countCoupon());
        System.out.println(allCoupons);
        return allCoupons;
    }
}
