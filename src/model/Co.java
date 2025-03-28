/**
 * @author: Adit Bhimani
 * Co class represents the Polar Bear type.
 * Implements specific behavior for updating stats.
 */

 package model;

/**
 * Represents a Polar Bear in the PoCoTo game.
 * Co has unique behavior affecting how it updates health when hungry.
 */
public class Co extends Bear {

    /**
     * Constructor to initialize a Co bear with a name.
     */
    public Co(String name) {
        super(name);
    }

    /**
     * Update stats for Co.
     * Co loses stats at a different rate and health drops when extremely hungry.
     */
    @Override
    public void updateStats() {
        // Co loses 2 points of hunger per update
        hunger = Math.max(hunger - 2, MIN_STAT);
        // Loses 2 points of sleep per update
        sleep = Math.max(sleep - 2, MIN_STAT);
        // Loses 1 point of happiness per update
        happiness = Math.max(happiness - 1, MIN_STAT);

        // If hunger is less than or equal to 15, lose 1 point of health
        if (hunger <= 15) {
            health = Math.max(health - 1, MIN_STAT);
        }
    }
    
    /**
     * Return the display label for this bear.
     *
     * @return "Co (Polar Bear)"
     */
    @Override
    public String getLabel() {
        return "Co (Polar Bear)";
    }
}