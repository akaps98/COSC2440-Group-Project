package model;

public class Coupon {
    protected String couponID;
    protected String type;
    protected Product product;
    protected double couponValue;

    public Coupon(String couponID, String type, Product product, double couponValue) {
        this.couponID = couponID;
        this.type = type;
        this.product = product;
        this.couponValue = couponValue;
    }
}
