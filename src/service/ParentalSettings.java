package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Shared, persisted playtime policy independent of individual bear saves. */
public final class ParentalSettings {
    private static final ParentalSettings DEFAULT =
            new ParentalSettings(Path.of("parental_controls.properties"));
    private final Path file;
    private int limitMinutes = -1; // -1 means unlimited
    private boolean revivalEnabled = true;
    private long playedSeconds;
    private long sessions;
    private long sessionStartedNanos = -1;

    public static ParentalSettings getDefault() { return DEFAULT; }

    public ParentalSettings(Path file) {
        this.file = file;
        reload();
    }

    public synchronized void reload() {
        Properties p = new Properties();
        if (Files.exists(file)) {
            try (InputStream in = Files.newInputStream(file)) {
                p.load(in);
            } catch (IOException e) {
                System.err.println("Could not load parental settings: " + e.getMessage());
            }
        }
        try {
            int limit = Integer.parseInt(p.getProperty("limitMinutes", "-1"));
            limitMinutes = limit == -1 || limit == 60 || limit == 120 || limit == 180 ? limit : -1;
        } catch (NumberFormatException e) { limitMinutes = -1; }
        revivalEnabled = Boolean.parseBoolean(p.getProperty("revivalEnabled", "true"));
        try { playedSeconds = Math.max(0, Long.parseLong(p.getProperty("playedSeconds", "0"))); }
        catch (NumberFormatException e) { playedSeconds = 0; }
        try { sessions = Math.max(0, Long.parseLong(p.getProperty("sessions", "0"))); }
        catch (NumberFormatException e) { sessions = 0; }
    }

    public synchronized void configure(int minutes, boolean revival) {
        if (minutes != -1 && minutes != 60 && minutes != 120 && minutes != 180)
            throw new IllegalArgumentException("Unsupported playtime limit");
        this.limitMinutes = minutes;
        this.revivalEnabled = revival;
        save();
    }

    public synchronized int getLimitMinutes() { return limitMinutes; }
    public synchronized boolean isRevivalEnabled() { return revivalEnabled; }
    public synchronized long getPlayedSeconds() { return playedSeconds; }
    public synchronized long getSessions() { return sessions; }
    public synchronized boolean canPlay() {
        return limitMinutes < 0 || playedSeconds < (long) limitMinutes * 60;
    }
    public synchronized boolean canRevive(model.Bear bear) {
        return revivalEnabled && bear != null && bear.isDead();
    }
    public synchronized boolean revive(model.Bear bear) {
        if (!canRevive(bear)) return false;
        bear.revive();
        return true;
    }
    public synchronized void beginSession() {
        if (sessionStartedNanos != -1 || !canPlay()) return;
        sessionStartedNanos = System.nanoTime();
        sessions++;
        save();
    }
    /** Persist real elapsed seconds, including fractional time carried between checks. */
    public synchronized void flushElapsedTime() {
        if (sessionStartedNanos == -1) return;
        long now = System.nanoTime();
        long elapsed = Math.max(0, (now - sessionStartedNanos) / 1_000_000_000L);
        if (elapsed > 0) {
            playedSeconds += elapsed;
            sessionStartedNanos += elapsed * 1_000_000_000L;
            save();
        }
    }
    public synchronized void endSession() {
        if (sessionStartedNanos == -1) return;
        flushElapsedTime();
        sessionStartedNanos = -1;
        save();
    }
    private void save() {
        Properties p = new Properties();
        p.setProperty("limitMinutes", Integer.toString(limitMinutes));
        p.setProperty("revivalEnabled", Boolean.toString(revivalEnabled));
        p.setProperty("playedSeconds", Long.toString(playedSeconds));
        p.setProperty("sessions", Long.toString(sessions));
        try {
            if (file.toAbsolutePath().getParent() != null)
                Files.createDirectories(file.toAbsolutePath().getParent());
            try (OutputStream out = Files.newOutputStream(file)) {
                p.store(out, "PoCoTo parental settings");
            }
        } catch (IOException e) {
            System.err.println("Could not save parental settings: " + e.getMessage());
        }
    }
}
