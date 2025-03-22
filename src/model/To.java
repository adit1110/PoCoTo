package model;

public class To extends Bear {

    public To(String name) {
        super(name);
    }

    @Override
    public void updateStats() {

        hunger = Math.max(hunger - 3, MIN_STAT);
        sleep = Math.max(sleep - 2, MIN_STAT);
        happiness = Math.max(happiness - 4, MIN_STAT); // big happiness penalty

        if (happiness <= 20) {
            health = Math.max(health - 2, MIN_STAT);
        }
    }
    
}
