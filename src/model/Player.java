package model;

/**
 * The Player class represents a player in the Bear game.
 * It holds a Bear object, an Inventory object, and the player's score.
 * It also tracks session time and provides methods to manage the player's game progress.
 * 
 * @author: Krish Patel
 */
public class Player {

    private Bear bear;
    private Inventory inventory;
    private int score;
    private long sessionStartTime;
    private long totalSessionTime;

    /**
     * Constructs a Player object with a given Bear object and initializes an empty Inventory and score.
     *
     * @param bear The Bear object associated with the player.
     */
    public Player(Bear bear) {
        this.bear = bear;
        this.inventory = new Inventory(); // Assuming Inventory has a default constructor
        this.score = 0;
        this.totalSessionTime = 0;
    }

    /**
     * Adds the given points to the player's total score.
     *
     * @param points The points to be added to the score.
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Gets the player's current score.
     *
     * @return The current score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the Bear object associated with the player.
     *
     * @return The Bear object associated with the player.
     */
    public Bear getBear() {
        return bear;
    }

    /**
     * Gets the Inventory object associated with the player.
     *
     * @return The Inventory object associated with the player.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Adds an item to the player's inventory.
     *
     * @param item The item to be added to the inventory.
     */
    public void addItemToInventory(String item) {
        inventory.addItem(item);
    }

    /**
     * Uses an item from the player's inventory.
     * The item must be in the inventory to be used.
     *
     * @param item The item to be used.
     */
    public void useItem(String item) {
        if (inventory.hasItem(item)) {
            inventory.useItem(item);
        }
    }

    /**
     * Starts a new session and tracks the session's start time.
     */
    public void startSession() {
        sessionStartTime = System.currentTimeMillis();
    }

    /**
     * Ends the current session and updates the total session time.
     */
    public void endSession() {
        long sessionEndTime = System.currentTimeMillis();
        totalSessionTime += (sessionEndTime - sessionStartTime);
    }

    /**
     * Gets the total session time accumulated across all sessions.
     *
     * @return The total session time in milliseconds.
     */
    public long getTotalSessionTime() {
        return totalSessionTime;
    }
}
