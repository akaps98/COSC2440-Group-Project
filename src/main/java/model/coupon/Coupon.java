package model.coupon;

/**
 * The class stores information about Coupon
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import java.util.regex.Pattern;

public abstract class Coupon {
    // ATTRIBUTES
    protected String couponID;
    protected String productName;

    // CONSTRUCTOR
    public Coupon(String couponID, String productName) {
        this.couponID = couponID;
        this.productName = productName;
    }

    // GETTERS & SETTERS
    public String getCouponID() {return couponID;}

    public void setCouponID(String couponID) {this.couponID = couponID;}

    public String getProductName() {return productName;}

    public void setProductName(String productName) {this.productName = productName;}

    // METHOD
    /**
     * This method validate the couponID
     *
     * Conditions:
     * 1. Starts with "p" or "d"
     * 2. Ends with "a" or "b"
     * 3. 4 numbers in the middle must follow this format: 09__, the remaining 2 digits can be any number
     *
     * @param couponID: unique id of a coupon
     * @return boolean value: state if the id is appropriate or not
     */
    public static boolean validateID(String couponID) {
        return Pattern.matches("[pd]09\\d{2}[ab]", couponID);
    }

    public static boolean checkCouponExisted(String couponID, CouponList couponList) {
        if (couponList.contains(couponID)) {
            return true;
        }
        System.out.println("Coupon is not existed in the system. Please try again!");
        return false;
    }

    /**
     * This method identify the coupon type
     *
     * @return String: store the coupon type
     */
    public static String getType(String couponID) {
        if (couponID.charAt(5) == 'a') {
            return "price";
        }
        return "percent";
    }

    /* Abstract methods */
    @Override
    public abstract String toString();
}
