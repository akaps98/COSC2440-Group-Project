package controller;

/**
 * desc
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.coupon.Coupon;
import model.coupon.CouponList;
import model.product.ProductMap;

public class CouponController extends AppController {
    // ATTRIBUTES
    private final ProductMap products;
    private final CouponList coupons;

    // CONSTRUCTOR
    public CouponController() {
        super();
        products = db.getProducts();
        coupons = db.getCoupons();

    }

    // METHOD
    /**
     * Function 4: this method is used to display all the coupons in the system
     *
     * Action: display all the coupons that are existed in the system
     */
    public void displayAllCoupons() {
        coupons.viewAllCoupons();
    }

    /**
     * Function 5: This method will apply a coupon to an existing product in the system
     * <p>
     * User input:
     * 1/ CouponID: the unique id of the selected coupon
     * 2/ Product Name: the unique name of the selected product
     *
     * @return boolean: state whether the coupon is applied successfully
     */
    public boolean applyCoupon() {
        // Display all available coupons in the system
        coupons.viewAllCoupons();

        // Get input for couponID and check if it existed in the system
        String couponID;
        do {
            System.out.print("Enter the couponID: ");
            couponID = input.nextLine();
        } while (!coupons.contains(couponID));
        Coupon usedCoupon = coupons.getCoupon(couponID);

        // Check the coupon status (if it is applied to a product already)
        if (usedCoupon.getProductName() != null) {
            System.out.println("The selected coupon has already applied to " + usedCoupon.getProductName());

            int option;
            while (true) {
                System.out.print("""
                        Do you want to apply this coupon to the new product instead?
                        ==================================================
                        1. Yes
                        2. No
                        ==================================================
                        Enter your choice:\040""");
                option = Integer.parseInt(input.nextLine());
                // Case 1: User agree to update the new product to apply the coupon
                if (option == 1) {
                    break;
                } else if (option == 2) { // Case 2: User do not agree to update the new product to apply the coupon
                    System.out.println("Did not apply this coupon to a new product! Please try again.");
                    return false;
                } else {
                    System.out.println("""
                            Invalid option. Please enter again!
                            --------------------------------------------------""");
                }
            }
        }
        // Display all available products in the system
        products.viewAllProducts();

        // Get input for product name and check if it existed in the system
        String name;
        do {
            System.out.print("Enter new product name to apply coupon: ");
            name = input.nextLine();
            if (!products.containProduct(name)) {
                System.out.println("""
                        This product name is not existed on our system.
                        Please select another name!
                        --------------------------------------------------""");
            }
        } while (!products.containProduct(name));

        // Apply the coupon to the selected product
        usedCoupon.setProductName(name);

        System.out.printf("Apply coupon - <%s> successfully to new product - <%s>%n", couponID, name);
        return true;
    }
}
