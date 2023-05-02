package model;

public class Coupon {
    protected String couponID;
    protected String type;
    protected double couponValue;
    protected String productName;

    // CONSTRUCTOR
    public Coupon(String couponID, String type, double couponValue, String productName) {
        this.couponID = couponID;
        this.type = type;
        this.couponValue = couponValue;
        this.productName = productName;
    }
}
