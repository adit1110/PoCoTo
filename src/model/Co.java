package model;

public class Co extends Bear {

    public Co(String name) {
        super(name);
    }

    @Override
    public void updateStats() {
        // Co loses health faster than the others

        hunger = Math.max(hunger - 2, MIN_STAT);
        sleep = Math.max(sleep - 2, MIN_STAT);
        happiness = Math.max(happiness - 1, MIN_STAT);

        // Minimal health drop unless very hunger
        if (hunger <= 15) {
            health = Math.max(health - 1, MIN_STAT);
        }
    }

    public String getLabel() {
        return "Co (Polar Bear)";
    }
    
}
