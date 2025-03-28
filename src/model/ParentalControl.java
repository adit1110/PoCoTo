package model;

import java.time.LocalDateTime;

/**
 * The ParentalControl class is responsible for enforcing time-based limits on gameplay,
 * tracking total playtime, session durations, and managing the revival of the bear.
 * It interacts with the Bear object and provides functionality like session tracking
 * and setting up playtime limits for guardians.
 * 
 * @author Krish Patel
 */
public class ParentalControl {

    private LocalDateTime sessionStartTime;
    private int totalPlaytimeMinutes;
    private int totalSessions;
    private boolean isRevivalEnabled;
    private int maxPlaytimeMinutes;

    private Bear bear;

    /**
     * Constructs a ParentalControl object associated with a Bear instance.
     * Initializes the total playtime and session count to zero and sets revival to enabled by default.
     * 
     * @param bear The Bear object associated with this parental control system.
     */
    public ParentalControl(Bear bear) {
        this.bear = bear;
        this.totalPlaytimeMinutes = 0;
        this.totalSessions = 0;
        this.isRevivalEnabled = true;  // Revival is enabled by default
        this.maxPlaytimeMinutes = Integer.MAX_VALUE;  // Default to no playtime limit
    }

    /**
     * Starts a new play session by recording the current time.
     * This method is invoked when the player starts a new session.
     */
    public void startSession() {
        sessionStartTime = LocalDateTime.now();
        totalSessions++;  // Increment session count
    }

    /**
     * Ends the current session, calculates the session duration, and adds it to the total playtime.
     * If the session exceeds the playtime limit, an alert can be triggered (not implemented here).
     */
    public void endSession() {
        if (sessionStartTime != null) {
            int sessionDurationMinutes = (int) java.time.Duration.between(sessionStartTime, LocalDateTime.now()).toMinutes();
            totalPlaytimeMinutes += sessionDurationMinutes;

            // Check if the total playtime exceeds the limit
            if (totalPlaytimeMinutes > maxPlaytimeMinutes) {
                // Trigger alert for exceeding the playtime limit (to be implemented)
                System.out.println("Warning: Playtime limit exceeded!");
            }

            sessionStartTime = null;  // Reset the session start time
        }
    }

    /**
     * Sets the playtime limit in minutes. The guardian can use this method to set a daily limit
     * on the total playtime.
     * 
     * @param minutes The new playtime limit in minutes.
     */
    public void setPlaytimeLimit(int minutes) {
        this.maxPlaytimeMinutes = minutes;
    }

    /**
     * Enables or disables the revival option for the bear. This method allows the guardian to
     * decide whether the bear can be revived after death.
     * 
     * @param enabled True if revival should be enabled, false otherwise.
     */
    public void setRevivalEnabled(boolean enabled) {
        this.isRevivalEnabled = enabled;
    }

    /**
     * Checks if the bear is eligible for revival based on the settings.
     * If revival is disabled or the bear is not dead, this will return false.
     * 
     * @return True if the bear can be revived, false otherwise.
     */
    public boolean canReviveBear() {
        return isRevivalEnabled && bear.isDead();
    }

    /**
     * Revives the bear if the revival option is enabled and the bear is dead.
     * This method should be called when the guardian chooses to revive the bear.
     */
    public void reviveBear() {
        if (canReviveBear()) {
            bear.revive();  // Assumes the Bear class has a revive method.
            System.out.println("The bear has been revived!");
        } else {
            System.out.println("Revival is either disabled or the bear is not dead.");
        }
    }

    /**
     * Returns the total playtime in minutes.
     * 
     * @return The total playtime in minutes.
     */
    public int getTotalPlaytimeMinutes() {
        return totalPlaytimeMinutes;
    }

    /**
     * Returns the total number of sessions played.
     * 
     * @return The total number of sessions.
     */
    public int getTotalSessions() {
        return totalSessions;
    }

    /**
     * Returns the current playtime limit in minutes.
     * 
     * @return The current playtime limit in minutes.
     */
    public int getPlaytimeLimit() {
        return maxPlaytimeMinutes;
    }

    /**
     * Returns whether the bear revival option is enabled.
     * 
     * @return True if revival is enabled, false otherwise.
     */
    public boolean isRevivalEnabled() {
        return isRevivalEnabled;
    }
}
