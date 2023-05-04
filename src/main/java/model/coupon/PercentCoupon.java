package model.coupon;

import model.coupon.Coupon;

public class PercentCoupon extends Coupon {
    // ATTRIBUTES
    private int couponValue;

    // CONSTRUCTOR
    public PercentCoupon(String couponID, String productName, int couponValue) {
        super(couponID, productName);
        this.couponValue = couponValue;
    }

    // GETTERS AND SETTERS
    public int getCouponValue() {return couponValue;}
    public void setCouponValue(int couponValue) {this.couponValue = couponValue;}

    // METHOD
    /**
     * This method print the details of a percent coupon
     *
     * @return String: string that contains the coupon information
     */
    @Override
    public String toString() {
        return String.format("PercentCoupon - <%s: %d%%, applied to: %s>",couponID,couponValue, productName) ;
    }
}
