package controller;
import model.*;
import view.*;

public class ProductController {
    Product model;
    ProductView view;

    // CONSTRUCTOR
    public ProductController(Product model, ProductView view) {
        this.model = model;
        this.view = view;
    }
}
