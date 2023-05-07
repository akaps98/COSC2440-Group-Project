package controller;

import model.cart.ShoppingCart;
import model.cart.ShoppingCartList;
import model.coupon.Coupon;
import model.coupon.CouponList;
import model.data.DataInput;
import model.data.DataOutput;
import model.product.Product;
import model.product.ProductMap;

public class ShoppingCartController extends AppController {
    // ATTRIBUTES
    private ProductMap products;
    private ShoppingCartList carts;
    private CouponList coupons;

    // CONSTRUCTOR
    public ShoppingCartController() {
        super();
        products = db.getProducts();
        carts = db.getCarts();
        coupons = db.getCoupons();
    }

    // METHOD
    /**
     * Function 6: this method creates a new Shopping Cart for user
     * <p>
     * Conditions:
     * 1/ Scope: Cannot remove a shopping cart
     * 2/ CartID must be a valid number
     * 3/ User can add as much shopping cart as they want (there is no limitation in adding more shopping cart)
     * <p>
     * Action: create a new Shopping Cart and add to the Shopping Cart List of the system
     * Also, output a message to inform user whether the Shopping Cart is created successfully
     */
    public void createShoppingCart() {
        int cartID;
        do {
            System.out.print("Enter shopping cart ID: ");
            cartID = Integer.parseInt(input.nextLine());
            if (carts.containCart(cartID)) {
                System.out.println("""
                                    This cart is already existed on our system.
                                    Please select another cart ID1
                                    --------------------------------------------------""");
            }

        } while (carts.containCart(cartID));

        ShoppingCart c = new ShoppingCart(cartID);
        carts.addCart(c);
        System.out.printf("Successfully created cart %d!%n", c.getCartID());
    }

    /**
     * Function 7: this method adds a Product to a Shopping Cart
     * <p>
     * User inputs:
     * 1/ product name (must exist in the product list)
     * 2/ cartID (the ID is corresponding to the selected shopping cart)
     * <p>
     * Conditions:
     * 1/ There must be at least 1 shopping cart in the system
     * <p>
     * Action: add the Product to the Shopping Cart and output a message to the user accordingly to the return value
     *
     * @return boolean: boolean value states if the Product has been added to the cart successfully
     */
    public boolean addProductToCart() {
        boolean check = false; // boolean value to return

        // Check if there is any shopping cart existed in the shopping cart list
        if (carts.countCarts() == 0) {
            System.out.println("""
                                    No shopping cart found!
                                    Please create a shopping cart before adding the product.
                                    --------------------------------------------------""");
            return false;
        }

        // Display the available products in the console
        products.viewAvailableProducts();

        // Get input for product name and check if it existed
        String name;
        do {
            System.out.print("Enter product name to add to cart: ");
            name = input.nextLine();
            if (!products.containProduct(name)) {
                System.out.println("""
                                    This product name is not existed on our system.
                                    Please select another name!
                                    --------------------------------------------------""");
            }
        } while (!products.containProduct(name));

        // Display the available carts in the console
        carts.viewCarts();

        // Get input for shopping cart ID and check if the cart existed
        int cartID;
        do {
            System.out.print("Select the shopping cart # to add product: ");
            cartID = Integer.parseInt(input.nextLine());
            if (!carts.containCart(cartID)) {
                System.out.println("""
                                    This cart is not existed on our system.
                                    Please select another cart ID1
                                    --------------------------------------------------""");
            }

        } while (!carts.containCart(cartID));
        ShoppingCart c = carts.getCart(cartID);

        // Get input for quantity to add and check if it is valid
        int quantity;
        do {
            System.out.print("Enter product quantity to add: ");
            quantity = Integer.parseInt(input.nextLine());
            if (quantity < 0) {
                System.out.println("""
                                    You cannot input negative number.
                                    Please input a valid quantity again!
                                    --------------------------------------------------""");
            } else {
                Product addedProduct = products.getProduct(name);
                if (quantity > addedProduct.getQuantity()) {
                    System.out.println("""
                                    The quantity you want exceeds our stock for this product!"
                                    Please enter another quantity value!
                                    --------------------------------------------------""");
                }
            }
        } while (quantity < 0);

        // Add product to cart
        check = c.addItem(name, quantity, products);

        if (check) { // display a message whether the product is added successfully to the shopping cart
            System.out.printf("Successfully add %d %s to cart #%d!%n", quantity, name, c.getCartID());
        } else {
            System.out.printf("Cannot add %d %s to cart #%d. Please try again!%n", quantity, name, c.getCartID());
        }

        return check;
    }

    /**
     * Function 8: this method removes a Product from a Shopping Cart
     * <p>
     * User inputs:
     * 1/ product name (must exist in the product list)
     * 2/ cartID (the ID is corresponding to the selected shopping cart)
     * <p>
     * Conditions:
     * 1/ There must be at least 1 shopping cart in the system
     * <p>
     * Action: remove a product from a shopping cart and output a message to the user accordingly to the return value
     *
     * @return boolean: boolean value states if the Product has been removed successfully
     */
    public boolean removeProductFromCart() {
        boolean check = false; // boolean value to return

        // Check if there is any shopping cart existed in the shopping cart list
        if (carts.countCarts() == 0) {
            System.out.println("""
                                    No shopping cart found!
                                    Please create a shopping cart before removing the product.
                                    --------------------------------------------------""");
            return false;
        }

        // Display all the existing carts in the system
        carts.viewCarts();

        // Get input for shopping cart ID and check if the cart existed
        int cartID;
        do {
            System.out.print("Select the shopping cart # to remove product: ");
            cartID = Integer.parseInt(input.nextLine());
            if (!carts.containCart(cartID)) {
                System.out.println("""
                                    This cart is not existed on our system.
                                    Please select another cart ID1
                                    --------------------------------------------------""");
            }

        } while (!carts.containCart(cartID));
        ShoppingCart c = carts.getCart(cartID);

        // Display the available products in the cart
        System.out.println(c.toString());

        // Get input for product name and check if it existed in the system and the cart
        String name;
        do {
            System.out.print("Enter the product name to remove from cart: ");
            name = input.nextLine();
            if (!c.containItem(name)) {
                System.out.println("""
                                    This product name is not existed on this cart.
                                    Please select another name!
                                    --------------------------------------------------""");
            }
        } while (!c.containItem(name));

        // Get input for quantity to remove and check if it is valid
        int quantity;
        do {
            System.out.print("Enter product quantity to remove: ");
            quantity = Integer.parseInt(input.nextLine());
            if (quantity < 0) {
                System.out.println("""
                                    You cannot input negative number.
                                    Please input a valid quantity again!
                                    --------------------------------------------------""");
            }
        } while (quantity < 0);

        // Remove product from cart
        check = c.removeItem(name, quantity, products);

        if (check) { // display a message whether the product is added successfully to the shopping cart
            System.out.printf("Successfully remove %d %s from cart %d!%n", quantity, name, c.getCartID());
        } else {
            System.out.printf("Cannot remove %s from cart %d. Please try again!%n", name, c.getCartID());
        }
        return check;
    }

    /**
     * Function 9: this method is used to display a cart amount of the shopping cart
     * <p>
     * User input:
     * 1/ cartID (the id is corresponding to the selected shopping cart)
     * <p>
     * Action: calculate the cart amount of the shopping cart and output the number to the console
     *
     * @return boolean: boolean value states if the cart amount has been calculated successfully
     */
    public boolean displayCartDetail() {
        // Check if there is any shopping cart existed
        if (carts.countCarts() == 0) {
            System.out.println("No shopping cart found! Please create a shopping cart first!");
            return false;
        }

        // Display all available cart in the system
        carts.viewCarts();

        // Get input for shopping cart ID and check if the cart existed
        int cartID;
        do {
            System.out.print("Select the shopping cart # to view further detail: ");
            cartID = Integer.parseInt(input.nextLine());
            if (!carts.containCart(cartID)) {
                System.out.println("""
                                    This cart is not existed on our system.
                                    Please select another cart ID1
                                    --------------------------------------------------""");
            }

        } while (!carts.containCart(cartID));
        ShoppingCart c = carts.getCart(cartID);

        // Display cart information
        c.viewCartInfo();
        return true;
    }

    /**
     * This method is used to edit the shopping cart detail
     *
     */
    public boolean editCart() {
        boolean check = false; // boolean value to return
        carts.viewCarts(); // display all carts for user to view

        // Get input for shopping cart ID and check if the cart existed
        int cartID;
        do {
            System.out.print("Select the shopping cart # to edit: ");
            cartID = Integer.parseInt(input.nextLine());
            if (!carts.containCart(cartID)) {
                System.out.println("""
                                    This cart is not existed on our system.
                                    Please select another cart ID1
                                    --------------------------------------------------""");
            }

        } while (!carts.containCart(cartID));
        ShoppingCart modifiedCart = carts.getCart(cartID);

        // Create a loop until user enter a valid choice
        int option = -1;
        while (option != 4) {
            System.out.print("""
                    ==================================================
                    1. Change applied coupon
                    2. Update gift message for a product
                    3. Make cart empty
                    4. Go Back
                    ==================================================
                    Enter your choice:\040""");
            option = Integer.parseInt(input.nextLine());
            switch (option) { // validate the choice from user
                // Update cart applied coupon
                case 1:
                    // Get input for new couponID and check if it is valid
                    String newCouponID;
                    do {
                        System.out.print("Enter the updated coupon ID for this cart: ");
                        newCouponID = input.nextLine();
                    } while (!Coupon.checkCouponExisted(newCouponID, coupons));
                    modifiedCart.setAppliedCouponID(newCouponID);
                    break;
                // Update gift message for a product
                case 2:
                    // view all items gift message in the cart
                    modifiedCart.viewCartGiftMessages();

                    // Get input for product name and check if it existed
                    String name;
                    do {
                        System.out.print("Enter the product name to set gift message: ");
                        name = input.nextLine();
                    } while (!modifiedCart.containItem(name));

                    //
                    if (modifiedCart.containGiftMessage(name)) {
                        System.out.println("This product has already contain a gift message: " + modifiedCart.getMessage(name));
                        while (true) {
                            System.out.println("""
                        Do you want to set a new gift message for this product in this cart?
                        1. Yes
                        2. No""");
                            System.out.print("Enter your option: ");
                            option = Integer.parseInt(input.nextLine());
                            // Case 1: User agree to update the new gift message for this product
                            if (option == 1) {
                                // Get input for the new message
                                System.out.print("Enter the new gift message: ");
                                String newMessage = input.nextLine();
                                modifiedCart.setMessage(name,newMessage);
                                System.out.printf("Update new message - <%s> successfully to new product - <%s>%n", newMessage, name);
                                return true;
                            } else if (option == 2) { // Case 2: User do not agree to update the new gift message
                                System.out.println("Did not change the previous gift message for this product! Please try again.");
                                break;
                            } else {
                                System.out.println("""
                                    Invalid option. Please enter again!
                                    --------------------------------------------------""");
                            }
                        }
                    } else {
                        System.out.print("Enter the new gift message to set for this product: ");
                        String newMessage = input.nextLine();
                        modifiedCart.setMessage(name,newMessage);
                        System.out.printf("Update new message - <%s> successfully to new product - <%s>%n", newMessage, name);
                        return true;
                    }
                    break;
                // Make cart empty
                case 3:
                    if (modifiedCart.getCartItems() == null) {
                        System.out.println("The cart is already empty!");
                    } else {
                        modifiedCart.resetCart();
                        System.out.printf("Reset cart #%d successfully!%n",cartID);
                        return true;
                    }
                    break;
                // Return to the primary menu
                case 4:
                    break;
                // User input invalid choice --> reenter the choice
                default:
                    System.out.println("""
                                    Invalid option. Please enter again!
                                    --------------------------------------------------""");
            }
        }
        // Output the message accordingly if the product is successfully modified
        if (option == 1 || option == 2 || option == 3 || option == 4) {
            check = true;
        }

        if (check) { // display a message if success
            System.out.printf("Successfully modified cart #%d!%n", cartID);
        } else {
            System.out.println("Did not modify cart!");
        }
        return check;
    }

    /**
     * Function 11: this method is used to display all the Shopping Carts in the system
     *
     * Condition: the carts will be displayed in ascending order based on the cart's weight
     *
     * Action: display all the shopping carts based on the condition
     */
    public void displayAllCartByWeight() {
        carts.viewCartsAfterSorted();
    }

    /**
     *  Function 12: this method is used to print the receipt of a shopping cart
     */
    public void printReceipt() {
        // Display all available cart in the system
        carts.viewCarts();

        // Get input for shopping cart ID and check if the cart existed
        int cartID;
        do {
            System.out.print("Select the shopping cart # to print receipt: ");
            cartID = Integer.parseInt(input.nextLine());
            if (!carts.containCart(cartID)) {
                System.out.println("""
                                    This cart is not existed on our system.
                                    Please select another cart ID1
                                    --------------------------------------------------""");
            }

        } while (!carts.containCart(cartID));

        // Print the receipt for the selected shopping cart
        DataOutput dOut = DataOutput.getInstance();
        dOut.writeReceipt(cartID);

        // Also display the receipt in the console
        DataInput dIn = DataInput.getInstance();
        dIn.readReceipt(cartID);
    }
}
