package model;

public class Po extends Bear{
    
    public Po(String name) {
        super(name);
    }

    @Override
    public void updateStats() {
        // Po loses hunger faster than the others

        hunger = Math.max(hunger - 4, MIN_STAT);
        sleep = Math.max(sleep - 2, MIN_STAT);
        happiness = Math.max(happiness - 2, MIN_STAT);

        if (hunger <= 25) {
            health = Math.max(health - 3, MIN_STAT);
        }
    }
}
