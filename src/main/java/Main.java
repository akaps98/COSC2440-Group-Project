/**
 * @author <Kang Junsik - s3916884>
 */

import database.ShoppingDB;
import model.cart.ShoppingCart;
import model.cart.ShoppingCartList;
import model.coupon.Coupon;
import model.coupon.CouponList;
import model.data.DataInput;
import model.data.DataOutput;
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
            System.out.println("----------------------------------------\n");
            System.out.println("      1. View a product detail");
            System.out.println("      2. View all products (product names)");
            System.out.println("      3. View available products to buy (product names)");
            System.out.println("      4. Go Back");
            System.out.println("\n----------------------------------------");
            System.out.print("Enter your choice: ");
            option = Integer.parseInt(input.nextLine());
            switch (option) { // validate the choice from user
                // View a product detail
                case 1:
                    // Get input for product name and check if it existed
                    String name;
                    do {
                        System.out.print("Enter product name to view: ");
                        name = input.nextLine();
                    } while (!products.contains(name));

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
                    System.out.println("Invalid option. Please enter again!" +
                            "\n-----------------------");
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
            System.out.print("Enter product name: ");
            name = input.nextLine();
        } while (!products.contains(name));

        // Get input for quantity and check if it is valid
        int quantity;
        do {
            System.out.print("Enter product available quantity: ");
            quantity = Integer.parseInt(input.nextLine());
        } while (!Product.checkQuantityIsValid(quantity));

        // Get all the other inputs (description, price)
        System.out.print("Enter product description: ");
        String description = input.nextLine();
        System.out.print("Enter product price($): ");
        double price = Double.parseDouble(input.nextLine());

        // Get input for product type and provide appropriate type casting
        while (!check) {
            System.out.println("""
                    What is the type of the product?
                    1. Digital
                    2. Physical
                    """);
            System.out.print("Enter your option: ");
            int productTypeOption = Integer.parseInt(input.nextLine());
            if (productTypeOption == 1) { // Create Digital Product
                Product addedProduct = new Digital(name, description, quantity, price);
                products.addProduct(addedProduct);
                check = true;
            } else if (productTypeOption == 2) { // Create Physical Product
                System.out.print("\nEnter product weight(g): ");
                double weight = Double.parseDouble(input.nextLine());
                Physical addedProduct = new Physical(name, description, quantity, price, weight);
                products.addProduct(addedProduct);
                check = true;
            } else {
                System.out.println("Invalid option. Please enter again!" +
                        "\n-----------------------");
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
            System.out.print("Enter product name: ");
            name = input.nextLine();
        } while (!products.contains(name));

        Product modifiedProduct = products.getProduct(name);
        // Create a loop until user enter a valid choice
        int option = -1;
        while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6 && option != 7) {
            System.out.println("""
                    ----------------------------------------
                    1. Change description
                    2. Update available quantity
                    3. Adjust price
                    4. Add gift message
                    5. Update weight (physical product only)
                    6. Change tax type
                    7. Go Back
                    """);
            System.out.print("Enter your choice: ");
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
                    } while (!Product.checkQuantityIsValid(newQuantity));
                    modifiedProduct.setQuantity(newQuantity);
                    break;
                // Update product price
                case 3:
                    System.out.print("\nEnter the adjusted product price: ");
                    double newPrice = Double.parseDouble(input.nextLine());
                    modifiedProduct.setPrice(newPrice);
                    break;
                // Update product as a gift
                case 4:
                    System.out.print("\nEnter the message you want to deliver for this gift: ");
                    String giftMsg = input.nextLine();
                    modifiedProduct.setMessage(giftMsg);
                    break;
                case 5:
                    // Check if product is a Physical Product
                    if (modifiedProduct instanceof Physical) {
                        // Set new weight
                        System.out.print("\nEnter the adjusted weight: ");
                        double newWeight = Double.parseDouble(input.nextLine());
                        ((Physical) modifiedProduct).setWeight(newWeight);
                    } else {
                        System.out.println("This is a physical product and cannot adjust the weight. Please select other options!" +
                                "\n-----------------------");
                    }
                    break;
                case 6:
                    // Get input for tax type
                    while (!check) {
                        System.out.println("""
                                Tax Type:
                                1. Free Tax
                                2. Normal Tax
                                3. Luxury Tax
                                """);
                        System.out.print("Enter your option: ");
                        int taxOption = Integer.parseInt(input.nextLine());
                        // Update corresponding tax type
                        if (taxOption == 1) {
                            modifiedProduct.setTaxType("freeTax");
                        } else if (taxOption == 2) {
                            modifiedProduct.setTaxType("normalTax");
                        } else if (taxOption == 3) {
                            modifiedProduct.setTaxType("luxuryTax");
                        } else {
                            System.out.println("Invalid option. Please enter again!" +
                                    "\n-----------------------");
                        }
                    }
                    break;
                // Return to the primary menu
                case 7:
                    break;
                // User input invalid choice --> reenter the choice
                default:
                    System.out.println("Invalid option. Please enter again!" +
                            "\n-----------------------");
            }
        }
        // Output the message accordingly if the product is successfully modifidied
        if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5 || option == 6) {
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
        } while (ShoppingCart.checkCartExisted(cartID, carts));

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
            System.out.println("No shopping cart found! Please create a shopping cart before adding the product.");
            return false;
        }

        // Display the available products in the console
        products.viewAvailableProducts();

        // Get input for product name and check if it existed
        String name;
        do {
            System.out.print("Enter the product name to add to cart: ");
            name = input.nextLine();
        } while (!products.contains(name));

        // Display the available carts in the console
        carts.viewCarts();

        // Get input for shopping cart ID and check if the cart existed
        int cartID;
        do {
            System.out.print("Select the shopping cart # to add product: ");
            cartID = Integer.parseInt(input.nextLine());
        } while (ShoppingCart.checkCartExisted(cartID, carts));
        ShoppingCart c = carts.getCart(cartID);

        // Get input for quantity to add and check if it is valid
        int quantity;
        do {
            System.out.print("Enter the product quantity to add: ");
            quantity = Integer.parseInt(input.nextLine());
        } while (!Product.checkQuantityIsValid(quantity));

        // Add product to cart
        check = c.addItem(name, quantity, products);

        if (check == true) { // display a message whether the product is added successfully to the shopping cart
            System.out.println(String.format("Successfully add %s to cart #%d!", name, c.getCartID()));
        } else {
            System.out.println(String.format("Cannot add %s to cart #%d. Please try again!", name, c.getCartID()));
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
            System.out.println("No shopping cart found! Please create a shopping cart before adding the product.");
            return false;
        }

        // Display all the existing carts in the system
        carts.viewCarts();

        // Get input for shopping cart ID and check if the cart existed
        int cartID;
        do {
            System.out.print("Select the shopping cart # to remove product: ");
            cartID = Integer.parseInt(input.nextLine());
        } while (ShoppingCart.checkCartExisted(cartID, carts));
        ShoppingCart c = carts.getCart(cartID);

        // Display the available products in the cart
        c.toString();

        // Get input for product name and check if it existed in the system and the cart
        String name;
        do {
            System.out.print("Enter the product name to remove from cart: ");
            name = input.nextLine();
        } while (!products.contains(name) || !c.containItem(name));

        // Get input for quantity to add and check if it is valid
        int quantity;
        do {
            System.out.print("Enter the product quantity to add: ");
            quantity = Integer.parseInt(input.nextLine());
        } while (!Product.checkQuantityIsValid(quantity));

        // Remove product from cart
        check = c.removeItem(name, quantity, products);

        if (check == true) { // display a message whether the product is added successfully to the shopping cart
            System.out.println(String.format("Successfully remove %s from cart %d!", name, c.getCartID()));
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
                System.out.println("""
                        Do you want to apply this coupon to the new product instead?
                        1. Yes
                        2. No
                        """);
                System.out.print("Enter your option: ");
                option = Integer.parseInt(input.nextLine());
                // Case 1: User agree to update the new product to apply the coupon
                if (option == 1) {
                    // Display all available products in the system
                    products.viewAllProducts();

                    // Get input for product name and check if it existed in the system
                    String name;
                    do {
                        System.out.print("Enter the product name to apply coupon: ");
                        name = input.nextLine();
                    } while (!products.contains(name));

                    // Apply the coupon to the selected product
                    usedCoupon.setProductName(name);

                    System.out.println(String.format("Apply coupon - <%s> successfully to new product - <$s>", couponID, name));
                    return true;
                } else if (option == 2) { // Case 2: User do not agree to update the new product to apply the coupon
                    System.out.println("Did not apply this coupon to a new product! Please try again.");
                    break;
                } else {
                    System.out.println("Invalid option. Please enter again!" +
                            "\n-----------------------");
                }
            }
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
    public boolean displayCartAmount() {
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
            System.out.print("Select the shopping cart # to remove product: ");
            cartID = Integer.parseInt(input.nextLine());
        } while (ShoppingCart.checkCartExisted(cartID, carts));
        ShoppingCart c = carts.getCart(cartID);

        // Calculate the cart amount and display in the console
        System.out.println(String.format("Shopping Cart #%d's cart amount: %,.2f", c.getCartID(), c.cartAmount(products)));

        return true;
    }

    /**
     * Function 10: this method is used to display all the Shopping Carts in the system
     *
     * Condition: the carts will be displayed in ascending order based on the cart's weight
     *
     * Action: display all the shopping carts based on the condition
     */
    public void displayAllCartByWeight() {
        carts.viewCartsAfterSorted();
    }

    /**
     *  Function 11: this method is used to print the receipt of a shopping cart
     */
    public void printReceipt() {
        // Display all available cart in the system
        carts.viewCarts();

        // Get input for shopping cart ID and check if the cart existed
        int cartID;
        do {
            System.out.print("Select the shopping cart # to remove product: ");
            cartID = Integer.parseInt(input.nextLine());
        } while (ShoppingCart.checkCartExisted(cartID, carts));
        ShoppingCart c = carts.getCart(cartID);

        // Print the receipt for the selected shopping cart
        DataOutput dOut = new DataOutput();
        dOut.writeReceipt(c);

        // Also display the receipt in the console
        DataInput dIn = new DataInput();
        dIn.readReceipt(cartID);
    }

    public static void generateData() {
        DataInput dIn = new DataInput();
        dIn.readProductFile();
        dIn.readCartFile();
    }

    /**
     * Display the console application primary menu (menu for user to select the feature)
     */
    public void displayMenu() {
        System.out.println("""
                ----------------------------------------
                    *** ONLINE SHOPPING SERVICE  ***    
                ----------------------------------------
                1. View product(s)
                2. Create new product
                3. Edit product
                4. Create a new shopping cart
                5. Add product(s) to shopping cart
                6. Remove product(s) from shopping cart
                7. View all coupons
                8. Apply coupon to product
                9. Display the cart amount
                10. Display shopping cart(s)
                (sorted based on the cart's weight)
                11. Print receipt for a shopping cart
                12. Exit
                ----------------------------------------
                """);
        System.out.print("Select the option you want: ");
    }

    /**
     * This method is called in Main class to run the whole program
     */
    public void run() {
        int choice = -1;
        boolean defaultCheck = false; // Use to check if the default method have been called yet

        // --------------MAIN PROGRAM--------------
        System.out.println("\nWelcome to the online shopping service application!");
        while (choice != 12) {
            displayMenu();
            choice = Integer.parseInt(input.nextLine()); // get the user's input
            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    editProduct();
                    break;
                case 3:
                    createProduct();
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
                    displayCartAmount();
                    break;
                case 10:
                    displayAllCartByWeight();
                    break;
                case 11:
                    printReceipt();
                    break;
                case 12:
                    System.out.println("\nThank you for using our service! We hope to see you again ^^");
                    break;
                default:
                    System.out.println("\n Invalid choice, please try again!");
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