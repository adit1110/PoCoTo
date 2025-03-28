package controller;

import java.io.*;
import model.Bear;

/**
 * GameController.java
 * Handles current game state, saving, and loading bear instance.
 * @author: Jayansh Bagga
 */
public class GameController {

    /** The currently selected bear */
    private static Bear currentBear;

    /** Save file path */
    private static final String SAVE_FILE = "bear_save.dat";

    /**
     * Sets the current bear in session
     * @param bear the active Bear object
     */
    public static void setCurrentBear(Bear bear) {
        currentBear = bear;
    }

    /**
     * Returns the current bear instance
     * @return Bear object currently being played
     */
    public static Bear getCurrentBear() {
        return currentBear;
    }

    /**
     * Saves the current bear state to disk using serialization
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
     * Loads the saved bear from disk
     * @return Bear object or null on failure
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
