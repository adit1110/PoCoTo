/**
 * BearController acts as an intermediary between the bear model and the UI.
 * It provides methods to trigger bear actions and access bear state.
 * @author: Adit Bhimani
 */

package controller;

import model.Bear;

/**
 * BearController class manages the bear's state and actions.
 * It provides methods to interact with the bear and retrieve its stats.
 * This class is responsible for handling user-triggered actions and updating the bear's state.
 */

public class BearController {

    private Bear bear;

    /**
     * Constructor to set the current bear.
     *
     * @param bear the bear instance to control
     */
    public BearController(Bear bear) {
        this.bear = bear;
    }

    // UI-Triggered actions: these methods modify the bear's state.

    /**
     * Feed the bear.
     */
    public void feedBear() {
        bear.feed(10);
    }

    /**
     * Play with the bear.
     */
    public void playWithBear() {
        bear.play(10);
    }

    /**
     * Put the bear to sleep.
     */
    public void putBearToSleep() {
        bear.sleep(15);
    }

    /**
     * Heal the bear.
     */
    public void healBear() {
        bear.heal(5);
    }

    /**
     * Update the bear's stats (e.g., on a timer).
     */
    public void updateBear() {
        bear.updateStats();
    }

    // UI Display Values: methods to get normalized percentages of each stat.

    /*
     * Returns the bear's hunger percentage.
     */
    public double getHungerPercent() {
        return bear.getHungerPercent();
    }

    /*
     * Returns the bear's health percentage.
     */
    public double getHealthPercent() {
        return bear.getHealthPercent();
    }

    /*
     * Returns the bear's sleep percentage.
     */
    public double getSleepPercent() {
        return bear.getSleepPercent();
    }

    /*
     * Returns the bear's happiness percentage.
     */
    public double getHappinessPercent() {
        return bear.getHappinessPercent();
    }

    // State checkers

    /*
     * Checks if the bear is dead.
     */
    public boolean isDead() {
        return bear.isDead();
    }

    /*
     * Checks if the bear is angry.
     */
    public boolean isAngry() {
        return bear.isAngry();
    }
    
    /**
     * Getter for the bear instance.
     *
     * @return the bear controlled by this controller.
     */
    public Bear getBear() {
        return bear;
    }
}