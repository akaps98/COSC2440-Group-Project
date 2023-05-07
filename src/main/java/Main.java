/**
 * @author <Kang Junsik - s3916884>
 */

import database.ShoppingDB;
import model.data.DataInput;
import model.data.DataOutput;
import model.product.Tax;
import model.cart.ShoppingCart;
import model.cart.ShoppingCartList;
import model.coupon.Coupon;
import model.coupon.CouponList;
import model.product.Digital;
import model.product.Physical;
import model.product.Product;
import model.product.ProductMap;
import java.util.Scanner;
// this one is to run the Online Shopping Service

public class Main { // run the program
    // ATTRIBUTES
    ProductMap products;
    ShoppingCartList carts;
    CouponList coupons;
    Scanner input = new Scanner(System.in); // collect user inputs
    int choice = -1; // get option
    boolean check; // if the user successfully use a feature

    public Main() {
        ShoppingDB db = ShoppingDB.getInstance();
        products = db.getProducts();
        carts = db.getCarts();
        coupons = db.getCoupons();
    }

    /**
     * Function 1: this method is used to display the product(s) information in the product list
     * User input the choice to select the option for viewing products
     * <p>
     * Available options to view product:
     * 1/ View a product detail (all the information of that product)
     * 2/ View all products in the system (only display the product names)
     * 3/ View all products that are currently available to buy (only display the product names)
     * <p>
     * Action: Display the string that contains information accordingly to user's choice
     */
    public void displayProducts() {
        // Create a loop until user enter a valid choice
        int option = -1;
        while (option != 1 && option != 2 && option != 3 && option != 4) {
            System.out.print("""
                    ==================================================
                    1. View a product detail
                    2. View all products (product names)
                    3. View available products to buy (product names)
                    4. Go Back
                    ==================================================
                    Enter your choice:\040""");
            option = Integer.parseInt(input.nextLine());
            switch (option) { // validate the choice from user
                // View a product detail
                case 1:
                    // Get input for product name and check if it existed
                    String name;
                    do {
                        System.out.print("Enter product name to view: ");
                        name = input.nextLine();
                        if (!products.containProduct(name)) {
                            System.out.println("""
                                    This product name is not existed on our system.
                                    Please select another name!
                                    --------------------------------------------------""");
                        }
                    } while (!products.containProduct(name));

                    // Found a product, proceeding to get the product detail
                    Product p = products.getProduct(name);
                    if (p instanceof Physical) { // Check for product type to call the appropriate type casting getProductDetail() method
                        System.out.println(((Physical) p).getProductDetail());
                    } else {
                        System.out.println(((Digital) p).getProductDetail());
                    }
                    break;
                // View every product that existed in the product list
                case 2:
                    products.viewAllProducts();
                    break;
                // View only the products that have the quantity which is currently available to add to cart (quantity > 0)
                case 3:
                    products.viewAvailableProducts();
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

    }

    /**
     * Function 2: this method is use to create a new Product and added to the system database (product list)
     * <p>
     * User inputs:
     * 1/ name
     * 2/ description
     * 3/ quantityAvailable
     * 4/ price
     * 5/ weight (for Physical Product only)
     * <p>
     * Action: create a Physical or Digital Product and add them to the product list
     * Also, output a message to the user accordingly to the return value
     *
     * @return boolean: boolean value states if the Product has been created successfully
     */
    public boolean createProduct() {
        check = false; // boolean value to return

        // Get input for product name and check if it existed
        String name;
        do {
            System.out.print("Enter new product name: ");
            name = input.nextLine();
            if (products.containProduct(name)) {
                System.out.println("""
                                    This product name is already existed on our system.
                                    Please select another name!
                                    --------------------------------------------------""");
            }
        } while (products.containProduct(name));

        // Get input for quantity and check if it is valid
        int quantity;
        do {
            System.out.print("Enter product available quantity: ");
            quantity = Integer.parseInt(input.nextLine());
            if (quantity < 0) {
                System.out.println("""
                                    You cannot input negative number.
                                    Please input a valid quantity again!
                                    --------------------------------------------------""");
            }
        } while (quantity < 0);

        // Get all the other inputs (description, price)
        System.out.print("Enter product description: ");
        String description = input.nextLine();

        double price;
        do {
            System.out.print("Enter product price($): ");
            price = Double.parseDouble(input.nextLine());
            if (price < 0) {
                System.out.println("""
                                    You cannot input negative number.
                                    Please input a valid price again!
                                    --------------------------------------------------""");
            }
        } while (price < 0);

        // Get input for product type and provide appropriate type casting
        while (!check) {
            System.out.print("""
                    What is the type of the product?
                    ==================================================
                    1. Digital
                    2. Physical
                    ==================================================
                    Enter your choice:\040""");
            int productTypeOption = Integer.parseInt(input.nextLine());
            if (productTypeOption == 1) {
                // Create a new Digital Product and add to system
                Product addedProduct = new Digital(name, description, quantity, price);
                products.addProduct(addedProduct);
                check = true;
            } else if (productTypeOption == 2) { // Create Physical Product
                double weight;
                do {
                    System.out.print("\nEnter product weight(g): ");
                    weight = Double.parseDouble(input.nextLine());
                    if (weight < 0) {
                        System.out.println("""
                                    You cannot input negative number.
                                    Please input a valid weight again!
                                    --------------------------------------------------""");
                    }
                } while (weight < 0);

                // Create a new Physical Product and add to system
                Physical addedProduct = new Physical(name, description, quantity, price, weight);
                products.addProduct(addedProduct);
                check = true;
            } else {
                System.out.println("""
                                    Invalid option. Please enter again!
                                    --------------------------------------------------""");
            }
        }

        // Display corresponding message whether the product is created and added to the system
        if (check) { // display a message if success
            System.out.println(String.format("Successfully create product: %s, available quantity: %d!", name, quantity));
        } else {
            System.out.println("Failed to create the product, please try again!");
        }
        return check;
    }

    /**
     * Function 3: this method is used to update the information about the product
     * User will input the choice based on the sub-menu to choose the option for modifying information
     * <p>
     * Available options to change:
     * 1/ description
     * 2/ quantityAvailable
     * 3/ price
     * 4/ set as gift
     * <p>
     * Condition:
     * 1/ User cannot change the product name
     * 2/ User cannot convert back a gift to a normal product (but can choose this option again to set the new message for gift products)
     * <p>
     * Action: modify the variable to the value that the user want to update and output a message to the user accordingly to the return value
     *
     * @return boolean: boolean value states if the Product information has been modified successfully
     */
    public boolean editProduct() {
        check = false; // boolean value to return
        products.viewAllProducts(); // display all products for user to view

        // Get input for product name and check if it existed
        String name;
        do {
            System.out.print("Enter product name to edit: ");
            name = input.nextLine();
            if (!products.containProduct(name)) {
                System.out.println("""
                                    This product name is not existed on our system.
                                    Please select another name!
                                    --------------------------------------------------""");
            }
        } while (!products.containProduct(name));

        Product modifiedProduct = products.getProduct(name);
        // Create a loop until user enter a valid choice
        int option = -1;
        while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6) {
            System.out.print("""
                    ==================================================
                    1. Change description
                    2. Update available quantity
                    3. Adjust price
                    4. Update weight (physical product only)
                    5. Change tax type
                    6. Go Back
                    ==================================================
                    Enter your choice:\040""");
            option = Integer.parseInt(input.nextLine());
            switch (option) { // validate the choice from user
                // Update product description
                case 1:
                    System.out.print("\nEnter new product description: ");
                    String newDescription = input.nextLine();
                    modifiedProduct.setDescription(newDescription);
                    break;
                // Update available quantity
                case 2:
                    // Get input for quantity and check if it is valid
                    int newQuantity;
                    do {
                        System.out.print("Enter the updated product available quantity in stock: ");
                        newQuantity = Integer.parseInt(input.nextLine());
                        if (newQuantity < 0) {
                            System.out.println("""
                                    You cannot input negative number.
                                    Please input a valid quantity again!
                                    --------------------------------------------------""");
                        }
                    } while (newQuantity < 0);
                    modifiedProduct.setQuantity(newQuantity);
                    break;
                // Update product price
                case 3:
                    double newPrice;
                    do {
                        System.out.print("Enter the adjusted product price($): ");
                        newPrice = Double.parseDouble(input.nextLine());
                        if (newPrice < 0) {
                            System.out.println("""
                                    You cannot input negative number.
                                    Please input a valid price again!
                                    --------------------------------------------------""");
                        }
                    } while (newPrice < 0);
                    modifiedProduct.setPrice(newPrice);
                    break;
                case 4:
                    // Check if product is a Physical Product
                    if (modifiedProduct instanceof Physical) {
                        // Set new weight
                        double newWeight;
                        do {
                            System.out.print("\nEnter the adjusted product weight(g): ");
                            newWeight = Double.parseDouble(input.nextLine());
                            if (newWeight < 0) {
                                System.out.println("""
                                    You cannot input negative number.
                                    Please input a valid weight again!
                                    --------------------------------------------------""");
                            }
                        } while (newWeight < 0);
                        ((Physical) modifiedProduct).setWeight(newWeight);
                    } else {
                        System.out.println("""
                                    This is a physical product and cannot adjust the weight.
                                    Please select other options!
                                    --------------------------------------------------""");
                    }
                    break;
                case 5:
                    // Get input for tax type
                    while (true) {
                        System.out.println("""
                                Tax Type:
                                ==================================================
                                1. Free Tax
                                2. Normal Tax
                                3. Luxury Tax
                                ==================================================
                                Enter your choice:\040""");
                        int taxOption = Integer.parseInt(input.nextLine());
                        // Update corresponding tax type
                        if (taxOption == 1) {
                            modifiedProduct.setTaxType("freeTax");
                            break;
                        } else if (taxOption == 2) {
                            modifiedProduct.setTaxType("normalTax");
                            break;
                        } else if (taxOption == 3) {
                            modifiedProduct.setTaxType("luxuryTax");
                            break;
                        } else {
                            System.out.println("""
                                    Invalid option. Please enter again!
                                    --------------------------------------------------""");
                        }
                    }
                    break;
                // Return to the primary menu
                case 6:
                    break;
                // User input invalid choice --> reenter the choice
                default:
                    System.out.println("""
                                    Invalid option. Please enter again!
                                    --------------------------------------------------""");
            }
        }
        // Output the message accordingly if the product is successfully modifidied
        if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5) {
            check = true;
        }

        if (check) { // display a message if success
            System.out.println(String.format("Successfully modified product: %s!", name));
        } else {
            System.out.println("Did not modify product!");
        }
        return check;
    }

    /**
     * Function 4: this method is use to create a new Shopping Cart for user
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
        System.out.println(String.format("Successfully created cart %d!", c.getCartID()));
    }

    /**
     * Function 5: this method is used to add a Product to a Shopping Cart
     * <p>
     * User inputs:
     * 1/ product name (must existed in the product list)
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
        check = false; // boolean value to return

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

        if (check == true) { // display a message whether the product is added successfully to the shopping cart
            System.out.println(String.format("Successfully add %d %s to cart #%d!", quantity, name, c.getCartID()));
        } else {
            System.out.println(String.format("Cannot add %d %s to cart #%d. Please try again!", quantity, name, c.getCartID()));
        }

        return check;
    }

    /**
     * Function 6: this method is used to remove a Product from a Shopping Cart
     * <p>
     * User inputs:
     * 1/ product name (must existed in the product list)
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
        check = false; // boolean value to return

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
        c.toString();

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

        if (check == true) { // display a message whether the product is added successfully to the shopping cart
            System.out.println(String.format("Successfully remove %d %s from cart %d!", quantity, name, c.getCartID()));
        } else {
            System.out.println(String.format("Cannot remove %s from cart %d. Please try again!", name, c.getCartID()));
        }
        return check;
    }

    /**
     * Function 7: this method is used to display all the Shopping Carts in the system
     * <p>
     * Condition: the carts will be displayed in ascending order based on the cart's weight
     * <p>
     * Action: display all the shopping carts based on the condition
     */
    public void displayAllCoupons() {
        coupons.viewAllCoupons();
    }

    /**
     * Function 8: This method will apply a coupon to an existing product in the system
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

            int option = -1;
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

                    System.out.println(String.format("Apply coupon - <%s> successfully to new product - <$s>", couponID, name));
                    return true;
                } else if (option == 2) { // Case 2: User do not agree to update the new product to apply the coupon
                    System.out.println("Did not apply this coupon to a new product! Please try again.");
                    break;
                } else {
                    System.out.println("""
                                    Invalid option. Please enter again!
                                    --------------------------------------------------""");
                }
            }
        } else {
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

            System.out.println(String.format("Apply coupon - <%s> successfully to new product - <$s>", couponID, name));
            return true;
        }
        return false;
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
        check = false; // boolean value to return
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
                        int newOption = -1;
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
                                System.out.println(String.format("Update new message - <%s> successfully to new product - <$s>", newMessage, name));
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
                        System.out.println(String.format("Update new message - <%s> successfully to new product - <$s>", newMessage, name));
                        return true;
                    }
                    break;
                // Make cart empty
                case 3:
                    if (modifiedCart.getCartItems() == null) {
                        System.out.println("The cart is already empty!");
                    } else {
                        modifiedCart.resetCart();
                        System.out.println(String.format("Reset cart #%d successfully!",cartID));
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
        // Output the message accordingly if the product is successfully modifidied
        if (option == 1 || option == 2 || option == 3 || option == 4) {
            check = true;
        }

        if (check) { // display a message if success
            System.out.println(String.format("Successfully modified cart #%d!", cartID));
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

    public static void generateData() {
        DataInput dIn = DataInput.getInstance();
        dIn.readProductFile();
        dIn.readCartFile();
    }

    /**
     * Display the console application primary menu (menu for user to select the feature)
     */
    public void displayMenu() {
        System.out.print("""
                --------------------------------------------------
                         *** ONLINE SHOPPING SERVICE  ***
                --------------------------------------------------
                1.  View product(s) detail
                2.  Create new product
                3.  Edit product
                4.  Create a new shopping cart
                5.  Add product(s) to shopping cart
                6.  Remove product(s) from shopping cart
                7.  View all coupons
                8.  Apply coupon to product
                9.  View shopping cart(s) detail
                10. Edit shopping cart
                11. Display all shopping cart
                (sorted based on the cart's weight)
                12. Print shopping cart receipt
                13. Exit
                --------------------------------------------------
                Select the option you want:\040""");
    }

    /**
     * This method is called in Main class to run the whole program
     */
    public void run() {
        int choice = -1;
        boolean defaultCheck = false; // Use to check if the default method have been called yet

        // --------------MAIN PROGRAM--------------
        System.out.println("\nWelcome to the online shopping service application!");
        while (choice != 13) {
            displayMenu();
            choice = Integer.parseInt(input.nextLine()); // get the user's input
            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    createProduct();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    createShoppingCart();
                    break;
                case 5:
                    addProductToCart();
                    break;
                case 6:
                    removeProductFromCart();
                    break;
                case 7:
                    displayAllCoupons();
                    break;
                case 8:
                    applyCoupon();
                    break;
                case 9:
                    displayCartDetail();
                    break;
                case 10:
                    editCart();
                    break;
                case 11:
                    displayAllCartByWeight();
                    break;
                case 12:
                    printReceipt();
                    break;
                case 13:
                    System.out.println("\nThank you for using our service! We hope to see you again ^^");
                    break;
                default:
                    System.out.println("""
                                    Invalid choice. Please enter again!
                                    --------------------------------------------------""");
                    displayMenu();
                    choice = Integer.parseInt(input.nextLine());
            }
        }
    }

    public static void main(String[] args) {
        // By default, when the system run, the program will execute the database files: products.txt + carts.txt
        Main.generateData();
        Main program = new Main();
        program.run();
    }
}