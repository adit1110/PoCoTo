package controller;

import java.io.*;
import model.Bear;
import java.nio.file.Path;

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
     * @return the current bearsaveGame
     */
    public static Bear getCurrentBear() {
        return currentBear;
    }

    public static void saveGame(Bear bear) {
        saveGame(bear, Path.of("bear_save.dat"));
    }

    public static boolean saveGame(Bear bear, Path path) {
        try (ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            out.writeObject(bear);
            System.out.println("Game saved successfully.");
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
            return false;
        }
    }

    public static Bear loadGame() {
        return loadGame(Path.of("bear_save.dat"));
    }

    public static Bear loadGame(Path path) {
        try (
            FileInputStream fileIn = new FileInputStream(path.toFile());
            ObjectInputStream objectIn = new ObjectInputStream(fileIn)
        ) {
            Bear loadedBear = (Bear) objectIn.readObject();
            currentBear = loadedBear;

            System.out.println("Game loaded successfully.");
            return loadedBear;

        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            return null;
        }
    }
}
