/**
 * @author <Kang Junsik - s3916884>
 */

import model.*;
import java.util.Collections;
import java.util.Scanner;


// this one is to run the Online Shopping Service

public class Main { // run the program

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
                    7. Apply coupon
                    8. Remove coupon
                    9. Display the cart amount
                    10. Display shopping cart(s)
                    11. Print receipt
                    12. Exit
                    ----------------------------------------
                    Select the option you want: """);
    }

    /**
     * This method is called in Main class to run the whole program
     */
    public void run() {
        int choice = -1;
        boolean defaultCheck = false; // Use to check if the default method have been called yet

        System.out.println("\nWelcome to the online shopping service application!");
        while (choice != 12) {
//            displayMenu();
//            choice = scanner.nextLine(); // get the user's input
//            switch (choice) {
//                case 0:
//                    if (defaultCheck == true) { // Check if the default feature has been called before or not
//                        System.out.println("You can only use default option for only once! Please select other options.");
//                        break;
//                    }
//                    runDefault(); // Call function 0
//                    defaultCheck = true;
//                    break;
//                case "1":
//                    createProduct();
//                    break;
//                case "2":
//                    editProduct();
//                    break;
//                case "3":
//                    displayProducts();
//                    break;
//                case "4":
//                    createShoppingCart();
//                    break;
//                case "5":
//                    addProductToCart();
//                    break;
//                case "6":
//                    removeProductFromCart();
//                    break;
//                case "7":
//                    displayCartAmount();
//                    break;
//                case "8":
//                    displayAllCartByWeight();
//                    break;
//                case "9":
//                    System.out.println("\nThank you for using our service! We hope to see you again ^^");
//                    break;
//                case "10":
//                    System.out.println("\nThank you for using our service! We hope to see you again ^^");
//                    break;
//                case "11":
//                    System.out.println("\nThank you for using our service! We hope to see you again ^^");
//                    break;
//                case "12":
//                    System.out.println("\nThank you for using our service! We hope to see you again ^^");
//                    break;
//                default:
//                    System.out.println("\n Invalid choice, please try again!");
//                    displayMenu();
//                    choice = scanner.nextLine();
//            }

        }
    }

    public static void main(String[] args){
        ShoppingService ss = new ShoppingService();
        Scanner input = new Scanner(System.in);

        while(true) {
            System.out.println("""
                    Welcome to the online shopping service!
                    ---------------------------------------
                    1. View products
                    2. Create new product
                    3. Edit products
                    4. Create a new shopping cart
                    5. Add products to the shopping cart
                    6. Remove product to the shopping cart
                    7. Apply coupons
                    8. Remove coupons
                    9. Display the cart details
                    10. Display all shopping carts
                    11. Print receipt
                    0. Terminate program
                    ----------------------------------------
                    Select the option you want.""");
            int option = input.nextInt();
            input.nextLine();
            if (option == 1) { // view products

            } else if (option == 2) { // Create new product
                String productName;
                do {
                    System.out.println("Input the name of product.");
                    productName = input.nextLine();
                } while (!Product.checkNameIsUnique(productName, ss.getProducts())); // check the input name is unique or not
                System.out.println("Input the description of product.");
                String productDesc = input.nextLine();
                int productQuantity;
                do {
                    System.out.println("Input the quantity of product.");
                    productQuantity = input.nextInt();
                    input.nextLine();
                } while (!Product.checkQuantityIsValid(productQuantity));
                System.out.println("Input the price of product.");
                double productPrice = input.nextDouble();
                input.nextLine();
                while (true) {
                    System.out.println("""
                            What is the type of the product?
                            1. Digital
                            2. Physical""");
                    int optionTypeProduct = input.nextInt();
                    if (optionTypeProduct == 1) { // Digital
                        Digital newProduct = new Digital(productName, productDesc, productQuantity, productPrice);
                        ss.getProducts().add(newProduct);
                        System.out.println("New product has been added on system!");
                        break;
                    } else if (optionTypeProduct == 2) { // Physical
                        System.out.println("Input the weight of product."); // only physical product requires its weight
                        double productWeight = input.nextDouble();
                        input.nextLine();
                        Physical newProduct = new Physical(productName, productDesc, productQuantity, productPrice, productWeight);
                        ss.getProducts().add(newProduct);
                        System.out.println("New product has been added on system!");
                        break;
                    } else {
                        System.out.println("You input wrong option." +
                                "\n-----------------------");
                    }
                }
            } else if (option == 3) { // Edit products
                while (true) {
                    if (ss.getProducts().size() == 0) { // no saved products in the system
                        System.out.println("There is no product in the system!" +
                                "\n--------------------------------");
                        break;
                    }
                    System.out.println("What product you want to edit?");
                    String productName = input.nextLine();
                    for (int i = 0; i < ss.getProducts().size(); i++) {
                        if (ss.getProducts().get(i).getName().equals(productName)) {
                            System.out.println("Input the new description of product.");
                            String editProductDesc = input.nextLine();
                            ss.getProducts().get(i).setDescription(editProductDesc);
                            int editProductQuantity = 0;
                            do {
                                System.out.println("Input the new quantity of product.");
                                editProductQuantity = input.nextInt();
                                input.nextLine();
                            } while (!Product.checkQuantityIsValid(editProductQuantity));
                            ss.getProducts().get(i).setQuantity(editProductQuantity);
                            System.out.println("Input the new price of product.");
                            double editProductPrice = input.nextDouble();
                            ss.getProducts().get(i).setPrice(editProductPrice);
                            input.nextLine();
                            if (ss.getProducts().get(i) instanceof Physical) {
                                System.out.println("Input the new weight of product."); // only physical product requires its weight
                                double editProductWeight = input.nextDouble();
                                input.nextLine();
                                ((Physical) ss.getProducts().get(i)).setWeight(editProductWeight);
                            }
                            System.out.println("This product has been edited!" +
                                    "\n--------------------------------");
                            for (int j = 0; j < ss.getCarts().size(); j++) {
                                ss.getCarts().get(j).cartAmount(ss.getProducts());
                            }
                            break;
                        }
                        System.out.println("Your entered product is not exist on our system!" +
                                "\n--------------------------------");
                    }
                    break;
                }
                Collections.sort(ss.getCarts()); // sort the saved carts by weight if the data is changed
            } else if (option == 4) { // Create a new shopping cart
                ShoppingCart sc = new ShoppingCart();
                ss.getCarts().add(sc); // add to the system
                System.out.println("New shopping cart has been added!" +
                        "\n--------------------------------");
                Collections.sort(ss.getCarts()); // sort the saved carts by weight if the data is changed
            } else if (option == 5) { // Add products to the shopping cart
                if (ss.getCarts().size() == 0) {
                    System.out.println("There is no saved shopping cart!" +
                            "\n--------------------------------");
                } else {
                    int cartNumber = 0;
                    do {
                        System.out.println("What number of shopping cart do you want to use?");
                        cartNumber = input.nextInt();
                        input.nextLine();
                    } while (cartNumber > ss.getCarts().size() && cartNumber < 0);
                    if (cartNumber > ss.getCarts().size()) {
                        System.out.println("Your chosen shopping cart is not created yet!" +
                                "\n--------------------------------");
                        continue;
                    }
                    System.out.println("You've chosen shopping cart no." + cartNumber + "!");
                    System.out.println("\nWhat product do you want to add?");
                    String wantProduct = input.nextLine();

                    for (int i = 0; i < ss.getCarts().size(); i++) {
                        if (ss.getCarts().get(i).getNumbering() == cartNumber) {
                            ss.getCarts().get(i).addItem(wantProduct, ss.getProducts());
                            ss.getCarts().get(i).cartAmount(ss.getProducts()); // to calculate and save the total price (, total weight if it contains physical product)
                        }
                    }
                }
                Collections.sort(ss.getCarts()); // sort the saved carts by weight if the data is changed
            } else if (option == 6) { // remove product to the shopping cart
                if (ss.getCarts().size() == 0) {
                    System.out.println("There is no saved shopping cart!" +
                            "\n--------------------------------");
                } else {
                    int cartNumber = 0;
                    do {
                        System.out.println("What number of shopping cart do you want to use?");
                        cartNumber = input.nextInt();
                        input.nextLine();
                    } while (cartNumber > ss.getCarts().size() && cartNumber < 0);

                    if (cartNumber > ss.getCarts().size()) {
                        System.out.println("Your chosen shopping cart is not created yet!" +
                                "\n--------------------------------");
                        continue;
                    }

                    System.out.println("You've chosen shopping cart no." + cartNumber + "!");

                    boolean checkCartProduct = true; // to check any products in the cart

                    for (int i = 0; i < ss.getCarts().size(); i++) {
                        if (ss.getCarts().get(i).getNumbering() == cartNumber) {
                            if (ss.getCarts().get(i).getProductsName().size() == 0) {
                                System.out.println("No products in the cart!" +
                                        "\n--------------------------------");
                                checkCartProduct = false;
                                break;
                            }
                        }
                    }

                    if (!checkCartProduct) { // no product in the cart
                        continue;
                    }

                    System.out.println("\nWhat product do you want to remove?");
                    String removeProduct = input.nextLine();

                    for (int i = 0; i < ss.getCarts().size(); i++) {
                        if (ss.getCarts().get(i).getNumbering() == cartNumber) { // find the cart what user wants
                            ss.getCarts().get(i).removeItem(removeProduct, ss.getProducts());
                            ss.getCarts().get(i).cartAmount(ss.getProducts());
                        }
                    }
                }
                Collections.sort(ss.getCarts()); // sort the saved carts by weight if the data is changed
            } else if (option == 7) { // Apply coupons

            } else if (option == 8) { // Remove coupons

            } else if (option == 9) { // Display the cart details
                if (ss.getCarts().size() == 0) {
                    System.out.println("There is no saved shopping cart!" +
                            "\n--------------------------------");
                } else {
                    int cartNumber = 0;
                    do {
                        System.out.println("What number of shopping cart do you want to check the cart amount?");
                        cartNumber = input.nextInt();
                        input.nextLine();
                    } while (cartNumber > ss.getCarts().size() && cartNumber < 0);
                    System.out.println("You've chosen shopping cart no." + cartNumber + "!");

                    for (int i = 0; i < ss.getCarts().size(); i++) {
                        if (ss.getCarts().get(i).getNumbering() == cartNumber) {  // find the cart what user wants
                            System.out.println(ss.getCarts().get(i).toString());
                        }
                    }
                }
            } else if (option == 10) { // Display all shopping carts (Sorting carts)
                if (ss.getCarts().size() == 0) {
                    System.out.println("There is no saved shopping cart!" +
                            "\n--------------------------------");
                } else {
                    System.out.println("All shopping carts:" +
                            "\n-----------------------");
                    for (int j = 0; j < ss.getCarts().size(); j++) {
                        System.out.println(ss.getCarts().get(j).toString());
                    }
                }
            } else if (option == 11) { // Print receipt

            } else if (option == 0) {
                System.out.println("GOODBYE! HAVE A NICE DAY!");
                break;
            } else {
                System.out.println("You input wrong option." +
                        "\n-----------------------");
            }
            }
        }
}
