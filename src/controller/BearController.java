package controller;

import model.Bear;

public class BearController {

    private Bear bear;

    public BearController(Bear bear) {
        this.bear = bear;
    }

    // UI-Triggered actions
    public void feedBear() {
        bear.feed(10);
    }

    public void playWithBear() {
        bear.play(10);
    }

    public void putBearToSleep() {
        bear.sleep(15);
    }

    public void healBear() {
        bear.heal(5);
    }

    // Update game state over time (optional, for timer loop)
    public void updateBear() {
        bear.updateStats();
    }

    // UI Display Values
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

    public boolean isDead() {
        return bear.isDead();
    }

    public boolean isAngry() {
        return bear.isAngry();
    }

    public Bear getBear() {
        return bear;
    }
}
