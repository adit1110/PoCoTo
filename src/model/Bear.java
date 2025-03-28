/**
 * Author: Adit Bhimani
 * Bear.java is an abstract class that represents a bear in the game. It contains
 * the bear's name, hunger, health, sleep, and happiness used by all bear types
 * 
 */

package model;
import java.io.Serializable;

/**
 * Abstract class representing a Bear.
 * Contains common fields and methods used by all bear types.
 */
public abstract class Bear implements Serializable { //Serializable Added By Jayansh Bagga, This allows bear onbjects to be saveable via seralization.
    // Basic attributes for a bear
    protected String name;
    protected int hunger;
    protected int health;
    protected int sleep;
    protected int happiness;

    // Maximum and minimum values for the stats
    protected final int MAX_STAT = 100;
    protected final int MIN_STAT = 0;

    /**
     * Constructor to initialize a bear with full stats.
     *
     * @param name the name of the bear
     */
    public Bear(String name) {
        this.name = name;
        this.hunger = MAX_STAT;
        this.health = MAX_STAT;
        this.sleep = MAX_STAT;
        this.happiness = MAX_STAT;
    }

    // Basic actions for the bear. These methods increase the respective stat
    // but do not allow it to exceed MAX_STAT.

    /**
     * Feed the bear to increase hunger (fullness).
     *
     * @param amount the amount to feed
     */
    public void feed(int amount) {
        hunger = Math.min(hunger + amount, MAX_STAT);
    }
    
    /**
     * Let the bear sleep to restore sleep stat.
     *
     * @param amount the amount of sleep recovery
     */
    public void sleep(int amount) {
        sleep = Math.min(sleep + amount, MAX_STAT);
    }

    /**
     * Play with the bear to increase happiness.
     *
     * @param amount the amount of fun to add
     */
    public void play(int amount) {
        happiness = Math.min(hunger + amount, MAX_STAT);
    }

    /**
     * Heal the bear to restore health.
     *
     * @param amount the healing amount
     */
    public void heal(int amount) {
        health = Math.min(hunger + amount, MAX_STAT);
    }

    /**
     * Abstract method to update the bear's stats over time.
     * Must be implemented by each subclass with its unique behavior.
     */
    public abstract void updateStats();

    // State checkers to determine if the bear is in a critical state.

    /**
     * Check if the bear is dead (health is at or below the minimum).
     *
     * @return true if dead, false otherwise.
     */
    public boolean isDead() {
        return health <= MIN_STAT;
    }

    /**
     * Check if the bear is angry (happiness is less than or equal to 25% of max).
     *
     * @return true if angry, false otherwise.
     */
    public boolean isAngry() {
        return happiness <= MAX_STAT * 0.25;
    }

    /**
     * Check if the bear is very hungry (hunger is less than or equal to 25% of max).
     *
     * @return true if hungry, false otherwise.
     */
    public boolean isHungry() {
        return hunger <= MAX_STAT * 0.25;
    }

    /**
     * Check if the bear is tired (sleep is less than or equal to 25% of max).
     *
     * @return true if tired, false otherwise.
     */
    public boolean isTired() {
        return sleep <= MAX_STAT * 0.25;
    }

    // Getters for the bear's attributes

    public String getName() {
        return name;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHealth() {
        return health;
    }

    public int getSleep() {
        return sleep;
    }

    public int getHappiness() {
        return happiness;
    }

    // These methods return normalized percentages (0.0 to 1.0) for UI usage.

    public double getHungerPercent() {
        return hunger / (double) MAX_STAT;
    }

    public double getHealthPercent() {
        return health / (double) MAX_STAT;
    }

    public double getSleepPercent() {
        return sleep / (double) MAX_STAT;
    }

    public double getHappinessPercent() {
        return happiness / (double) MAX_STAT;
    }
    
    /**
     * Abstract method to get the display label for the bear.
     * Subclasses should return a string like "Po (Panda)".
     *
     * @return the display label for the bear.
     */
    public abstract String getLabel();
}
