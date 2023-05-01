package model;
/**
 * @author Nguyen Anh Duy - s3878141
 */

import java.util.Scanner;

public class App {
    // ATTRIBUTES
    Scanner scanner = new Scanner(System.in); // collect user input;
    public static ProductMap productList = new ProductMap(); // a global variable to instantiate only 1 product list for the whole system
    public static ShoppingCartList cartList = new ShoppingCartList(); // a global variable to instantiate only 1 Shopping Cart list for the whole system

    String choice = "";
    boolean check = false;

    /**
     * Function 0: Default feature
     *
     * This method generates 16 products and 5 Shopping Carts into the system
     *
     * Conditions:
     * 1/ The products and shopping carts is created manually by the program (the values are assigned manually, not randomly)
     * 2/ User cannot modify the default method, only call it
     * 3/ Can only use the default method for only once after the program is executed
     *
     */
    public void runDefault() {
        /* Create 16 products (10 Physical & 6 Digital) and add to the system */
        Product p1 = new Physical("Ipad", "a tablet use for entertainment or presentation purpose", 10, 1200, 487);
        Product p2 = new Physical("Iphone", "a smart device use for entertainment or presentation purpose", 20, 1430, 148);
        Product p3 = new Physical("Macbook", "a laptop using MacOs operating system, M2 processor and exclusive components from Apple", 10, 2500, 1.29);
        Product p4 = new Physical("Airpod", "a smart and convenient wireless earphones for apple users", 7, 1200, 1.7);
        Product p5 = new Physical("Bluetooth Mouse", "a smart and wireless mouse with maximum of 10000 DPI", 0, 60, 0.85); // quantityAvailable = 0
        Product p6 = new Physical("4k Monitor", "a big screen monitor to display when using with PC", 3, 350, 4180);
        Product p7 = new Physical("Mechanical Keyboard", "a tablet use for entertainment or presentation purpose", 8, 115, 0.53);
        Product p8 = new Physical("Webcam", "a device to catch picture or record yourself during a meeting", 0, 34, 0.123); // quantityAvailable = 0
        Product p9 = new Physical("Wireless Charger", "a thing use to charge the electronic devices", 50, 8, 0.169);
        Product p10 = new Physical("Streaming Dock", "a device use by streamers", 0, 750, 2.4); // quantityAvailable = 0
        Product p11 = new Digital("Office 365", "the office 365 softwares: word, excel, powerpoint, teams, etc.", 18, 120);
        Product p12 = new Digital("Jetbrain license", "a license to download the pro software from Jetbrain company", 0, 1200);
        Product p13 = new Digital("Netflix family packet", "a subcription to the max option of a netflix account for watching unlimited movies, share to maximum of 4 devices", 10, 48);
        Product p14 = new Digital("Shooting game", "a multiplayer game on Steam", 100, 12);
        Product p15 = new Digital("Canva pro plan", "a pro plan to access all the pictures and templates on Canva", 9, 20);
        Product p16 = new Digital("Adobe package", "the adobe softwares: photoshop, premier pro, lightroom, etc.", 0, 1200); // quantityAvailable = 0

        // Add 16 products to the product list
        App.productList.addProduct(p1);
        App.productList.addProduct(p2);
        App.productList.addProduct(p3);
        App.productList.addProduct(p4);
        App.productList.addProduct(p5);
        App.productList.addProduct(p6);
        App.productList.addProduct(p7);
        App.productList.addProduct(p8);
        App.productList.addProduct(p9);
        App.productList.addProduct(p10);
        App.productList.addProduct(p11);
        App.productList.addProduct(p12);
        App.productList.addProduct(p13);
        App.productList.addProduct(p14);
        App.productList.addProduct(p15);
        App.productList.addProduct(p16);

        /* Create 5 Shopping Carts and add to the system */
        ShoppingCart2 c1 = new ShoppingCart2(); // Only contains physical products
        ShoppingCart2 c2 = new ShoppingCart2(); // Only contains digital products
        ShoppingCart2 c3 = new ShoppingCart2(); // Contains both physical and digital products (more digital than physical)
        ShoppingCart2 c4 = new ShoppingCart2(); // Contains both physical and digital products (more physical than physical)
        ShoppingCart2 c5 = new ShoppingCart2(); // Empty Cart

        // Add 5 Shopping Carts to the Shopping Cart List
        App.cartList.addCart(c1);
        App.cartList.addCart(c2);
        App.cartList.addCart(c3);
        App.cartList.addCart(c4);
        App.cartList.addCart(c5);

        /* Add the products to the Shopping Cart */
        c1.addItem(p1.getName());
        c1.addItem(p2.getName());
        c1.addItem(p3.getName());

        c2.addItem(p11.getName());
        c2.addItem(p12.getName());
        c2.addItem(p13.getName());

        c3.addItem(p1.getName());
        c3.addItem(p3.getName());
        c3.addItem(p13.getName());
        c3.addItem(p14.getName());
        c3.addItem(p15.getName());

        c4.addItem(p3.getName());
        c4.addItem(p4.getName());
        c4.addItem(p6.getName());
        c4.addItem(p7.getName());
        c4.addItem(p9.getName());
        c4.addItem(p11.getName());
        c4.addItem(p14.getName());

        // Trying to add products with quantity available = 0 --> shopping cart #5 will be empty
        c5.addItem(p5.getName());
        c5.addItem(p8.getName());
        c5.addItem(p10.getName());
        c5.addItem(p16.getName());

        // Display all products and shopping carts information
        App.productList.viewAllProducts();
        System.out.println("");
        App.cartList.viewCarts();
        System.out.println("");
        System.out.println("Succesfully created 16 products (10 Physical & 6 Digital) and 5 Shopping Cart in the system!");
    }

    /**
     * Function 1: this method is use to create a new Product and added to the system database (product list)
     *
     * User inputs:
     * 1/ name
     * 2/ description
     * 3/ quantityAvailable
     * 4/ price
     *
     * Action: create a Physical or Digital Product and add them to the product list
     * Also, output a message to the user accordingly to the return value
     *
     * @return boolean: boolean value states if the Product has been created successfully
     */
    public boolean createProduct() {
        check = false; // boolean value to return

        // Get input for product name and check if it existed
        System.out.print("\nEnter product name: ");
        String name = scanner.nextLine();
        if (App.productList.containProduct(name)) {
            System.out.println("The product has existed and could not be created!"); // each product name is unique and cannot be duplicated
            return check;
        }
        // Get all the other inputs (description, available quantity, price)
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product available quantity: ");
        int quantityAvailable = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter product price($): ");
        double price = Double.parseDouble(scanner.nextLine());
        // Get input for product type and provide appropriate type casting
        System.out.print("Is this product a digital or physical product (D - digital, P - physical): ");
        String productType = scanner.nextLine();
        if (productType.toUpperCase().equals("PHYSICAL") || productType.toUpperCase().equals("P")) { // if Product is Physical
            System.out.print("\nEnter product weight(g): ");
            double weight = Double.parseDouble(scanner.nextLine());
            Product addedProduct = new Physical(name, description, quantityAvailable, price, weight);
            App.productList.addProduct(addedProduct);
            check = true;
        } else if (productType.toUpperCase().equals("DIGITAL") || productType.toUpperCase().equals("D")) { // if Product is Digital
            Product addedProduct = new Digital(name, description, quantityAvailable, price);
            App.productList.addProduct(addedProduct);
            check = true;
        } else { // when user input the wrong product type (syntax or value error)
            System.out.println("\nInvalid product type.");
        }
        if (check == true) { // display a message whether the product is added successfully
            System.out.println(String.format("Successfully create product: %s, available quantity: %d!", name, quantityAvailable));
        } else {
            System.out.println("Failed to create the product, please try again!");
        }
        return check;
    }

    /**
     * Function 2: this method is use to update the information about the product
     * User will input the choice based on the sub-menu to choose the option for modifying information
     *
     * Available options to change:
     * 1/ description
     * 2/ quantityAvailable
     * 3/ price
     * 4/ set as gift
     *
     * Condition:
     * 1/ User cannot change the product name
     * 2/ User cannot convert back a gift to a normal product (but can choose this option again to set the new message for gift products)
     *
     * Action: modify the variable to the value that the user want to update and output a message to the user accordingly to the return value
     *
     * @return boolean: boolean value states if the Product information has been modified successfully
     */
    public boolean editProduct() {
        check = false; // boolean value to return
        App.productList.viewAllProducts(); // display all products for user to view

        // Get input for product name and check if the product is existed in the product list
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        if (!App.productList.containProduct(productName)) {
            System.out.println("The product is not existed and could not be modified!"); // there is no product found to modify
            return check;
        }
        Product modifiedProduct = App.productList.getProduct(productName);
        // Create a loop until user enter a valid choice
        String option = "";
        while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5")) {
            System.out.println("----------------------------------------\n");
            System.out.println("      1. Change description");
            System.out.println("      2. Update available quantity");
            System.out.println("      3. Adjust price");
            System.out.println("      4. Set as a gift");
            System.out.println("      5. Go Back");
            System.out.println("\n----------------------------------------");
            System.out.print("Enter your choice: ");
            option = scanner.nextLine();
            switch (option) { // validate the choice from user
                // Update product description
                case "1":
                    System.out.print("\nEnter new product description: ");
                    String newDescription = scanner.nextLine();
                    modifiedProduct.setDescription(newDescription);
                    break;
                // Update available quantity
                case "2":
                    System.out.print("\nEnter the updated product quantity in stock: ");
                    int newQuantity = Integer.parseInt(scanner.nextLine());;
                    modifiedProduct.setQuantity(newQuantity);
                    break;
                // Update product price
                case "3":
                    System.out.print("\nEnter the adjusted product price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());;
                    modifiedProduct.setPrice(newPrice);
                    break;
                // Update product as a gift
                case "4":
                    System.out.print("\nEnter the message you want to deliver for this gift: ");
                    String giftMsg = scanner.nextLine();
                    if (modifiedProduct instanceof Physical) {
                        ((Physical) modifiedProduct).setMessage(giftMsg);
                    } else {
                        ((Digital) modifiedProduct).setMessage(giftMsg);
                    }
                    break;
                // Return to the primary menu
                case "5":
                    break;
                // User input invalid choice --> reenter the choice
                default:
                    System.out.println("Invalid choice, please try again!");
            }
        }
        // Output the message accordingly if the product is successfully modifidied
        if (option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4") || option.equals("5")) {
            System.out.println(String.format("Successfully modified product: %s!", productName));
            check = true;
        }
        return check;
    }

    /**
     * Function 3: this method is used to display all the products in the product list
     * User input the choice to select the option for viewing products
     *
     * Available options to view product:
     * 1/ View a product detail (all the information of that product)
     * 2/ View all products in the system (only display the product names)
     * 3/ View all products that are currently available to buy (only display the product names)
     *
     * Action: Display the string that contains information accordingly to user's choice
     */
    public void displayProducts() {
        // Create a loop until user enter a valid choice
        String option = "";
        while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4")) {
            System.out.println("----------------------------------------\n");
            System.out.println("      1. View a product detail");
            System.out.println("      2. View all products (product names)");
            System.out.println("      3. View available products to buy (product names)");
            System.out.println("      4. Go Back");
            System.out.println("\n----------------------------------------");
            System.out.print("Enter your choice: ");
            option = scanner.nextLine();
            switch (option) { // validate the choice from user
                // View a product detail
                case "1":
                    // Get user input for product name and check if it is existed in the product list
                    System.out.print("\nEnter product name: ");
                    String name = scanner.nextLine();
                    if (!App.productList.containProduct(name)) { // Cannot find a Product accordingly to the given product name
                        System.out.println("The product is not existed and could not be displayed!");
                        break;
                    }
                    // Found a product, proceeding to get the product detail
                    Product p = App.productList.getProduct(name);
                    if (p instanceof Physical) { // Check for product type to call the appropriate type casting getProductDetail() method
                        System.out.println(((Physical) p).getProductDetail());
                    } else {
                        System.out.println(((Digital) p).getProductDetail());
                    }
                    break;
                // View every products that existed in the product list
                case "2":
                    App.productList.viewAllProducts();
                    break;
                // View only the products that have the quantity which is currently available to add to cart (quantity > 0)
                case "3":
                    App.productList.viewAvailableProducts();
                    break;
                // Return to the primary menu
                case "4":
                    break;
                // User input invalid choice --> reenter the choice
                default:
                    System.out.println("Invalid choice, please try again!");
            }
        }

    }

    /**
     * Function 4: this method is use to create a new Shopping Cart for user
     *
     * Conditions:
     * 1/ Cannot remove a shopping cart (not covered in the app's scope)
     * 2/ The Shopping Cart is automatically assigned a cart number, according to the (index of the cart in the list + 1)
     * 3/ User can add as much shopping cart as they want (there is no limitation in adding more shopping cart)
     *
     * Action: create a new Shopping Cart and add to the Shopping Cart List of the system
     * Also, output a message to inform user whether the Shopping Cart is created successfully
     */
    public void createShoppingCart() {
        ShoppingCart2 c = new ShoppingCart2();
        App.cartList.addCart(c);
        System.out.println(String.format("Successfully created cart %d!", App.cartList.indexOf(c) + 1));
    }

    /**
     * Function 5: this method is use to add a Product to a Shopping Cart
     *
     * User inputs:
     * 1/ product name (must existed in the product list)
     * 2/ cart number (the number is addressed to the according shopping cart)
     *
     * Conditions:
     * 1/ Scope: the cart number is not validated in this case (assume that user input numbers correctly)
     * 2/ There must be at least 1 shopping cart in the system
     *
     * Action: add the Product to the Shopping Cart and output a message to the user accordingly to the return value
     * @return boolean: boolean value states if the Product has been added to the cart successfully
     */
    public boolean addProductToCart() {
        check = false; // boolean value to return

        // Check if there is any shopping cart existed in the shopping cart list
        if (App.cartList.countCarts() == 0) {
            System.out.println("No shopping cart found! Please create a shopping cart before adding the product.");
            return false;
        }
        App.productList.viewAvailableProducts(); // display the available products in the console
        System.out.print("\nEnter the product name you want to add to cart: ");
        // Get user input for product name to add and generate the shopping cart
        String addedProductName = scanner.nextLine();
        ShoppingCart2 c = App.cartList.getCart(1); // assume that there is at least 1 cart --> get the first cart in the list
        if (App.cartList.countCarts() == 1) { // if there is only one cart --> automatically call the method to add product to cart 1
            check = c.addItem(addedProductName);
        } else if (App.cartList.countCarts() > 1) { // if there is more than one cart --> let user choose the cart to add product
            App.cartList.viewCarts(); // display all the existing carts in the system
            System.out.print("Select the shopping cart # to add product: ");
            int cartNumber = Integer.parseInt(scanner.nextLine()); // get user input for cart number
            c = App.cartList.getCart(cartNumber);
            check = c.addItem(addedProductName);
        }
        if (check == true) { // display a message whether the product is added successfully to the shopping cart
            System.out.println(String.format("Successfully add %s to cart %d!", addedProductName, App.cartList.indexOf(c) + 1));
        } else {
            System.out.println(String.format("Cannot add %s to cart %d. Please try again!", addedProductName, App.cartList.indexOf(c) + 1));
        }

        return check;
    }

    /**
     * Function 6: this method is use to remove a Product from a Shopping Cart
     *
     * User inputs:
     * 1/ product name (must existed in the product list)
     * 2/ cart number (the number is addressed to the according shopping cart)
     *
     * Conditions:
     * 1/ Scope: the cart number is not validated in this case (assume that user input numbers correctly)
     * 2/ There must be at least 1 shopping cart in the system
     *
     * Action: remove a product from a shopping cart and output a message to the user accordingly to the return value
     * @return boolean: boolean value states if the Product has been removed successfully
     */
    public boolean removeProductFromCart() {
        check = false;
        if (App.cartList.countCarts() == 0) {
            System.out.println("No shopping cart found! Please create a shopping cart before adding and removing the product.");
            return false;
        }
        App.productList.viewAvailableProducts();
        System.out.print("\nEnter the product name you want to remove from cart: ");
        // Get user input for product name to remove and generate the shopping cart
        String removedProductName = scanner.nextLine();
        ShoppingCart2 c = App.cartList.getCart(1); // assume that there is at least 1 cart --> get the first cart in the list
        if (App.cartList.countCarts() == 1) { // if there is only one cart --> automatically call the method to remove product from cart 1
            check = c.removeItem(removedProductName);
        } else if (App.cartList.countCarts() > 1) { // if there is more than one cart --> let user choose the cart to remove product
            App.cartList.viewCarts(); // display all the existing carts in the system
            System.out.print("Select the shopping cart # to remove product: ");
            int cartNumber = Integer.parseInt(scanner.nextLine()); // get user input for the cart number
            c = App.cartList.getCart(cartNumber);
            check = c.removeItem(removedProductName);
        }
        if (check == true) { // display a message whether the product is added successfully to the shopping cart
            System.out.println(String.format("Successfully remove %s from cart %d!", removedProductName, App.cartList.indexOf(c) + 1));
        } else {
            System.out.println(String.format("Cannot remove %s from cart %d. Please try again!", removedProductName, App.cartList.indexOf(c) + 1));
        }
        return check;
    }

    /**
     * Function 7: this method is use to display a cart amount of the shopping cart
     *
     * User inputs:
     * 1/ cart number (the number is addressed to the according shopping cart)
     *
     * Conditions:
     * 1/ cart number is not validated in this case (assume that user input numbers correctly)
     *
     * Action: calculate the cart amount of the shopping cart and output the number to the console
     * @return boolean: boolean value states if the cart amount has been calculated successfully
     */
    public boolean displayCartAmount() {
        check = false; // boolean value to return

        // Check if there is any shopping cart existed
        if (App.cartList.countCarts() == 0) {
            System.out.println("No shopping cart found! Please create a shopping cart first!");
            return check;
        }
        int cartNumber = 1; // assume that there is at least 1 cart --> set the cart number = 1
        if (App.cartList.countCarts() > 1) { // if there is more than 1 cart --> let the user choose the cart
            App.cartList.viewCarts(); // display all the existing carts in the system
            System.out.print("Select the shopping cart #: ");
            cartNumber = Integer.parseInt(scanner.nextLine());
        }
        // Get the shopping cart accordingly to the cart number
        ShoppingCart2 c = App.cartList.getCart(cartNumber);
        // Calculate the cart amount and display in the console
        System.out.println(String.format("Shopping Cart #%d's cart amount: %,.2f", App.cartList.indexOf(c) + 1, c.cartAmount()));
        check = true;
        return check;
    }

    /**
     * Function 8: this method is use to display all the Shopping Cart
     *
     * Condition: the carts will be displayed in ascending order based on the cart's weight
     *
     * Action: display all the shopping carts based on the condition
     */
    public void displayAllCartByWeight() {
        App.cartList.viewCartsAfterSorted();
    }

    /**
     * Display the instructions and the scope of the console application for user
     */
    public void displayInstructions() {
        System.out.println("Welcome to the Online Shopping Service Application! \n");
    }

    /**
     * Display the console application primary menu (menu for user to select the feature)
     */
    public void displayMenu() {
        System.out.println("\n----------------------------------------");
        System.out.println("    *** ONLINE SHOPPING SERVICE  ***    ");
        System.out.println("----------------------------------------\n");
        System.out.println("    0. Generate default shopping carts and products");
        System.out.println("    1. Create new product");
        System.out.println("    2. Edit product");
        System.out.println("    3. View product(s)");
        System.out.println("    4. Create new shopping cart");
        System.out.println("    5. Add product to cart");
        System.out.println("    6. Remove product from cart");
        System.out.println("    7. Display cart amount");
        System.out.println("    8. Display all shopping carts");
        System.out.println("     (based on their total weights)");
        System.out.println("    9. Exit");
        System.out.println("\n----------------------------------------");
        System.out.print("Enter your choice: ");
    }

    /**
     * This method is called in Main class to run the whole program
     */
    public void run() {
        boolean defaultCheck = false; // Use to check if the default method have been called yet

        System.out.println("\nWelcome to the online shopping service application!");
        while (!choice.equals("9")) {
            displayMenu();
            choice = scanner.nextLine(); // get the user's input
            switch (choice) {
                case "0":
                    if (defaultCheck == true) { // Check if the default feature has been called before or not
                        System.out.println("You can only use default option for only once! Please select other options.");
                        break;
                    }
                    runDefault(); // Call function 0
                    defaultCheck = true;
                    break;
                case "1":
                    createProduct();
                    break;
                case "2":
                    editProduct();
                    break;
                case "3":
                    displayProducts();
                    break;
                case "4":
                    createShoppingCart();
                    break;
                case "5":
                    addProductToCart();
                    break;
                case "6":
                    removeProductFromCart();
                    break;
                case "7":
                    displayCartAmount();
                    break;
                case "8":
                    displayAllCartByWeight();
                    break;
                case "9":
                    System.out.println("\nThank you for using our service! We hope to see you again ^^");
                    break;
                default:
                    System.out.println("\n Invalid choice, please try again!");
                    displayMenu();
                    choice = scanner.nextLine();
            }

        }

    }
}

