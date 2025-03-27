/**
 * Author: Adit Bhimani
 * BearController acts as an intermediary between the bear model and the UI.
 * It provides methods to trigger bear actions and access bear state.
 */

package controller;

import model.Bear;

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

    public double getHungerPercent() {
        return bear.getHungerPercent();
    }

    public double getHealthPercent() {
        return bear.getHealthPercent();
    }

    public double getSleepPercent() {
        return bear.getSleepPercent();
    }

    public double getHappinessPercent() {
        return bear.getHappinessPercent();
    }

    // State checkers

    public boolean isDead() {
        return bear.isDead();
    }

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