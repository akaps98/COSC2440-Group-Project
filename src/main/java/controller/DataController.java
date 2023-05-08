package controller;

/**
 * This class is the controller to execute the data functions (features)
 *
 * @author Group 9
 * @since 2023 - 05 - 07
 */

import model.data.DataInput;

public class DataController extends AppController {
    // CONSTRUCTOR
    public DataController() {
        super();
    }

    // METHOD
    /**
     * This method generate data from the text files and store in the system database
     * The text files used are: products.txt and carts.txt
     */
    public void generateData() {
        DataInput dIn = DataInput.getInstance();
        dIn.readProductFile();
        dIn.readCartFile();
    }
}
