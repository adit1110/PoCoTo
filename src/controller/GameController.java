package controller;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.*; 
import model.Bear;

/**
 * GameController.java
 * Handles current game state, saving, and loading bear instance.
 * Created by Jayansh Bagga
 */
public class GameController {

    private static Bear currentBear;
    private static final String SAVE_FILE = "bear_save.dat"; // Save file name

    /**
     * Sets the currently active bear
     * @param bear the selected bear object
     */
    public static void setCurrentBear(Bear bear) {
        currentBear = bear;
    }

    /**
     * Gets the currently active bear
     * @return Bear object currently in play
     */
    public static Bear getCurrentBear() {
        return currentBear;
    }

    /**
     * Saves the current bear to a file using serialization
     */
    public static void saveGame() {
        if (currentBear == null) {
            System.out.println("No bear to save.");
            return;
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(currentBear);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads the bear object from save file
     * @return the loaded Bear or null if failed
     */
    public static Bear loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            Bear loadedBear = (Bear) in.readObject();
            currentBear = loadedBear;
            System.out.println("Game loaded successfully.");
            return loadedBear;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
