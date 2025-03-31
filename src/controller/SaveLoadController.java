/**
 * SaveLoadController.java
 * Controller class for saving and loading Bear game data.
 * Uses the DatabaseHelper utility class to write and read Bear objects to and from a file.
 *
 * @author: Bhavya Sharma
 */

package controller;

import model.Bear;
import util.DatabaseHelper;


/**
 * SaveLoadController class handles the saving and loading of Bear game data.
 * It uses the DatabaseHelper utility class to perform file operations.
 */

public class SaveLoadController {

    private Bear bear;

    public SaveLoadController(Bear bear) {
        this.bear = bear;
    }

    public void saveGame() {
        DatabaseHelper.writeToFile(bear);
    }

    public Bear loadGame(Class<? extends Bear> bearType) {
        return DatabaseHelper.readFromFile(bearType);
    }
}