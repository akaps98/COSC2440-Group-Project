/**
 * @author <Kang Junsik - s3916884>
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ShoppingCart extends ShoppingService implements Comparable<ShoppingCart> {
    private HashSet<String> productsName = new HashSet<String>();
    private double totalWeight = 0;
    private double totalPrice = 0;
    private final double BASEFEE = 0.1; // to calculate shipping fee
    private static int addNumber = 1;
    private int numbering = 0;

    public ShoppingCart() {
        numbering = addNumber;
        addNumber++;
    }

    boolean addItem(String productName, ArrayList<Product> savedProductsOnSystem) {
        Iterator<String> savedProductsName = productsName.iterator(); // to check the product that is already in the cart
        while(savedProductsName.hasNext()) {
            if (savedProductsName.next().equals(productName)) { // already exists
                System.out.println("This product is already in the cart." +
                                   "\n--------------------------------");
                return false;
            }
        }
        for(int i = 0; i < savedProductsOnSystem.size(); i++) {
            if(savedProductsOnSystem.get(i).getName().equals(productName)) {
                if(savedProductsOnSystem.get(i).getQuantity() == 0) {
                    System.out.println("Out of stock!" +
                                       "\n--------------------------------");
                    return false;
                } else {
                    productsName.add(savedProductsOnSystem.get(i).getName());
                    savedProductsOnSystem.get(i).setQuantity(savedProductsOnSystem.get(i).getQuantity() - 1);
                    System.out.println("The product has been added in this shopping cart!" +
                                       "\n--------------------------------");
                    return true;
                }
            }
        }
        System.out.println("This product is not exist!" +
                           "\n--------------------------------");
        return false;
    }

    boolean removeItem(String productName, ArrayList<Product> savedProductsOnSystem) {
        Iterator<String> savedProductsName = productsName.iterator(); // iterator for the products(name) in current shopping cart
        while(savedProductsName.hasNext()) {
            if(savedProductsName.next().equals(productName)) { // find the product that user is searching!
                productsName.remove(productName); // remove the product in the cart first
                System.out.println("The product has been removed in this shopping cart!");
                for(int i = 0; i < savedProductsOnSystem.size(); i++) { // find the product in the product in system
                    if(savedProductsOnSystem.get(i).getName().equals(productName)) {
                        savedProductsOnSystem.get(i).setQuantity(savedProductsOnSystem.get(i).getQuantity() + 1); // increase the quantity of product
                        return true;
                    }
                }
            }
        } // if the while loop doesn't find any products,
        System.out.println("This product is not exist in this shopping cart." +
                           "\n--------------------------------");
        return false;
    }

    double cartAmount(ArrayList<Product> savedProductsOnSystem) {
        ArrayList<Physical> physicalProductsOnCart = new ArrayList<>();

        for(int i = 0; i < savedProductsOnSystem.size(); i++) {
            if(savedProductsOnSystem.get(i) instanceof Physical) {
                physicalProductsOnCart.add((Physical) savedProductsOnSystem.get(i));
            }
        }

        double productPrice = 0;
        double totalWeight = 0;
        double finalAmount = 0;

        Iterator<String> temporarySavedProductsName = productsName.iterator(); // iterator for the products(name) in current shopping cart

        ArrayList<String> savedProductName = new ArrayList<>(); // change iterator to arraylist

        while(temporarySavedProductsName.hasNext()) {
            savedProductName.add(temporarySavedProductsName.next());
        }

        for(int i = 0; i < savedProductName.size(); i++) {
            for(int j = 0; j < savedProductsOnSystem.size(); j++) {
                if (savedProductName.get(i).equals(savedProductsOnSystem.get(j).getName())) {
                    productPrice += savedProductsOnSystem.get(j).getPrice();
                    if (savedProductsOnSystem.get((j)) instanceof Physical) { // the weight only be added when the product is physical
                        for (int k = 0; k < physicalProductsOnCart.size(); k++) { // find the physical product
                            if (physicalProductsOnCart.get(k).equals(savedProductsOnSystem.get((j)))) {
                                totalWeight += physicalProductsOnCart.get(k).getWeight();
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        finalAmount = productPrice + (totalWeight * BASEFEE); // calculate final cost
        this.totalPrice = finalAmount;
        this.totalWeight = totalWeight; // to save & renew the total weight
        return finalAmount; // to save & renew the final amount
    }

    @Override
    public int compareTo(ShoppingCart o) {
        return (int) (o.totalWeight - this.totalWeight); // sort the shopping carts as the ascending order by its total weight
    }

    @Override
    public String toString() {
        String printOutProducts = "";

        Iterator<String> allProducts = productsName.iterator();

        while(allProducts.hasNext()) {
            printOutProducts += allProducts.next() + " ";
        }

        return  "Shopping Cart no." + numbering +
                "\n----------------------" +
                "\nAll Products: " + printOutProducts +
                "\nTotal weight: " + totalWeight +
                "\nTotal price: " + totalPrice +
                "\n----------------------";
    }

    public HashSet<String> getProductsName() {
        return productsName;
    }

    public void setProductsName(HashSet<String> productsName) {
        this.productsName = productsName;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumbering() {
        return numbering;
    }

    public void setNumbering(int numbering) {
        this.numbering = numbering;
    }
}

