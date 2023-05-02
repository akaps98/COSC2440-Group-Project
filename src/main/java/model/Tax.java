package model;

import database.ShoppingDB;

/**
 * @author Group
 */

public class Tax {
    private static Tax TaxInstance;
    private double taxAmount;


    private Tax() {
        this.taxAmount = 0;
    }

    public void setTaxAmount(String productName) {
//        Edit tax value here
        double freeTax = 0;
        double normalTax = 0.1;
        double luxuryTax = 0.2;

        String taxType = ShoppingDB.getInstance().getProducts().getProduct(productName).getTaxType();

//        Assign tax amount based on type
        switch (taxType) {
            case "freeTax" -> taxAmount = freeTax;
            case "normalTax" -> taxAmount = normalTax;
            case "luxuryTax" -> taxAmount = luxuryTax;
        }
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public static Tax getTaxInstance(String productName) {
        if (TaxInstance == null) {
            TaxInstance = new Tax();
        }
        TaxInstance.setTaxAmount(productName);
        return TaxInstance;
    }
}
