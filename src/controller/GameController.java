package controller;

import java.io.*;
import model.Bear;

/**
 * GameController.java
 * Manages the global game state and save/load functionalities.
 * Created by Jayansh Bagga
 */
public class GameController {

    private static Bear currentBear;

    /**
     * Set the currently active bear in the game.
     * @param bear the bear to set
     */
    public static void setCurrentBear(Bear bear) {
        currentBear = bear;
    }

    /**
     * Get the currently active bear in the game.
     * @return the current bear
     */
    public static Bear getCurrentBear() {
        return currentBear;
    }

    /**
     * Saves the current bear to a file using serialization.
     * @param bear the bear object to save
     */
    public static void saveGame(Bear bear) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("bear_save.dat"))) {
            out.writeObject(bear);
            System.out.println("Game saved successfully."); // log success
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    /**
     * Loads the saved bear from file using deserialization.
     * @return the loaded bear object, or null if loading failed
     */
    public static Bear loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("bear_save.dat"))) {
            Bear loadedBear = (Bear) in.readObject();
            currentBear = loadedBear;
            System.out.println("Game loaded successfully."); // log success
            return loadedBear;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            return null;
        }
    }
}
