package controller;

import model.product.Digital;
import model.product.Physical;
import model.product.Product;
import model.product.ProductMap;

public class ProductController extends AppController{
    // ATTRIBUTES
    private ProductMap products;

    // CONSTRUCTOR
    public ProductController() {
        super();
        products = db.getProducts();
    }

    // METHOD
    /**
     * Function 1: this method displays the product(s) information in the product list
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
                        System.out.println(p.getProductDetail());
                    } else {
                        System.out.println(p.getProductDetail());
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
     * Function 2: this method create a new Product and added to the system database (product list)
     * <p>
     * User inputs:
     * 1/ name
     * 2/ description
     * 3/ quantity: available quantity for this product to be purchased
     * 4/ price
     * 5/ weight (for Physical Product only)
     * <p>
     * Action: create a Physical or Digital Product and add them to the product list
     * Also, output a message to the user accordingly to the return value
     *
     * @return boolean: boolean value states if the Product has been created successfully
     */
    public boolean createProduct() {
        boolean check = false; // boolean value to return

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
                    System.out.print("Enter product weight(g): ");
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
            System.out.printf("Successfully create product: %s, available quantity: %d!%n", name, quantity);
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
     * 2/ available quantity
     * 3/ weight (Physical Product only)
     * 4/ tax type
     * <p>
     * Condition:
     * 1/ User cannot change the product name
     * <p>
     * Action: modify the variable to the value that the user want to update and output a message to the user accordingly to the return value
     *
     * @return boolean: boolean value states if the Product information has been modified successfully
     */
    public boolean editProduct() {
        boolean check = false; // boolean value to return
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
            System.out.printf("Successfully modified product: %s!%n", name);
        } else {
            System.out.println("Did not modify product!");
        }
        return check;
    }
}
