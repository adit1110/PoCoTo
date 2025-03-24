package model;

public abstract class Bear {
    protected String name;
    protected int hunger;
    protected int health;
    protected int sleep;
    protected int happiness;

    protected final int MAX_STAT = 100;
    protected final int MIN_STAT = 0;

    public Bear(String name) {
        this.name = name;
        this.hunger = MAX_STAT;
        this.health = MAX_STAT;
        this.sleep = MAX_STAT;
        this.happiness = MAX_STAT;
    }

    // Basic actions for bear

    public void feed(int amount) {
        hunger = Math.min(hunger + amount, MAX_STAT);
    }
    
    public void sleep(int amount) {
        sleep = Math.min(hunger + amount, MAX_STAT);
    }

    public void play(int amount) {
        happiness = Math.min(hunger + amount, MAX_STAT);
    }

    public void heal(int amount) {
        health = Math.min(hunger + amount, MAX_STAT);
    }

    // Stat updates (to be called periodically or in a game loop)

    public abstract void updateStats();

    // State Checkers

    public boolean isDead() {
        return health <= MIN_STAT;
    }

    public boolean isAngry() {
        return happiness <= MAX_STAT * 0.25;
    }

    public boolean isHungry() {
        return hunger <= MAX_STAT * 0.25;
    }

    public boolean isTired() {
        return sleep <= MAX_STAT * 0.25;
    }

    // Getters

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

    // Optional useful for UI (check in with group about this)
    
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

    public abstract String getLabel();
}
