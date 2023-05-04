package model;

public class PriceCoupon extends Coupon {
    // ATTRIBUTES
    private double couponValue;

    // CONSTRUCTOR
    public PriceCoupon(String couponID, String productName, double couponValue) {
        super(couponID, productName);
        this.couponValue = couponValue;
    }

    // GETTERS AND SETTERS
    public double getCouponValue() {return couponValue;}
    public void setCouponValue(double couponValue) {this.couponValue = couponValue;}

    // METHOD
    /**
     * This method print the details of a price coupon
     *
     * @return String: string that contains the coupon information
     */
    @Override
    public String toString() {
        return String.format("PriceCoupon - <%s: $%,.2f, applied to: %s>",couponID,couponValue,productName) ;
    }
}
