package controller;

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
