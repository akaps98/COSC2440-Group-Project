package controller;

/**
 * desc
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
     */
    public void generateData() {
        DataInput dIn = DataInput.getInstance();
        dIn.readProductFile();
        dIn.readCartFile();
    }
}
