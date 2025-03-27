/**
 * Author: Adit Bhimani
 * To class represents the Grizzly Bear type.
 * Implements specific behavior for updating stats.
 */

package model;

/**
 * To class represents the Grizzly Bear type.
 * Implements specific behavior for updating stats.
 */
public class To extends Bear {

    public To(String name) {
        super(name);
    }

    /**
     * Update stats for To.
     * To loses hunger, sleep, and particularly happiness quickly.
     * If happiness is very low, health also drops.
     */
    @Override
    public void updateStats() {
        // To loses 3 points of hunger per update
        hunger = Math.max(hunger - 3, MIN_STAT);
        // Loses 2 points of sleep per update
        sleep = Math.max(sleep - 2, MIN_STAT);
        // Loses 4 points of happiness per update (big penalty)
        happiness = Math.max(happiness - 4, MIN_STAT);

        // If happiness is less than or equal to 20, lose 2 points of health
        if (happiness <= 20) {
            health = Math.max(health - 2, MIN_STAT);
        }
    }
    
    /**
     * Return the display label for this bear.
     *
     * @return "To (Grizzly Bear)"
     */
    @Override
    public String getLabel() {
        return "To (Grizzly Bear)";
    }
}