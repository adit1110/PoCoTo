/**
 * Author: Adit Bhimani
 *
 * Po class represents the Panda bear type.
 * Implements specific behavior for updating stats.
 */

package model;

/**
 * Represents a Panda Bear in the PoCoTo game.
 * Po has a special behavior where it becomes angry more easily.
 */
public class Po extends Bear {

    public Po(String name) {
        super(name);
    }

    /**
     * Update stats for Po.
     * Po loses hunger faster than others. If hunger is too low, health drops.
     */
    @Override
    public void updateStats() {
        // Po loses 4 points of hunger per update
        hunger = Math.max(hunger - 4, MIN_STAT);
        // Loses 2 points of sleep per update
        sleep = Math.max(sleep - 2, MIN_STAT);
        // Loses 2 points of happiness per update
        happiness = Math.max(happiness - 2, MIN_STAT);

        // If hunger is less than or equal to 25, drop health by 3 points
        if (hunger < 25 || sleep < 20) {
        health = Math.max(health - 2, MIN_STAT);
        }
    }

    /**
     * Return the display label for this bear.
     *
     * @return "Po (Panda)"
     */
    @Override
    public String getLabel() {
        return "Po (Panda)";
    }
}